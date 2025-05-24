package net.capspock.endupdate;

import com.mojang.logging.LogUtils;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.block.entity.ModBlockEntities;
import net.capspock.endupdate.component.ModDataComponentTypes;
import net.capspock.endupdate.effect.ModEffects;
import net.capspock.endupdate.enchantment.ModEnchantmentEffects;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.entity.client.EnderSlimeRenderer;
import net.capspock.endupdate.entity.client.EnderSlimeballRenderer;
import net.capspock.endupdate.item.ModCreativeModeTabs;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.loot.ModLootModifiers;
import net.capspock.endupdate.particle.custom.EnderSlimeballParticles;
import net.capspock.endupdate.particle.ModParticles;
import net.capspock.endupdate.potion.ModPotions;
import net.capspock.endupdate.screen.ModMenuTypes;
import net.capspock.endupdate.screen.custom.VoidInfuserScreen;
import net.capspock.endupdate.sound.ModSounds;
import net.capspock.endupdate.util.ModItemProperties;
import net.capspock.endupdate.worldgen.tree.custom.ModTrunkPlacerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EndUpdate.MOD_ID)
public class EndUpdate {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "endupdate";

    public EndUpdate() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);

        ModTrunkPlacerTypes.register(modEventBus);

        ModEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModParticles.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.IRON_HAMMER);
            event.accept(ModItems.DIAMOND_HAMMER);
            event.accept(ModItems.NETHERITE_HAMMER);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
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
            EntityRenderers.register(ModEntities.ENDER_SLIMEBALL.get(), EnderSlimeballRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENDER_SLIME_BLOCK.get(), RenderType.translucent());

            MenuScreens.register(ModMenuTypes.VOID_INFUSER_MENU.get(), VoidInfuserScreen::new);
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpecial(ModParticles.ITEM_ENDER_SLIME.get(), new EnderSlimeballParticles.EnderSlimeProvider());
        }
    }
}
