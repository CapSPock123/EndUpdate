package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.entity.client.*;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EnderSlimeModel.LAYER_LOCATION, EnderSlimeModel::createInnerBodyLayer);
        event.registerLayerDefinition(EnderSlimeModel.ENDER_SLIME_OUTER, EnderSlimeModel::createOuterBodyLayer);
    }

    /* Code from ElytraChestplates by FrogMan650
    GitHub Repo: https://github.com/FrogMan650/ElytraChestplates
    Distributed under the MIT License
     */
    @SubscribeEvent @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {

            LivingEntityRenderer renderer = event.getPlayerSkin(skin);

            if (renderer != null) {
                renderer.addLayer(new DiamondElytraChestplateLayer(renderer, event.getEntityModels()));
                renderer.addLayer(new NetheriteElytraChestplateLayer(renderer, event.getEntityModels()));
                renderer.addLayer(new ShulkerPlatedNetheriteElytraChestplateLayer(renderer, event.getEntityModels()));
                renderer.addLayer(new ShulkerElytraChestplateLayer(renderer, event.getEntityModels()));
            }
        }
        EntityRenderer renderer = event.getEntityRenderer(EntityType.ARMOR_STAND);
        if (renderer != null) {
            ((LivingEntityRenderer)renderer).addLayer(new DiamondElytraChestplateLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
            ((LivingEntityRenderer)renderer).addLayer(new NetheriteElytraChestplateLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
            ((LivingEntityRenderer)renderer).addLayer(new ShulkerPlatedNetheriteElytraChestplateLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
            ((LivingEntityRenderer)renderer).addLayer(new ShulkerElytraChestplateLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ENDER_SLIME.get(), EnderSlimeEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.ENDER_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                EnderSlimeEntity::checkEnderSlimeSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
