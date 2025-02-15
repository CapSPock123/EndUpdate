package net.capspock.endupdate.block;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.custom.AuroraLampBlock;
import net.capspock.endupdate.block.custom.MagicBlock;
import net.capspock.endupdate.block.custom.ModFlammableRotatedPillarBlock;
import net.capspock.endupdate.block.custom.ModSaplingBlock;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.sound.ModSounds;
import net.capspock.endupdate.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, EndUpdate.MOD_ID);

    public static final RegistryObject<Block> ENDERSTEEL_BLOCK = registerBlock("endersteel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(60f).requiresCorrectToolForDrops().sound(SoundType.METAL).explosionResistance(1200f)));
    public static final RegistryObject<Block> ENDERSTEEL_ORE = registerBlock("endersteel_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(40f).requiresCorrectToolForDrops().sound(SoundType.STONE).explosionResistance(720f)));

    public static final RegistryObject<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties.of()
                    .strength(15f).explosionResistance(1200f).sound(ModSounds.MAGIC_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> AURORA_LAMP = registerBlock("aurora_lamp",
            () -> new AuroraLampBlock(BlockBehaviour.Properties.of().strength(9f).explosionResistance(6f)
                    .lightLevel(state -> state.getValue(AuroraLampBlock.CLICKED) ? 15 : 0)));

    public static final RegistryObject<RotatedPillarBlock> ABYSSAL_LOG = registerBlock("abyssal_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> ABYSSAL_WOOD = registerBlock("abyssal_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ABYSSAL_LOG = registerBlock("stripped_abyssal_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ABYSSAL_WOOD = registerBlock("stripped_abyssal_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final RegistryObject<Block> ABYSSAL_PLANKS = registerBlock("abyssal_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    });

    public static final RegistryObject<Block> ABYSSAL_LEAVES = registerBlock("abyssal_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES))

    {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 60;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    });

    public static final RegistryObject<Block> ABYSSAL_SAPLING = registerBlock("abyssal_sapling",
            () -> new ModSaplingBlock(ModTreeGrowers.ABYSSAL, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING),
                    () -> ModBlocks.ABYSSAL_MOSS.get()));

    public static final RegistryObject<Block> ABYSSAL_MOSS = registerBlock("abyssal_moss",
            () -> new Block(BlockBehaviour.Properties.of().strength(3).explosionResistance(9)
                    .sound(SoundType.NYLIUM).requiresCorrectToolForDrops()));

    public static final RegistryObject<StairBlock> ABYSSAL_STAIRS = registerBlock("abyssal_stairs",
            () -> new StairBlock(ModBlocks.ABYSSAL_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(3f).explosionResistance(3f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<SlabBlock> ABYSSAL_SLAB = registerBlock("abyssal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).explosionResistance(3f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<PressurePlateBlock> ABYSSAL_PRESSURE_PLATE = registerBlock("abyssal_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(0.5f).explosionResistance(0.5f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<ButtonBlock> ABYSSAL_BUTTON = registerBlock("abyssal_button",
            () -> new ButtonBlock(BlockSetType.OAK, 1, BlockBehaviour.Properties.of().strength(0.5f).explosionResistance(0.5f).noCollission())
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<FenceBlock> ABYSSAL_FENCE = registerBlock("abyssal_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).explosionResistance(3f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<FenceGateBlock> ABYSSAL_FENCE_GATE = registerBlock("abyssal_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of().strength(2f).explosionResistance(3f))
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<DoorBlock> ABYSSAL_DOOR = registerBlock("abyssal_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(3f).explosionResistance(3f).noOcclusion())
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<TrapDoorBlock> ABYSSAL_TRAPDOOR = registerBlock("abyssal_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(3f).explosionResistance(3f).noOcclusion())
            {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });

    private static <T extends Block> RegistryObject<T>  registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
