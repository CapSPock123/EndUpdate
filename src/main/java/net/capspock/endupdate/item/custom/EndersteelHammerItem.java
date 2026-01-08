package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class EndersteelHammerItem extends HammerItem {
    private int tickCount = 0;
    public boolean isEchoScheduled = false;
    public final List<BlockPos> checkedBlocks = new ArrayList<>();
    public final List<BlockPos> confirmedBlocks = new ArrayList<>();
    public final List<BlockState> checkedBlocksBlockstates = new ArrayList<>();

    public EndersteelHammerItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, TagKey<Block> pBlocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if(!isEchoScheduled && !checkedBlocks.isEmpty()) {
            isEchoScheduled = true;
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide()) {
            if(isEchoScheduled) {
                tickCount++;
                if(tickCount == 1) {
                    for(BlockPos pos : checkedBlocks) {
                        if(Math.random() < 0.2) {
                            confirmedBlocks.add(pos);
                        } else {
                            checkedBlocksBlockstates.remove(0);
                        }
                    }
                }

                if(tickCount == 5 && !confirmedBlocks.isEmpty()) {
                    for(BlockPos pos : confirmedBlocks) {
                        pLevel.setBlock(pos, checkedBlocksBlockstates.get(0), 3);
                        checkedBlocksBlockstates.remove(0);
                    }
                }

                if(tickCount >= 6) {
                    Player player = (Player) pEntity;
                    for(BlockPos pos : confirmedBlocks) {
                        pLevel.destroyBlock(pos, !player.isCreative(), player, 512);
                        ((ServerLevel) pLevel).sendParticles(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5,
                                pos.getY() + 0.5, pos.getZ() + 0.5, 15, 0.1, 0.1, 0.1, 1);
                        pLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.ENDERSTEEL_ECHO_ACTIVATE.get(), SoundSource.BLOCKS, 2, 1);
                    }

                    isEchoScheduled = false;
                    tickCount = 0;
                    checkedBlocks.clear();
                    checkedBlocksBlockstates.clear();
                    confirmedBlocks.clear();
                }
            }
        }
    }
}
