package net.capspock.endupdate.entity.custom;

import net.capspock.endupdate.effect.ModEffects;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class EnderSlimeballItemProjectileEntity extends ItemProjectile {

    public EnderSlimeballItemProjectileEntity(EntityType<? extends ItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EnderSlimeballItemProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.ENDER_SLIMEBALL.get(), pShooter, pLevel);
    }

    public EnderSlimeballItemProjectileEntity(Level pLevel, double pX, double pY, double pZ, ItemStack pPickupItemStack, @Nullable ItemStack pFiredFromWeapon) {
        super(ModEntities.ENDER_SLIMEBALL.get(), pX, pY, pZ, pLevel, pPickupItemStack, pFiredFromWeapon);
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItem();
        return (ParticleOptions)(!itemstack.isEmpty() && !itemstack.is(this.getDefaultItem())
                ? new ItemParticleOption(ParticleTypes.ITEM, itemstack)
                : ModParticles.ITEM_ENDER_SLIME.get());
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ENDER_SLIMEBALL.get();
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        pLiving.addEffect(new MobEffectInstance(ModEffects.STICKY_EFFECT.getHolder().get(), 200, 0));
    }
}
