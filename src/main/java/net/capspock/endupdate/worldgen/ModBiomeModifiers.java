package net.capspock.endupdate.worldgen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ENDERSTEEL_ORE = registerKey("add_endersteel_ore");

    public static final ResourceKey<BiomeModifier> SPAWN_ENDER_SLIME = registerKey("spawn_ender_slime");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_ENDERSTEEL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ENDERSTEEL_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SPAWN_ENDER_SLIME, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_BARRENS),
                        biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.SMALL_END_ISLANDS)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.ENDER_SLIME.get(), 15, 2, 5))));

    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
    }
}
