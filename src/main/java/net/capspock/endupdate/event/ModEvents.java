package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static int tickCount = 0;
    private static boolean actionScheduledSword = false;
    private static boolean actionScheduledHoe = false;

    private static LivingEntity entity;
    private static float amount;
    private static DamageSource source;

    private static LevelAccessor levelAccessor;
    private static BlockPos position;
    private static CropBlock block;

    @SubscribeEvent
    public static void onEndersteelSwordHit (LivingDamageEvent event) {
        if(event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == ModItems.ENDERSTEEL_SWORD.get()) {
                if(Math.random() <= 0.2 && !actionScheduledSword) {
                tickCount = 0;
                entity = event.getEntity();
                amount = event.getAmount();
                source = event.getSource();
                actionScheduledSword = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        if(!event.level.isClientSide()) {
            tickCount++;

            if(actionScheduledSword) {
                if (tickCount >= 50) {
                    entity.invulnerableTime = 0;

                    entity.hurt(source, amount);
                    ((ServerLevel) event.level).sendParticles(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), entity.getX() + 0.5,
                            entity.getY() + 1.5, entity.getZ() + 0.5, 1, 0, 0, 0, 1);

                    entity.invulnerableTime = 0;

                    entity = null;
                    source = null;
                    amount = 0;
                    actionScheduledSword = false;
                    tickCount = 0;
                }
            }

            if(actionScheduledHoe) {
                if(tickCount >= 20) {
                    levelAccessor.setBlock(position, block.getStateForAge(0), 3);

                    levelAccessor = null;
                    block = null;
                    position = null;
                    actionScheduledHoe = false;
                    tickCount = 0;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);

        if(player.getMainHandItem().getItem() == ModItems.ENDERSTEEL_HOE.get()) {
            if(blockState.is(BlockTags.CROPS) &&
                    !(blockState.getBlock() instanceof StemBlock) &&
                    !(blockState.getBlock() instanceof PitcherCropBlock)) {
                CropBlock cropBlock = (CropBlock) blockState.getBlock();

                if(cropBlock.getAge(blockState) == cropBlock.getMaxAge()) {
                    if(!level.isClientSide()) {
                        if(player instanceof ServerPlayer serverPlayer) {
                            if(serverPlayer.gameMode.isSurvival()) {
                                if(serverPlayer.getOffhandItem().is(cropBlock.getCloneItemStack(level, blockPos, blockState).getItem())) {
                                    serverPlayer.getOffhandItem().shrink(1);
                                    changeHoeVariables(level, blockPos);
                                    block = cropBlock;
                                }
                            } else {
                                changeHoeVariables(level, blockPos);
                                block = cropBlock;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void changeHoeVariables(LevelAccessor level, BlockPos blockPos) {
        actionScheduledHoe = true;
        tickCount = 0;
        levelAccessor = level;
        position = blockPos;
    }
}
