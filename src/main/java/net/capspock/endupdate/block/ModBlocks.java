package net.capspock.endupdate.block;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.custom.EnderSlimeBlock;
import net.capspock.endupdate.block.custom.EndersteelOreBlock;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, EndUpdate.MOD_ID);

    public static final RegistryObject<Block> ENDERSTEEL_ORE = registerBlock("endersteel_ore",
            () -> new EndersteelOreBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE).strength(33, 1200)
                    .lightLevel(state -> state.getValue(EndersteelOreBlock.LIT) ? 5 : 0)));
    public static final RegistryObject<Block> ENDERSTEEL_BLOCK = registerBlock("endersteel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(80, 1200)));
    public static final RegistryObject<Block> RAW_ENDERSTEEL_BLOCK = registerBlock("raw_endersteel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(80, 1200)));

    public static final RegistryObject<Block> ENDER_SLIME_BLOCK = registerBlock("ender_slime_block",
            () -> new EnderSlimeBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).strength(0f).sound(SoundType.SLIME_BLOCK)
                    .explosionResistance(0f).mapColor(MapColor.COLOR_PURPLE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
