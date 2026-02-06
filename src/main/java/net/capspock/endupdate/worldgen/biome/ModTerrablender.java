package net.capspock.endupdate.worldgen.biome;

import terrablender.api.EndBiomeRegistry;

public class ModTerrablender {
    public static void registerBiomes() {
        EndBiomeRegistry.registerHighlandsBiome(ModBiomes.ENDER_MIRE, 10);
    }
}
