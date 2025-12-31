package net.capspock.endupdate.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class EndersteelPickaxeItem extends PickaxeItem {
    private int tickCount = 0;
    public boolean isEchoScheduled = false;
    public BlockState blockState = null;
    public BlockPos blockPos = null;
    public LevelAccessor levelAccessor = null;

    public EndersteelPickaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide()) {
            if(isEchoScheduled) {
                tickCount++;
                if(tickCount == 5) {
                    levelAccessor.setBlock(blockPos, blockState, 3);
                }

                if(tickCount == 6) {
                    Player player = (Player) pEntity;
                    levelAccessor.destroyBlock(blockPos, !player.isCreative(), player, 512);
                    ((ServerLevel) pLevel).sendParticles(ParticleTypes.REVERSE_PORTAL, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5,
                            15, 0.1, 0.1, 0.1, 1);
                    pLevel.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 2, 5);

                    isEchoScheduled = false;
                    tickCount = 0;
                }
            }
        }
    }
}
