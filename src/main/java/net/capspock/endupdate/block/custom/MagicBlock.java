package net.capspock.endupdate.block.custom;

import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class MagicBlock extends Block {
    public MagicBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos,
                                    Player pPlayer, BlockHitResult pHit) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 5f, 1f);
            return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(pEntity instanceof ItemEntity itemEntity) {
            if(isValidItem(itemEntity.getItem())) {
                itemEntity.setItem(new ItemStack(Items.DIAMOND, itemEntity.getItem().getCount()));
            }

            if(itemEntity.getItem().getItem() == Items.BARRIER) {
                itemEntity.setItem(new ItemStack(Items.RABBIT, itemEntity.getItem().getCount()));
            }

            if(itemEntity.getItem().getItem() == Items.WITHER_SKELETON_SKULL) {
                itemEntity.setItem(new ItemStack(Items.NETHERITE_SCRAP, itemEntity.getItem().getCount()));
            }

            if(itemEntity.getItem().getItem() == Items.HONEYCOMB) {
                itemEntity.setItem(new ItemStack(Items.RAW_GOLD));
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    private boolean isValidItem(ItemStack item) {
        return item.is(ModTags.Items.TRANSFORMABLE_ITEMS);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext appendHoverText, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.endupdate.magic_block.tooltip"));
        pTooltip.add(Component.translatable("tooltip.endupdate.magic_block.tooltip2"));
        super.appendHoverText(pStack, appendHoverText, pTooltip, pFlag);
    }
}
