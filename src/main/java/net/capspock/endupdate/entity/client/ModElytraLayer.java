package net.capspock.endupdate.entity.client;

import net.capspock.endupdate.item.custom.ElytraChestplateItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {
    public ModElytraLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer, pModelSet);
    }

    @Override
    public boolean shouldRender(ItemStack stack, T entity) {
        return stack.getItem() instanceof ElytraChestplateItem;
    }
}
