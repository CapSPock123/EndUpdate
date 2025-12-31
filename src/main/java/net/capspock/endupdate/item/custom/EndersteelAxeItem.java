package net.capspock.endupdate.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class EndersteelAxeItem extends AxeItem {
    private boolean isEchoOn = false;
    public List<BlockPos> toBreak = new ArrayList<>();

    public EndersteelAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public boolean isEchoOn() {
        return isEchoOn;
    }

    public void setEchoOn(boolean echoOn) {
        isEchoOn = echoOn;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide()) {
            if(!toBreak.isEmpty()) {
                pLevel.destroyBlock(toBreak.get(0), !((Player) pEntity).isCreative(), pEntity);
                toBreak.remove(0);
            }
        }
    }
}
