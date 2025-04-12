package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.entity.client.EnderSlimeModel;
import net.capspock.endupdate.entity.client.NetheriteElytraChestplateLayer;
import net.capspock.endupdate.entity.client.NetheriteElytraChestplateModel;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
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
        event.registerLayerDefinition(NetheriteElytraChestplateModel.WINGS_LOCATION, NetheriteElytraChestplateModel::createLayer);
    }

    /* Code from ElytraChestplates by FrogMan650
    Github Repo: https://github.com/FrogMan650/ElytraChestplates
    Distributed under the MIT License
     */
    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {

            LivingEntityRenderer renderer = event.getPlayerSkin(skin);

            if (renderer != null) {
                renderer.addLayer(new NetheriteElytraChestplateLayer(renderer, event.getEntityModels()));
            }
        }
        EntityRenderer renderer = event.getEntityRenderer(EntityType.ARMOR_STAND);
        if (renderer != null) {
            ((LivingEntityRenderer)renderer).addLayer(new NetheriteElytraChestplateLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
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
