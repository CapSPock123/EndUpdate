package net.capspock.endupdate.util;

import net.capspock.endupdate.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeCustomElytra(ModItems.DIAMOND_ELYTRA_CHESTPLATE.get());
        makeCustomElytra(ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());
        makeCustomElytra(ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE.get());
        ItemProperties.register(ModItems.ENDER_BOW.get(), ResourceLocation.withDefaultNamespace("pull"),
                (itemStack, clientLevel, livingEntity, i) -> livingEntity == null ? 0 :
                        livingEntity.getUseItem() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F);
        ItemProperties.register(ModItems.ENDER_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"),
                (itemStack, clientLevel, livingEntity, i) -> livingEntity != null
                        && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
    }

    private static void makeCustomElytra(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("broken"),
                (itemStack, clientLevel, livingEntity, i) -> item.canElytraFly(itemStack, livingEntity) ? 0f : 1f);
    }
}
