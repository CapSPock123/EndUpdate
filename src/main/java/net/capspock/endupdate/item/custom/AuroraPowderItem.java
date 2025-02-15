package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.component.ModDataComponentTypes;
import net.capspock.endupdate.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class AuroraPowderItem extends Item {
    private static final Map<Block, Block> AURORA_POWDER_MAP =
            Map.of(
                    Blocks.EMERALD_BLOCK, ModBlocks.MAGIC_BLOCK.get()
            );

    public AuroraPowderItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(AURORA_POWDER_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                Player player = pContext.getPlayer();
                level.setBlockAndUpdate(pContext.getClickedPos(), AURORA_POWDER_MAP.get(clickedBlock).defaultBlockState());
                level.playSound(null, pContext.getClickedPos(), ModSounds.AURORA_POWDER_USE.get(), SoundSource.BLOCKS);
                pContext.getItemInHand().set(ModDataComponentTypes.COORDINATES.get(), pContext.getClickedPos());
                if(player instanceof ServerPlayer serverPlayer) {
                    if(serverPlayer.gameMode.isSurvival()) {
                        pContext.getItemInHand().shrink(1);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext appendHoverText, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.endupdate.aurora_powder.shift_down.tooltip"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.endupdate.aurora_powder.tooltip"));
        }

        if(pStack.get(ModDataComponentTypes.COORDINATES.get()) != null) {
            pTooltipComponents.add(Component.literal("Last Block transformed at " + pStack.get(ModDataComponentTypes.COORDINATES.get())));
        }

        super.appendHoverText(pStack, appendHoverText, pTooltipComponents, pIsAdvanced);
    }
}
