package net.capspock.endupdate.entity.custom;

import net.capspock.endupdate.effect.ModEffects;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static net.minecraftforge.event.ForgeEventFactory.onProjectileImpact;

public class EnderSlimeballProjectile extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(EnderSlimeballProjectile.class, EntityDataSerializers.ITEM_STACK);
    private final double baseDamage = 1.0;

    public EnderSlimeballProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EnderSlimeballProjectile(EntityType<? extends EnderSlimeballProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public EnderSlimeballProjectile(Level pLevel, LivingEntity pShooter) {
        this(ModEntities.ENDER_SLIMEBALL.get(), pShooter.getX(), pShooter.getEyeY() - 0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ITEM_STACK, this.getItem());
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ModItems.ENDER_SLIMEBALL.get().getDefaultInstance();
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? ModParticles.ITEM_ENDER_SLIME_PARTICLES.get() :
                new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    protected float getGravity() {
        return 0.03F;
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        System.out.println(UUID.nameUUIDFromBytes("endupdate:sticky".getBytes()));
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        float multiplier = (float)this.getDeltaMovement().length();
        int finalDamage = Mth.ceil(Mth.clamp((double)multiplier * this.baseDamage, 0.0D, Integer.MAX_VALUE));

        Entity owner = this.getOwner();
        DamageSource damagesource;
        if (owner == null) {
            damagesource = this.damageSources().thrown(this, this);
        } else {
            damagesource = this.damageSources().thrown(this, owner);
            if (owner instanceof LivingEntity) {
                ((LivingEntity)owner).setLastHurtMob(entity);
            }
        }

        if (entity.hurt(damagesource, (float)finalDamage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity) {
                if (!this.level().isClientSide()) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                this.doPostHurtEffects(livingentity);
                this.level().broadcastEntityEvent(this, (byte)3);
            }

            this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.discard();
        } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                this.discard();
            }
        }
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        pLiving.addEffect(new MobEffectInstance(ModEffects.STICKY_EFFECT.get(), 200, 0));
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25,
                        d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
            f = 0.8F;
        } else {
            f = 0.99F;
        }
        this.setDeltaMovement(vec3.scale(f));
        double gravity = this.getGravity();
        if (gravity != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -gravity, 0.0));
        }
        this.setPos(d0, d1, d2);
    }
}
