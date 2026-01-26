package net.capspock.endupdate.worldgen.biome;

import terrablender.api.EndBiomeRegistry;

public class ModTerrablender {
    public static void registerBiomes() {
        EndBiomeRegistry.registerHighlandsBiome(ModBiomes.ENDER_MIRE, 3);
        EndBiomeRegistry.registerMidlandsBiome(ModBiomes.ENDER_MIRE, 1);
    }
}
