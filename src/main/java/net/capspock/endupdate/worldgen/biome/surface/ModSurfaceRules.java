package net.capspock.endupdate.worldgen.biome.surface;

import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource ENDER_BOG = makeStateRule(ModBlocks.ENDER_BOG.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource enderBogSurface = SurfaceRules.sequence(ENDER_BOG);
        return SurfaceRules.sequence(SurfaceRules.sequence(SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomes.ENDER_MIRE),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, ENDER_BOG)),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, enderBogSurface)));
    }

    private static SurfaceRules.RuleSource makeStateRule(@NotNull Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
