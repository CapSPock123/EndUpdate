package net.capspock.endupdate.util;

import net.capspock.endupdate.item.ModItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeCustomBow(ModItems.ENDER_BOW.get());
        makeCustomElytra(ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());
    }

    private static void makeCustomBow(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (ClampedItemPropertyFunction)((itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        }));
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (ClampedItemPropertyFunction)((p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F));
    }

    private static void makeCustomElytra(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("broken"), (ClampedItemPropertyFunction)((itemStack, clientLevel, livingEntity, i) ->  {
            if(itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
                return 1;
            } else {
                return 0;
            }
        }));
    }
}
