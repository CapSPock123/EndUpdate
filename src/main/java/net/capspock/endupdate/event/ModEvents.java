package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.item.custom.HammerItem;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static int tickCount = 0;
    private static boolean isActionScheduledSword = false;
    private static boolean isActionScheduledPickaxe = false;
    private static boolean isActionScheduledPickaxe2 = false;
    private static boolean isActionScheduledHoe = false;
    private static boolean isActionScheduledHammer = false;
    private static boolean isActionScheduledHammer2 = false;
    private static boolean isEchoOnAxe = false;
    private static boolean isEchoOnShovel = false;

    private static LivingEntity entity;
    private static float amount;
    private static DamageSource source;

    private static LevelAccessor levelAccessor;
    private static BlockPos position;
    private static CropBlock block;
    private static BlockState state;

    private static final List<BlockPos> checkedBlocks = new ArrayList<>();
    private static final List<BlockPos> confirmedBlocks = new ArrayList<>();
    private static final List<BlockState> checkedBlocksBlockstates = new ArrayList<>();

    @SubscribeEvent
    public static void onEndersteelSwordHit (LivingDamageEvent event) {
        if(event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == ModItems.ENDERSTEEL_SWORD.get()) {
                if(Math.random() <= 0.2 && !isActionScheduledSword) {
                    tickCount = 0;
                    entity = event.getEntity();
                    amount = event.getAmount();
                    source = event.getSource();
                    isActionScheduledSword = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        if(!event.level.isClientSide()) {
            tickCount++;

            if(isActionScheduledSword) {
                if (tickCount >= 50) {
                    entity.invulnerableTime = 0;

                    entity.hurt(source, amount);
                    ((ServerLevel) event.level).sendParticles(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), entity.getX() + 0.5,
                            entity.getY() + 1.5, entity.getZ() + 0.5, 1, 0, 0, 0, 1);

                    entity.invulnerableTime = 0;

                    entity = null;
                    source = null;
                    amount = 0;
                    isActionScheduledSword = false;
                    tickCount = 0;
                }
            }

            if(isActionScheduledPickaxe) {
                if(tickCount >= 10 && isActionScheduledPickaxe2) {
                    levelAccessor.setBlock(position, state, 3);
                    isActionScheduledPickaxe2 = false;
                }

                if(tickCount >= 20) {
                    Player player = (Player) entity;
                    levelAccessor.destroyBlock(position, !player.isCreative(), player);
                    ((ServerLevel) event.level).sendParticles(ParticleTypes.REVERSE_PORTAL, position.getX() + 0.5,
                            position.getY() + 0.5, position.getZ() + 0.5, 15, 0, 0, 0, 1);

                    isActionScheduledPickaxe = false;
                    levelAccessor = null;
                    position = null;
                    entity = null;
                    state = null;
                    tickCount = 0;
                }
            }

            if(isActionScheduledHoe) {
                if(tickCount >= 20) {
                    levelAccessor.setBlock(position, block.getStateForAge(0), 3);

                    levelAccessor = null;
                    block = null;
                    position = null;
                    isActionScheduledHoe = false;
                    tickCount = 0;
                }
            }

            if(isActionScheduledHammer) {
                if(tickCount == 1) {
                    for(BlockPos pos : checkedBlocks) {;
                        if(Math.random() < 0.2) {
                            confirmedBlocks.add(pos);
                        } else {
                            checkedBlocksBlockstates.remove(0);
                        }
                    }
                }

                if(tickCount >= 10 && isActionScheduledHammer2 && !confirmedBlocks.isEmpty()) {
                    for(BlockPos pos : confirmedBlocks) {
                        levelAccessor.setBlock(pos, checkedBlocksBlockstates.get(0), 3);
                        checkedBlocksBlockstates.remove(0);
                        isActionScheduledHammer2 = false;
                    }
                }

                if(tickCount >= 20) {
                    Player player = (Player) entity;
                    for(BlockPos pos : confirmedBlocks) {
                        levelAccessor.destroyBlock(pos, !player.isCreative(), player);
                        ((ServerLevel) event.level).sendParticles(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5,
                                pos.getY() + 0.5, pos.getZ() + 0.5, 15, 0, 0, 0, 1);
                    }

                    isActionScheduledHammer = false;
                    levelAccessor = null;
                    entity = null;
                    tickCount = 0;
                    checkedBlocks.clear();
                    checkedBlocksBlockstates.clear();
                    confirmedBlocks.clear();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Item item = player.getMainHandItem().getItem();
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);

        if(item == ModItems.ENDERSTEEL_HOE.get()) {
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
        } else if(item == ModItems.ENDERSTEEL_AXE.get() && isEchoOnAxe) {
            if(blockState.is(BlockTags.LOGS)) {
                List<BlockPos> savedPositions = new ArrayList<>();
                List<BlockPos> positionsToCheck = new ArrayList<>();
                List<BlockPos> toBreak = new ArrayList<>();

                savedPositions.add(blockPos);

                int blocksBroken = 1;

                while(!savedPositions.isEmpty()) {
                    int size = savedPositions.size();

                    for(int i = 0; i < size; i++) {
                        positionsToCheck.add(savedPositions.get(0).above().north().west());
                        positionsToCheck.add(savedPositions.get(0).above().north());
                        positionsToCheck.add(savedPositions.get(0).above().north().east());
                        positionsToCheck.add(savedPositions.get(0).above().west());
                        positionsToCheck.add(savedPositions.get(0).above());
                        positionsToCheck.add(savedPositions.get(0).above().east());
                        positionsToCheck.add(savedPositions.get(0).above().south().west());
                        positionsToCheck.add(savedPositions.get(0).above().south());
                        positionsToCheck.add(savedPositions.get(0).above().south().east());

                        positionsToCheck.add(savedPositions.get(0).north().west());
                        positionsToCheck.add(savedPositions.get(0).north());
                        positionsToCheck.add(savedPositions.get(0).north().east());
                        positionsToCheck.add(savedPositions.get(0).west());
                        positionsToCheck.add(savedPositions.get(0));
                        positionsToCheck.add(savedPositions.get(0).east());
                        positionsToCheck.add(savedPositions.get(0).south().west());
                        positionsToCheck.add(savedPositions.get(0).south());
                        positionsToCheck.add(savedPositions.get(0).south().east());

                        positionsToCheck.add(savedPositions.get(0).below().north().west());
                        positionsToCheck.add(savedPositions.get(0).below().north());
                        positionsToCheck.add(savedPositions.get(0).below().north().east());
                        positionsToCheck.add(savedPositions.get(0).below().west());
                        positionsToCheck.add(savedPositions.get(0).below());
                        positionsToCheck.add(savedPositions.get(0).below().east());
                        positionsToCheck.add(savedPositions.get(0).below().south().west());
                        positionsToCheck.add(savedPositions.get(0).below().south());
                        positionsToCheck.add(savedPositions.get(0).below().south().east());

                        savedPositions.remove(0);
                    }

                    for(BlockPos pos : positionsToCheck) {
                        if(level.getBlockState(pos).is(BlockTags.LOGS) && !toBreak.contains(pos)) {
                            savedPositions.add(pos);
                            toBreak.add(pos);
                        }
                    }

                    positionsToCheck.clear();
                }

                for(BlockPos pos : toBreak) {
                    if (blocksBroken <= 27) {
                        level.destroyBlock(pos, !player.isCreative(), player);
                        blocksBroken++;
                    } else {
                        return;
                    }
                }
            }
        } else if(item == ModItems.ENDERSTEEL_PICKAXE.get()) {
            if(blockState.is(Tags.Blocks.ORES) &&
                    item.getEnchantmentLevel(event.getPlayer().getMainHandItem(), Enchantments.SILK_TOUCH) == 0 &&
                    !(blockState.getBlock() == Blocks.ANCIENT_DEBRIS)) {
                if(Math.random() < 0.2) {
                    isActionScheduledPickaxe = true;
                    isActionScheduledPickaxe2 = true;
                    tickCount = 0;
                    levelAccessor = level;
                    position = blockPos;
                    state = blockState;
                    entity = player;
                }
            }
        } else if(item instanceof HammerItem hammerItem) {
            if(player instanceof ServerPlayer serverPlayer) {
                if(item == ModItems.ENDERSTEEL_SHOVEL.get() && !isEchoOnShovel) {
                    return;
                }

                for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                    if (pos == blockPos || !hammerItem.isCorrectToolForDrops(item.getDefaultInstance(), event.getLevel().getBlockState(pos))) {
                        continue;
                    }

                    BlockState state1 = level.getBlockState(pos);

                    level.destroyBlock(pos, serverPlayer.gameMode.isSurvival(), serverPlayer);

                    if(item == ModItems.ENDERSTEEL_HAMMER.get() &&
                            item.getEnchantmentLevel(event.getPlayer().getMainHandItem(), Enchantments.SILK_TOUCH) == 0 &&
                            !(blockState.getBlock() == Blocks.ANCIENT_DEBRIS)) {
                        if(state1.is(Tags.Blocks.ORES) ) {
                            checkedBlocks.add(pos);
                            checkedBlocksBlockstates.add(state1);
                        }
                    }
                }

                if(item == ModItems.ENDERSTEEL_HAMMER.get() && !checkedBlocks.isEmpty() && !isActionScheduledHammer) {
                    isActionScheduledHammer = true;
                    isActionScheduledHammer2 = true;
                    levelAccessor = level;
                    entity = player;
                    tickCount = 0;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickAir(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        Item mainHandItem = player.getMainHandItem().getItem();
        if(player.isShiftKeyDown()) {
            if(mainHandItem == ModItems.ENDERSTEEL_AXE.get()) {
                if(isEchoOnAxe) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    isEchoOnAxe = false;
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);

                    isEchoOnAxe = true;
                }
            } else if(mainHandItem == ModItems.ENDERSTEEL_SHOVEL.get()) {
                if(isEchoOnShovel) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    isEchoOnShovel = false;
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);
                    isEchoOnShovel = true;
                }
            }
        }
    }

    private static void changeHoeVariables(LevelAccessor level, BlockPos blockPos) {
        isActionScheduledHoe = true;
        tickCount = 0;
        levelAccessor = level;
        position = blockPos;
    }
}
