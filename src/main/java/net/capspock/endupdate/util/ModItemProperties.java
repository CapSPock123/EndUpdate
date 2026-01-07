package net.capspock.endupdate.util;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ElytraItem;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.DIAMOND_ELYTRA_CHESTPLATE.get(), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "broken"),
                (itemStack, clientLevel, livingEntity, i) -> ElytraItem.isFlyEnabled(itemStack) ? 0f : 1f);
        ItemProperties.register(ModItems.NETHERITE_ELYTRA_CHESTPLATE.get(), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "broken"),
                (itemStack, clientLevel, livingEntity, i) -> ElytraItem.isFlyEnabled(itemStack) ? 0f : 1f);
        ItemProperties.register(ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE.get(), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "broken"),
                (itemStack, clientLevel, livingEntity, i) -> ElytraItem.isFlyEnabled(itemStack) ? 0f : 1f);
    }
}
