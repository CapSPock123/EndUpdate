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

    public static final RegistryObject<CreativeModeTab> END_UPDATE_ITEMS_TAB = CREATIVE_MODE_TABS.register("end_update_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ENDERANIUM_INGOT.get()))
                    .title(Component.translatable("creativetab.endupdate.end_update_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ENDERANIUM_INGOT.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> END_UPDATE_BLOCKS_TAB = CREATIVE_MODE_TABS.register("end_update_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ENDERANIUM_ORE.get()))
                    .withTabsBefore(END_UPDATE_ITEMS_TAB.getId())
                    .title(Component.translatable("creativetab.endupdate.end_update_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ENDERANIUM_ORE.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
