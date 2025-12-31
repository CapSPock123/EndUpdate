package net.capspock.endupdate.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EndersteelHoeItem extends HoeItem {
    private int tickCount = 0;
    public boolean isEchoScheduled = false;
    public BlockState blockState = null;
    public Player player = null;
    public BlockPos blockPos = null;

    public EndersteelHoeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide()) {
            if(isEchoScheduled) {
                CropBlock cropBlock = (CropBlock) blockState.getBlock();
                tickCount++;
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.gameMode.isSurvival()) {
                        if (serverPlayer.getOffhandItem().is(cropBlock.getCloneItemStack(pLevel, blockPos, blockState).getItem())) {
                            serverPlayer.getOffhandItem().shrink(1);
                            if(tickCount == 1) {
                                pLevel.setBlock(blockPos, cropBlock.getStateForAge(0), 3);
                            }
                        }
                    } else {
                        if(tickCount == 1) {
                            pLevel.setBlock(blockPos, cropBlock.getStateForAge(0), 3);
                        }
                    }
                }
                isEchoScheduled = false;
                tickCount = 0;
            }
        }
    }
}
