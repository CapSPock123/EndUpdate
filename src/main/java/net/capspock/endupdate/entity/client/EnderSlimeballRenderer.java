package net.capspock.endupdate.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.capspock.endupdate.EndUpdate;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;

public class EnderSlimeballRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public EnderSlimeballRenderer(EntityRendererProvider.Context pContext, float scale, boolean fullBright) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public EnderSlimeballRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25)) {
            pPoseStack.pushPose();
            pPoseStack.scale(this.scale, this.scale, this.scale);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            this.itemRenderer
                    .renderStatic(
                            pEntity.getItem(),
                            ItemDisplayContext.GROUND,
                            pPackedLight,
                            OverlayTexture.NO_OVERLAY,
                            pPoseStack,
                            pBuffer,
                            pEntity.level(),
                            pEntity.getId()
                    );
            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "textures/item/ender_slimeball.png");
    }
}
