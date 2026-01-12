package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.util.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElytraChestplateItem extends ArmorItem {
    private final String key = "endupdate.elytra_damage";
    private final int maxElytraDamage = 432;

    public ElytraChestplateItem(ArmorMaterial pMaterial, Properties pProperties) {
        super(pMaterial, Type.CHESTPLATE, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.getTag().contains(key) && pIsAdvanced.isAdvanced()) {
            pTooltipComponents.add(Component.translatable("tooltip.endupdate.elytra_durability.tooltip",
                    maxElytraDamage - this.getElytraDamageValue(pStack), 432));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return this.getElytraDamageValue(stack) < maxElytraDamage - 1;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0) {
                    if(stack.getTag().contains(key)) {
                        stack.getTag().putInt(key, this.getElytraDamageValue(stack) + 1);
                    } else {
                        CompoundTag tag = new CompoundTag();
                        tag.putInt(key, 1);
                        stack.addTagElement(key, tag);
                    }
                }
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }

    public int getElytraDamageValue(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.ELYTRA_CHESTPLATE) ?
                itemStack.getTag().contains(key) ? itemStack.getTag().getInt(key) : 0 : 0;
    }

    public boolean isElytraBarVisible(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE)) {
            return pStack.getTag().contains(key);
        }
        return super.isBarVisible(pStack);
    }

    public int getElytraBarWidth(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE) && pStack.getTag().contains(key)) {
            return Math.round(13.0F - (float)this.getElytraDamageValue(pStack) * 13.0F / (float) maxElytraDamage);
        }

        return super.getBarWidth(pStack);
    }

    public int getElytraBarColor(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE) && pStack.getTag().contains(key)) {
            float f = Math.max(0.0F, ((maxElytraDamage - (float)this.getElytraDamageValue(pStack)) / maxElytraDamage));
            return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        }
        return super.getBarColor(pStack);
    }
}
