package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.util.ModTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElytraChestplateItem extends ArmorItem {
    private final String key = "endupdate.elytra_damage";
    private final int maxElytraDamage = 432;

    public ElytraChestplateItem(ArmorMaterial pMaterial, Properties pProperties) {
        super(pMaterial, Type.CHESTPLATE, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if(this.elytraIsDamaged(pStack) && pIsAdvanced.isAdvanced()) {
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
                    if(entity instanceof Player player && player.isCreative()) {
                        return true;
                    }

                    int unbreakingLevel = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
                    int unbreakingProc = 0;
                    for(int i = 0; i < unbreakingLevel; i++) {
                        if(DigDurabilityEnchantment.shouldIgnoreDurabilityDrop(stack, unbreakingLevel, entity.getRandom())) {
                            unbreakingProc++;
                        }
                    }

                    if(unbreakingProc == 0) {
                        this.setElytraDamageValue(stack, this.getElytraDamageValue(stack) + 1);
                    }
                }
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }

    public int getElytraDamageValue(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.ELYTRA_CHESTPLATE) ?
                elytraIsDamaged(itemStack) ? itemStack.getTag().getInt(key) : 0 : 0;
    }

    public void setElytraDamageValue(ItemStack itemStack, int value) {
        if(itemStack.getItem() instanceof ElytraChestplateItem) {
            if(elytraIsDamaged(itemStack)) {
                itemStack.getTag().putInt(key, value);
            } else {
                CompoundTag tag = new CompoundTag();
                tag.putInt(key, value);
                itemStack.addTagElement(key, tag);
            }
        }
    }

    public boolean isElytraBarVisible(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE)) {
            return elytraIsDamaged(pStack);
        }

        return false;
    }

    public int getElytraBarWidth(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE) && elytraIsDamaged(pStack)) {
            return Math.round(13.0F - (float)this.getElytraDamageValue(pStack) * 13.0F / (float) maxElytraDamage);
        }

        return 0;
    }

    public int getElytraBarColor(ItemStack pStack) {
        if(pStack.is(ModTags.Items.ELYTRA_CHESTPLATE) && elytraIsDamaged(pStack)) {
            float f = Math.max(0.0F, ((maxElytraDamage - (float)this.getElytraDamageValue(pStack)) / maxElytraDamage));
            return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        }
        return 0;
    }

    private boolean elytraIsDamaged(ItemStack itemStack) {
        if(itemStack.getTag().getInt(key) == 0) {
            itemStack.removeTagKey(key);
        }

        return itemStack.getTag().contains(key);
    }
}
