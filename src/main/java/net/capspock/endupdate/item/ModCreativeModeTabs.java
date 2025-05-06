package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EndUpdate.MOD_ID);

    public static final RegistryObject<CreativeModeTab> END_UPDATE_MATERIALS_TAB = CREATIVE_MODE_TABS.register("end_update_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ENDERSTEEL_INGOT.get()))
                    .title(Component.translatable("creativetab.endupdate.end_update_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ENDERSTEEL_INGOT.get());
                        output.accept(ModItems.ENDERSTEEL_NUGGET.get());
                        output.accept(ModItems.ENDERSTEEL.get());
                        output.accept(ModItems.RAW_ENDERSTEEL.get());
                        output.accept(ModItems.SHULKER_PLATE.get());
                        output.accept(ModItems.ENDER_SLIMEBALL.get());

                        output.accept(ModItems.VOID_ESSENCE.get());

                        output.accept(ModItems.AURORA_ASHES.get());
                        output.accept(ModItems.AURORA_POWDER.get());

                        output.accept(ModItems.SHULKER_SMITHING_TEMPLATE.get());
                        output.accept(ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get());

                        output.accept(ModItems.ENDER_SLIME_SPAWN_EGG.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> END_UPDATE_BLOCKS_TAB = CREATIVE_MODE_TABS.register("end_update_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ENDERSTEEL_BLOCK.get()))
                    .withTabsBefore(END_UPDATE_MATERIALS_TAB.getId())
                    .title(Component.translatable("creativetab.endupdate.end_update_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ENDERSTEEL_BLOCK.get());
                        output.accept(ModBlocks.ENDERSTEEL_ORE.get());

                        output.accept(ModBlocks.MAGIC_BLOCK.get());
                        output.accept(ModBlocks.AURORA_LAMP.get());

                        output.accept(ModBlocks.ABYSSAL_LOG.get());
                        output.accept(ModBlocks.ABYSSAL_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_ABYSSAL_LOG.get());
                        output.accept(ModBlocks.STRIPPED_ABYSSAL_WOOD.get());
                        output.accept(ModBlocks.ABYSSAL_PLANKS.get());
                        output.accept(ModBlocks.ABYSSAL_LEAVES.get());
                        output.accept(ModBlocks.ABYSSAL_SAPLING.get());

                        output.accept(ModBlocks.ABYSSAL_MOSS.get());

                        output.accept(ModBlocks.ABYSSAL_STAIRS.get());
                        output.accept(ModBlocks.ABYSSAL_SLAB.get());
                        output.accept(ModBlocks.ABYSSAL_BUTTON.get());
                        output.accept(ModBlocks.ABYSSAL_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.ABYSSAL_FENCE.get());
                        output.accept(ModBlocks.ABYSSAL_FENCE_GATE.get());
                        output.accept(ModBlocks.ABYSSAL_DOOR.get());
                        output.accept(ModBlocks.ABYSSAL_TRAPDOOR.get());

                        output.accept(ModBlocks.ENDER_SLIME_BLOCK.get());

                        output.accept(ModBlocks.VOID_INFUSER.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> END_UPDATE_EQUIPMENT_TAB = CREATIVE_MODE_TABS.register("end_update_equipment_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ENDERSTEEL_SWORD.get()))
                    .withTabsBefore(END_UPDATE_BLOCKS_TAB.getId())
                    .title(Component.translatable("creativetab.endupdate.end_update_equipment"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ENDERSTEEL_SWORD.get());
                        output.accept(ModItems.ENDERSTEEL_PICKAXE.get());
                        output.accept(ModItems.ENDERSTEEL_SHOVEL.get());
                        output.accept(ModItems.ENDERSTEEL_AXE.get());
                        output.accept(ModItems.ENDERSTEEL_HOE.get());
                        output.accept(ModItems.ENDERSTEEL_HAMMER.get());
                        output.accept(ModItems.ENDER_BOW.get());

                        output.accept(ModItems.SHULKER_HELMET.get());
                        output.accept(ModItems.SHULKER_CHESTPLATE.get());
                        output.accept(ModItems.SHULKER_LEGGINGS.get());
                        output.accept(ModItems.SHULKER_BOOTS.get());
                        output.accept(ModItems.SHULKER_PLATED_NETHERITE_HELMET.get());
                        output.accept(ModItems.SHULKER_PLATED_NETHERITE_CHESTPLATE.get());
                        output.accept(ModItems.SHULKER_PLATED_NETHERITE_LEGGINGS.get());
                        output.accept(ModItems.SHULKER_PLATED_NETHERITE_BOOTS.get());

                        output.accept(ModItems.DIAMOND_ELYTRA_CHESTPLATE.get());
                        output.accept(ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());
                        output.accept(ModItems.SHULKER_PLATED_NETHERITE_ELYTRA_CHESTPLATE.get());
                        output.accept(ModItems.SHULKER_ELYTRA_CHESTPLATE.get());

                        output.accept(ModItems.ENDER_SLIME_BOOTS.get());

                        output.accept(ModItems.SLINGSHOT.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
