package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnderSlimeArmorItem extends ArmorItem {
    public EnderSlimeArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide() && pEntity instanceof Player player) {
            if(player.isCrouching()) {
                ItemStack boots = player.getInventory().getArmor(0);
                if(!boots.isEmpty() && boots.is(ModItems.ENDER_SLIME_BOOTS.get())) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 1, 15, false, false));
                    player.resetFallDistance();
                }
            }
        }
    }
}
