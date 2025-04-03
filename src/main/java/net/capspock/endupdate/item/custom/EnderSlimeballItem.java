package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.entity.custom.EnderSlimeballItemProjectileEntity;
import net.capspock.endupdate.entity.custom.ItemProjectile;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class EnderSlimeballItem extends Item implements ProjectileItem {
    public EnderSlimeballItem(Properties pProperties) {
        super(pProperties);
    }

    public ItemProjectile createEnderSlimeball(Level pLevel, ItemStack pAmmo, LivingEntity pShooter, @Nullable ItemStack pWeapon) {
        return new EnderSlimeballItemProjectileEntity(pLevel, pShooter);
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        EnderSlimeballItemProjectileEntity enderSlimeballItemProjectileEntity = new EnderSlimeballItemProjectileEntity(pLevel, pPos.x(), pPos.y(), pPos.z(), pStack.copyWithCount(1), null);
        return enderSlimeballItemProjectileEntity;
    }
}
