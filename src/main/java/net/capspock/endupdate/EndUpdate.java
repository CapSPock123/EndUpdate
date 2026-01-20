package net.capspock.endupdate;

import com.mojang.logging.LogUtils;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.effect.ModEffects;
import net.capspock.endupdate.enchantment.ModEnchantments;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.entity.client.EnderSlimeModel;
import net.capspock.endupdate.entity.client.EnderSlimeRenderer;
import net.capspock.endupdate.entity.client.ModElytraLayer;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.capspock.endupdate.item.ModCreativeModeTabs;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.loot.ModLootModifiers;
import net.capspock.endupdate.particle.ModParticles;
import net.capspock.endupdate.particle.custom.EnderSlimeballBreakingItemParticle;
import net.capspock.endupdate.potion.ModPotions;
import net.capspock.endupdate.sound.ModSounds;
import net.capspock.endupdate.util.ModItemProperties;
import net.minecraft.client.particle.AttackSweepParticle;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EndUpdate.MOD_ID)
public class EndUpdate
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "endupdate";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public EndUpdate(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModEnchantments.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        IBrewingRecipe iBrewingRecipe = new IBrewingRecipe() {
            @Override
            public boolean isInput(@NotNull ItemStack input) {
                return PotionUtils.getPotion(input) == Potions.AWKWARD;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return ingredient.is(ModItems.ENDER_SLIMEBALL.get());
            }

            @Override
            public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
                if(!this.isInput(input) || !this.isIngredient(ingredient)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemStack = new ItemStack(input.getItem());
                itemStack.setTag(new CompoundTag());
                return PotionUtils.setPotion(itemStack, ModPotions.SLIMEY_POTION.get());
            }
        };

        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(iBrewingRecipe));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.IRON_HAMMER);
            event.accept(ModItems.DIAMOND_HAMMER);
            event.accept(ModItems.NETHERITE_HAMMER);
        }

        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.DIAMOND_ELYTRA_CHESTPLATE);
            event.accept(ModItems.NETHERITE_ELYTRA_CHESTPLATE);
        }

        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();

            EntityRenderers.register(ModEntities.ENDER_SLIME.get(), EnderSlimeRenderer::new);
            EntityRenderers.register(ModEntities.ENDER_SLIMEBALL.get(), ThrownItemRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENDER_SLIME_BLOCK.get(), RenderType.translucent());
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), AttackSweepParticle.Provider::new);
            event.registerSpriteSet(ModParticles.ECHO_SONIC_BOOM_PARTICLES.get(), SonicBoomParticle.Provider::new);
            event.registerSpecial(ModParticles.ITEM_ENDER_SLIME_PARTICLES.get(), new EnderSlimeballBreakingItemParticle.EnderSlimeProvider());
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(EnderSlimeModel.LAYER_LOCATION, EnderSlimeModel::createInnerBodyLayer);
            event.registerLayerDefinition(EnderSlimeModel.ENDER_SLIME_OUTER, EnderSlimeModel::createOuterBodyLayer);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.ENDER_SLIME.get(), EnderSlimeEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
            event.register(ModEntities.ENDER_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    EnderSlimeEntity::checkEnderSlimeSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        /* Code adapted from ElytraChestplates by FrogMan650
        GitHub Repo: https://github.com/FrogMan650/ElytraChestplates
        Distributed under the MIT License */
        @SubscribeEvent @SuppressWarnings({"rawtypes", "unchecked"})
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            for (String skin : event.getSkins()) {
                LivingEntityRenderer renderer = event.getPlayerSkin(skin);
                if (renderer != null) {
                    renderer.addLayer(new ModElytraLayer(renderer, event.getEntityModels()));
                }
            }
            EntityRenderer renderer = event.getEntityRenderer(EntityType.ARMOR_STAND);
            if (renderer != null) {
                ((LivingEntityRenderer)renderer).addLayer(new ModElytraLayer(((LivingEntityRenderer)renderer), event.getEntityModels()));
            }
        }
    }
}
