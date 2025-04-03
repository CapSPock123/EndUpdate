package net.capspock.endupdate.entity.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class ItemProjectile extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ItemProjectile.class, EntityDataSerializers.ITEM_STACK);

    private double baseDamage = 1.0;

    @Nullable
    private ItemStack firedFromWeapon = null;

    public ItemProjectile(EntityType<? extends ItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ItemProjectile(EntityType<? extends ItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
    }

    public ItemProjectile(EntityType<? extends ItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        this(pEntityType, pShooter.getX(), pShooter.getEyeY() - 0.1F, pShooter.getZ(), pLevel);
        this.setOwner(pShooter);
    }

    public ItemProjectile(EntityType<? extends ItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel, ItemStack pPickupItemStack,
                          @Nullable ItemStack pFiredFromWeapon) {
        this(pEntityType, pLevel);
        this.setCustomName(pPickupItemStack.get(DataComponents.CUSTOM_NAME));
        if(pFiredFromWeapon != null) {
            this.firedFromWeapon = pFiredFromWeapon.copy();
        }
    }

    protected void doPostHurtEffects(LivingEntity pTarget) {
    }

    protected abstract Item getDefaultItem();

    public void setItem(ItemStack pStack) {
        this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_ITEM_STACK, new ItemStack(this.getDefaultItem()));
    }

    @Override
    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
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
                float f1 = 0.25F;
                this.level()
                        .addParticle(
                                ParticleTypes.BUBBLE,
                                d0 - vec3.x * 0.25,
                                d1 - vec3.y * 0.25,
                                d2 - vec3.z * 0.25,
                                vec3.x,
                                vec3.y,
                                vec3.z
                        );
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.setDeltaMovement(vec3.scale((double)f));
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Entity owner = this.getOwner();
        DamageSource damagesource = this.damageSources().thrown(this, (Entity)(owner != null ? owner : this));
        double baseDamage = this.baseDamage;
        float multiplier = (float) this.getDeltaMovement().length();
        if (this.getWeaponItem() != null && this.level() instanceof ServerLevel serverlevel) {
            baseDamage = (double) EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, (float)baseDamage);
        }
        int finalDamage = Mth.ceil(Mth.clamp((double)multiplier * baseDamage, 0.0, 2.147483647E9));
        entity.hurt(this.damageSources().thrown(this ,this.getOwner()), (float) finalDamage);
        if(pResult.getEntity() instanceof LivingEntity livingEntity) {
            this.doPostHurtEffects(livingEntity);
            this.doKnockback(livingEntity, damagesource);
        }
    }

    protected void doKnockback(LivingEntity pEntity, DamageSource pDamageSource) {
        double d0 = (double)(
                this.getWeaponItem() != null && this.level() instanceof ServerLevel serverlevel
                        ? EnchantmentHelper.modifyKnockback(serverlevel, this.getWeaponItem(), pEntity, pDamageSource, 0.0F)
                        : 0.0F
        );
        if (d0 > 0.0) {
            double d1 = Math.max(0.0, 1.0 - pEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(d0 * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                pEntity.push(vec3.x, 0.1, vec3.z);
            }
        }
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.firedFromWeapon;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putDouble("damage", this.baseDamage);
        if (this.firedFromWeapon != null) {
            pCompound.put("weapon", this.firedFromWeapon.save(this.registryAccess(), new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        if (pCompound.contains("weapon", 10)) {
            this.firedFromWeapon = ItemStack.parse(this.registryAccess(), pCompound.getCompound("weapon")).orElse(null);
        } else {
            this.firedFromWeapon = null;
        }
    }
}
