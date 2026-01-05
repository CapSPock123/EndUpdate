package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.item.custom.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        Entity sourceEntity = event.getSource().getDirectEntity();
        if(event.getEntity() instanceof Sheep sheep && sourceEntity instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " JUST HIT A SHEEP WITH AN END ROD! YOU SICK FRICK!!!!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 4));
                player.getMainHandItem().shrink(1);
            }
        }

        if(event.getEntity() instanceof ServerPlayer player && isWearingFullEndersteel(player) && player.getHealth() <= player.getMaxHealth() * 0.5 && Math.random() <= 0.5 &&
                sourceEntity instanceof LivingEntity && player.getInventory().getArmor(3).getItem() instanceof EndersteelArmorItem endersteelArmorItem && endersteelArmorItem.cooldown == 0) {
            endersteelArmorItem.isEchoScheduled = true;
            endersteelArmorItem.amount = event.getAmount();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Item mainHandItem = player.getMainHandItem().getItem();
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if(mainHandItem instanceof EndersteelHoeItem endersteelHoeItem && blockState.is(BlockTags.CROPS) &&
                !(blockState.getBlock() instanceof StemBlock) && !(blockState.getBlock() instanceof PitcherCropBlock)) {
            if(((CropBlock) block).isMaxAge(blockState)) {
                endersteelHoeItem.isEchoScheduled = true;
                endersteelHoeItem.blockState = blockState;
                endersteelHoeItem.player = player;
                endersteelHoeItem.blockPos = blockPos;
            }
        } else if(mainHandItem instanceof EndersteelAxeItem endersteelAxeItem) {
            if(blockState.is(BlockTags.LOGS) && endersteelAxeItem.isEchoOn()) {
                int blocksBroken = 1;
                List<BlockPos> positionsToCheck = new ArrayList<>();
                List<BlockPos> savedPositions = new ArrayList<>();
                List<BlockPos> positionsToBreak = new ArrayList<>();
                savedPositions.add(blockPos);
                while(!savedPositions.isEmpty()) {
                    BlockPos.betweenClosed(savedPositions.get(0).below().south().west(), savedPositions.get(0).above().north().east())
                            .forEach(position -> positionsToCheck.add(position.immutable()));
                    savedPositions.remove(0);
                    for(BlockPos pos : positionsToCheck) {
                        if(level.getBlockState(pos).is(BlockTags.LOGS) && !positionsToBreak.contains(pos)) {
                            savedPositions.add(pos);
                            positionsToBreak.add(pos);
                        }
                    }
                    positionsToCheck.clear();
                }
                for(BlockPos pos : positionsToBreak) {
                    if (blocksBroken <= 27) {
                        endersteelAxeItem.toBreak.add(pos);
                        blocksBroken++;
                    }
                }
            }
        } else if(mainHandItem instanceof HammerItem hammerItem) {
            Level level1 = (Level) level;
            if (player instanceof ServerPlayer serverPlayer) {
                for (BlockPos pos : hammerItem.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                    if (pos == blockPos || !hammerItem.isCorrectToolForDrops(mainHandItem.getDefaultInstance(), event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    BlockState state1 = level.getBlockState(pos);
                    level1.destroyBlock(pos, serverPlayer.gameMode.isSurvival(), serverPlayer);
                    serverPlayer.awardStat(Stats.BLOCK_MINED.get(block));

                    if (hammerItem instanceof EndersteelHammerItem endersteelHammerItem && state1.is(Tags.Blocks.ORES) && block != Blocks.ANCIENT_DEBRIS) {
                        endersteelHammerItem.checkedBlocks.add(pos);
                        endersteelHammerItem.checkedBlocksBlockstates.add(state1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickAir(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        Item mainHandItem = player.getMainHandItem().getItem();
        if(player.isShiftKeyDown()) {
            if(mainHandItem instanceof EndersteelShovelItem endersteelShovelItem) {
                if(endersteelShovelItem.isEchoOn()) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    endersteelShovelItem.setEchoOn(false);
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);
                    endersteelShovelItem.setEchoOn(true);
                }
            } else if(mainHandItem instanceof EndersteelAxeItem endersteelAxeItem) {
                if(endersteelAxeItem.isEchoOn()) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    endersteelAxeItem.setEchoOn(false);
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);
                    endersteelAxeItem.setEchoOn(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEnderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        if(isWearingFullEndersteel(event.getPlayer())) {
            event.setAttackDamage(0);
        }
    }

    private static boolean isWearingFullEndersteel(ServerPlayer player) {
        return player.getInventory().getArmor(0).getItem() == ModItems.ENDERSTEEL_BOOTS.get() &&
                player.getInventory().getArmor(1).getItem() == ModItems.ENDERSTEEL_LEGGINGS.get() &&
                player.getInventory().getArmor(2).getItem() == ModItems.ENDERSTEEL_CHESTPLATE.get() &&
                player.getInventory().getArmor(3).getItem() == ModItems.ENDERSTEEL_HELMET.get();
    }
}