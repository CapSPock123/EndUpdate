package net.capspock.endupdate.worldgen.tree.custom;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class AbyssalTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<AbyssalTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            abyssalTrunkPlacerInstance -> trunkPlacerParts(abyssalTrunkPlacerInstance).apply(abyssalTrunkPlacerInstance, AbyssalTrunkPlacer::new)
    );

    public AbyssalTrunkPlacer(int trunkHeight, int i1, int i2) {
        super(trunkHeight, i1, i2);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.ABYSSAL_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader pLevel,
            BiConsumer<BlockPos, BlockState> pBlockSetter,
            RandomSource pRandom,
            int pFreeTreeHeight,
            BlockPos pPos,
            TreeConfiguration pConfig)
    {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        Direction randomDirection = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
        int trunkHeight = pFreeTreeHeight - pRandom.nextInt(4) - 1;
        int randomTreeHeight = 3 - pRandom.nextInt(3);
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        int posX = pPos.getX();
        int posZ = pPos.getZ();
        OptionalInt optionalint = OptionalInt.empty();

        for (int i1 = 0; i1 < pFreeTreeHeight; i1++) {
            int j1 = pPos.getY() + i1;
            if (i1 >= trunkHeight && randomTreeHeight > 0) {
                posX += randomDirection.getStepX();
                posZ += randomDirection.getStepZ();
                randomTreeHeight--;
            }

            if (this.placeLog(pLevel, pBlockSetter, pRandom, blockPos.set(posX, j1, posZ), pConfig)) {
                optionalint = OptionalInt.of(j1 + 1);
            }
        }

        if (optionalint.isPresent()) {
            list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(posX, optionalint.getAsInt(), posZ), 1, false));
        }

        posX = pPos.getX();
        posZ = pPos.getZ();
        Direction randomDirection2 = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
        if(randomDirection2 == randomDirection) {
            randomDirection2 = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
        }
            int j2 = trunkHeight - pRandom.nextInt(4) - 1;
            int k1 = 1 + pRandom.nextInt(5);
            optionalint = OptionalInt.empty();

            for (int l1 = j2; l1 < pFreeTreeHeight && k1 > 0; k1--) {
                if (l1 >= 1) {
                    int i2 = pPos.getY() + l1;
                    posX += randomDirection2.getStepX();
                    posZ += randomDirection2.getStepZ();
                    if (this.placeLog(pLevel, pBlockSetter, pRandom, blockPos.set(posX, i2, posZ), pConfig)) {
                        optionalint = OptionalInt.of(i2 + 1);
                    }
                }

                l1++;
            }

            if (optionalint.isPresent()) {
                list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(posX, optionalint.getAsInt(), posZ), 0, false));
            }

        return list;
    }
}
