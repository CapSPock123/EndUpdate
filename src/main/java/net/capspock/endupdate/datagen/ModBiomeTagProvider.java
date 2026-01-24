package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends BiomeTagsProvider {
    public ModBiomeTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Biomes.OUTER_END_ISLANDS)
                .add(Biomes.END_BARRENS)
                .add(Biomes.END_HIGHLANDS)
                .add(Biomes.END_MIDLANDS)
                .add(Biomes.SMALL_END_ISLANDS);
        tag(ModTags.Biomes.HAS_END_ALTAR)
                .replace(false)
                .add(Biomes.END_HIGHLANDS)
                .add(Biomes.END_MIDLANDS);
    }
}
