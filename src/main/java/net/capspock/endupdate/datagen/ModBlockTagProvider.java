package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ENDERSTEEL_ORE.get())
                .add(ModBlocks.ENDERSTEEL_BLOCK.get())
                .add(ModBlocks.RAW_ENDERSTEEL_BLOCK.get())
                .add(ModBlocks.ENDER_BOG.get())
                .add(ModBlocks.ENDER_SLIME_GEYSER.get());
        this.tag(Tags.Blocks.NEEDS_WOOD_TOOL)
                .add(ModBlocks.ENDER_BOG.get())
                .add(ModBlocks.ENDER_SLIME_GEYSER.get());
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(ModBlocks.ENDERSTEEL_ORE.get())
                .add(ModBlocks.ENDERSTEEL_BLOCK.get())
                .add(ModBlocks.RAW_ENDERSTEEL_BLOCK.get());
        this.tag(Tags.Blocks.ORES)
                .add(ModBlocks.ENDERSTEEL_ORE.get());
        this.tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.ENDERSTEEL_BLOCK.get());
    }
}
