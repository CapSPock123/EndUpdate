package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.entity.custom.EnderSlimeballProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EnderSlimeballItem extends Item {
    public EnderSlimeballItem(Properties pProperties) {
        super(pProperties);
    }

    public EnderSlimeballProjectile createEnderSlimeball(Level pLevel, LivingEntity pShooter) {
        return new EnderSlimeballProjectile(pLevel, pShooter);
    }
}
