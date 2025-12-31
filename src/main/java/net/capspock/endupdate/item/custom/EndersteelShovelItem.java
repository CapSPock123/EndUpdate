package net.capspock.endupdate.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class EndersteelShovelItem extends HammerItem {
    private boolean isEchoOn = false;

    public EndersteelShovelItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
    }

    public boolean isEchoOn() {
        return isEchoOn;
    }

    public void setEchoOn(boolean echoOn) {
        isEchoOn = echoOn;
    }

    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();
        return isEchoOn ? super.getBlocksToBeDestroyed(range, initalBlockPos, player) : positions;
    }
}
