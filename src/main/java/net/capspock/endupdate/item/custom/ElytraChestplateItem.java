package net.capspock.endupdate.item.custom;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class ElytraChestplateItem extends ArmorItem {
    public ElytraChestplateItem(ArmorMaterial pMaterial, Properties pProperties) {
        super(pMaterial, Type.CHESTPLATE, pProperties);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return ElytraItem.isFlyEnabled(stack);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0) {
                    stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
                }
                entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }
}
