package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ENDERSTEEL_BLOCK.get())
                .add(ModBlocks.ENDERSTEEL_ORE.get())
                .add(ModBlocks.AURORA_LAMP.get())
                .add(ModBlocks.MAGIC_BLOCK.get())
                .add(ModBlocks.ABYSSAL_MOSS.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.ABYSSAL_PLANKS.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.AURORA_LAMP.get())
                .add(ModBlocks.MAGIC_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ENDERSTEEL_BLOCK.get())
                .add(ModBlocks.ENDERSTEEL_ORE.get())
                .add(ModBlocks.MAGIC_BLOCK.get());

        tag(ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_FOR_ENDERSTEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
                .remove(ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL);

        tag(BlockTags.FENCES).add(ModBlocks.ABYSSAL_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.ABYSSAL_FENCE_GATE.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ABYSSAL_LOG.get())
                .add(ModBlocks.ABYSSAL_WOOD.get())
                .add(ModBlocks.STRIPPED_ABYSSAL_LOG.get())
                .add(ModBlocks.STRIPPED_ABYSSAL_WOOD.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.ABYSSAL_SAPLING.get());


        tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.ABYSSAL_STAIRS.get());

        tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ABYSSAL_SLAB.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.ABYSSAL_BUTTON.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ABYSSAL_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ABYSSAL_FENCE.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ABYSSAL_FENCE_GATE.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.ABYSSAL_DOOR.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ABYSSAL_TRAPDOOR.get());

        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.ENDERSTEEL_BLOCK.get());
    }
}
