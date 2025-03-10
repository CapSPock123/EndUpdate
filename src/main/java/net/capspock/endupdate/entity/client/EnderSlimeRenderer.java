package net.capspock.endupdate.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EnderSlimeRenderer extends MobRenderer<EnderSlimeEntity, EnderSlimeModel<EnderSlimeEntity>> {
    @Override
    public ResourceLocation getTextureLocation(EnderSlimeEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "textures/entity/ender_slime/ender_slime.png");
    }

    public EnderSlimeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new EnderSlimeModel<>(pContext.bakeLayer(EnderSlimeModel.LAYER_LOCATION)), 0.25f);
        this.addLayer(new EnderSlimeOuterLayer<>(this, pContext.getModelSet()));
    }

    public void render(EnderSlimeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = 0.25F * (float)pEntity.getSize();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    protected void scale(EnderSlimeEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 0.999F;
        pPoseStack.scale(0.999F, 0.999F, 0.999F);
        pPoseStack.translate(0.0F, 0.001F, 0.0F);
        float f1 = (float)pLivingEntity.getSize();
        float f2 = Mth.lerp(pPartialTickTime, pLivingEntity.oSquish, pLivingEntity.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        pPoseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
}
