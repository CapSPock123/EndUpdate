package net.capspock.endupdate.block.entity.custom;

import net.capspock.endupdate.block.custom.EnderSlimeGeyserBlock;
import net.capspock.endupdate.block.custom.EruptionType;
import net.capspock.endupdate.block.entity.ModBlockEntities;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.capspock.endupdate.entity.custom.EnderSlimeballProjectile;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EnderSlimeGeyserBlockEntity extends BlockEntity {
    private int tickCount = 0;
    private int i = 0;

    public EnderSlimeGeyserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENDER_SLIME_GEYSER_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(blockState.getBlock() instanceof EnderSlimeGeyserBlock enderSlimeGeyserBlock &&
                enderSlimeGeyserBlock.isEruptionScheduled() && level instanceof ServerLevel serverLevel) {
            tickCount++;
            double random = enderSlimeGeyserBlock.getRandom();
            EruptionType eruptionType = enderSlimeGeyserBlock.getEruptionType();
            BlockPos pos = blockPos.above();
            Vec3 vec3 = pos.getCenter();

            if (tickCount % 20 == 0 && tickCount < 100) {
                serverLevel.sendParticles(ModParticles.ITEM_ENDER_SLIME_PARTICLES.get(),
                        vec3.x, vec3.y, vec3.z,
                        15, 0.5f, 0f, 0.5f, 1f);
                serverLevel.playSound(null, blockPos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1f, 1f);
            }

            if(tickCount == 100) {
                serverLevel.playSound(null, blockPos, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 1f, 1f);
                serverLevel.playSound(null, blockPos, SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.BLOCKS, 1f, 1f);
                List<Entity> entities = new ArrayList<>(level.getEntities(null,
                        AABB.encapsulatingFullBlocks(pos.below().east().south(), pos.above(25).west().north())));
                entities.removeIf((entity -> !(entity instanceof LivingEntity)));
                entities.forEach((entity) -> {
                    double yMovement = Math.max(1, random * 3);

                    if(entity.getDeltaMovement().y < yMovement) {
                        entity.setDeltaMovement(0, yMovement, 0);

                        if(entity instanceof Player player) {
                            player.hurtMarked = true;
                        }
                    }
                });
            }

            if(tickCount >= 100) {
                if (eruptionType == EruptionType.ENDER_SLIME_ENTITY) {
                    EnderSlimeEntity entity = ModEntities.ENDER_SLIME.get().spawn(serverLevel, pos, MobSpawnType.NATURAL);
                    if (entity != null) {
                        entity.setDeltaMovement(0, Math.max(1, random * 3), 0);
                    }
                    i++;
                    if (i > Math.ceil(random * 3)) {
                        enderSlimeGeyserBlock.setEruptionScheduled(false);
                        enderSlimeGeyserBlock.setEruptionType(null);
                        tickCount = 0;
                        i = 0;
                    }
                } else if (eruptionType == EruptionType.ENDER_SLIMEBALL_PROJECTILE) {
                    EnderSlimeballProjectile entity = new EnderSlimeballProjectile(ModEntities.ENDER_SLIMEBALL.get(),
                            vec3.x, vec3.y, vec3.z, level);

                    entity.shoot(0, Math.max(Math.random(), 0.5), 0, (float) random * 2, 5);

                    level.addFreshEntity(entity);
                    i++;
                    if (i >= Math.max(Math.round(random * 15), 5)) {
                        enderSlimeGeyserBlock.setEruptionScheduled(false);
                        enderSlimeGeyserBlock.setEruptionType(null);
                        tickCount = 0;
                        i = 0;
                    }
                }
            }
        }
    }
}
