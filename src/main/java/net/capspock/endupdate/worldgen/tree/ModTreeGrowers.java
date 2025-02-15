package net.capspock.endupdate.worldgen.tree;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower ABYSSAL = new TreeGrower(EndUpdate.MOD_ID + ":abyssal",
            Optional.empty(), Optional.of(ModConfiguredFeatures.ABYSSAL_KEY), Optional.empty());
}
