package net.capspock.endupdate.entity.custom;

import net.capspock.endupdate.particle.ModParticles;
import net.capspock.endupdate.worldgen.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jetbrains.annotations.NotNull;

public class EnderSlimeEntity extends Slime {
    public EnderSlimeEntity(EntityType<? extends Slime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return ModParticles.ITEM_ENDER_SLIME_PARTICLES.get();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return EnderSlimeEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 035D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, @NotNull DamageSource pSource) {
        return false;
    }

    public static boolean checkEnderSlimeSpawnRules(EntityType<EnderSlimeEntity> pSlime, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (MobSpawnType.SPAWNER == pSpawnType) {
            return checkMobSpawnRules(pSlime, pLevel, pSpawnType, pPos, pRandom);
        } else {
            if (pLevel.getDifficulty() != Difficulty.PEACEFUL) {
                if (pLevel.getBiome(pPos).is(ModBiomes.ENDER_MIRE)
                        && pPos.getY() > 50
                        && pPos.getY() < 70
                        && pRandom.nextFloat() < 0.5F
                        && pRandom.nextFloat() < pLevel.getMoonBrightness()
                        && pLevel.getMaxLocalRawBrightness(pPos) <= pRandom.nextInt(8)) {
                    return checkMobSpawnRules(pSlime, pLevel, pSpawnType, pPos, pRandom);
                }

                if (!(pLevel instanceof WorldGenLevel)) {
                    return false;
                }

                ChunkPos chunkpos = new ChunkPos(pPos);
                boolean flag = WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, ((WorldGenLevel)pLevel).getSeed(), 987234911L).nextInt(10)
                        == 0;
                if (pRandom.nextInt(10) == 0 && flag && pPos.getY() < 40) {
                    return checkMobSpawnRules(pSlime, pLevel, pSpawnType, pPos, pRandom);
                }
            }

            return false;
        }
    }
}
