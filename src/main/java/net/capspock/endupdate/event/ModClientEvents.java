package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.item.custom.ElytraChestplateItem;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem()) {
            if(event.getPlayer().getUseItem().getItem() == ModItems.ENDER_BOW.get()) {
                float fovModifier = 1f;
                int ticksUsingItem = event.getPlayer().getTicksUsingItem();
                float deltaTicks = (float)ticksUsingItem / 20f;
                if(deltaTicks > 1f) {
                    deltaTicks = 1f;
                } else {
                    deltaTicks *= deltaTicks;
                }
                fovModifier *= 1f - deltaTicks * 0.15f;
                event.setNewFovModifier(fovModifier);
            }
        }
    }

    @SubscribeEvent
    public static void renderElytraChestplateDurabilityBar(ContainerScreenEvent.Render.Foreground event) {
        AbstractContainerScreen<?> containerScreen = event.getContainerScreen();
        AbstractContainerMenu menu = containerScreen.getMenu();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        menu.slots.stream().filter(slot -> {
                    ItemStack itemStack = slot.getItem();
                    return itemStack.getItem() instanceof ElytraChestplateItem elytraChestplateItem &&
                            elytraChestplateItem.isElytraBarVisible(itemStack);
                })
                .forEach(slot -> {
                    ItemStack itemStack = slot.getItem();
                    ElytraChestplateItem elytraChestplateItem = (ElytraChestplateItem) itemStack.getItem();
                    int x = slot.x + 2;
                    int y = slot.y + 16;
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 13, y + 2, -16777216);
                    guiGraphics.fill(RenderType.guiOverlay(), x, y, x + elytraChestplateItem.getElytraBarWidth(itemStack),
                            y + 1, elytraChestplateItem.getElytraBarColor(itemStack) | -16777216);
                });

        int guiTop = containerScreen.getGuiTop();
        int guiLeft = containerScreen.getGuiLeft();
        int mouseX = event.getMouseX();
        int mouseY = event.getMouseY();
        ItemStack itemStack = menu.getCarried();

        if (!itemStack.isEmpty() && itemStack.is(ModTags.Items.ELYTRA_CHESTPLATE)) {
            int x = mouseX - guiLeft - 6;
            int y = mouseY - guiTop + 8;
            ElytraChestplateItem elytraChestplateItem = (ElytraChestplateItem) itemStack.getItem();
            if(elytraChestplateItem.isElytraBarVisible(itemStack)) {
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 13, y + 2, -16777216);
                guiGraphics.fill(RenderType.guiOverlay(), x, y, x + elytraChestplateItem.getElytraBarWidth(itemStack),
                        y + 1, elytraChestplateItem.getElytraBarColor(itemStack) | -16777216);
            }
        }
    }
}
