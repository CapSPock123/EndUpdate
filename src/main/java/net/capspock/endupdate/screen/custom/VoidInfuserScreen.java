package net.capspock.endupdate.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.capspock.endupdate.EndUpdate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class VoidInfuserScreen extends AbstractContainerScreen<VoidInfuserMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "textures/gui/void_infuser/void_infuser_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "textures/gui/arrow_progress.png");
    private static final ResourceLocation VOID_FIRE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "textures/gui/void_fire_progress.png");

    public VoidInfuserScreen(VoidInfuserMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);


        renderProgressArrow(pGuiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE,x + 80, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
            guiGraphics.blit(VOID_FIRE_TEXTURE, x + 56, y + 37, 0, 0, 14, menu.getScaledFireProgress(), 14, 14);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
