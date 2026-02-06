package net.capspock.endupdate.block.custom;

import com.mojang.serialization.MapCodec;
import net.capspock.endupdate.block.entity.ModBlockEntities;
import net.capspock.endupdate.block.entity.custom.EnderSlimeGeyserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnderSlimeGeyserBlock extends BaseEntityBlock {
    public static final MapCodec<EnderSlimeGeyserBlock> CODEC = simpleCodec(EnderSlimeGeyserBlock::new);
    private boolean eruptionScheduled = false;
    private EruptionType eruptionType = null;
    private double random = Math.random();

    public EnderSlimeGeyserBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new EnderSlimeGeyserBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null :
                createTickerHelper(pBlockEntityType, ModBlockEntities.ENDER_SLIME_GEYSER_BE.get(),
                        (level, blockPos, blockState, enderSlimeGeyserBlockEntity) ->
                        enderSlimeGeyserBlockEntity.tick(level ,blockPos, blockState));
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel,
                           @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if(!eruptionScheduled && pLevel.getBlockState(pPos.above()).isAir()) {
            random = Math.random();
            eruptionScheduled = true;
            if(random <= 0.5) {
                eruptionType = EruptionType.ENDER_SLIME_ENTITY;
            } else {
                eruptionType = EruptionType.ENDER_SLIMEBALL_PROJECTILE;
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        ItemStack itemStack = player.getMainHandItem();
        if(!itemStack.isCorrectToolForDrops(state) ||
                !(itemStack.getItem().getEnchantmentLevel(itemStack, Enchantments.SILK_TOUCH) > 0)) {
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 2f, false, Level.ExplosionInteraction.BLOCK);
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    public boolean isEruptionScheduled() {
        return eruptionScheduled;
    }

    public void setEruptionScheduled(boolean eruptionScheduled) {
        this.eruptionScheduled = eruptionScheduled;
    }

    public EruptionType getEruptionType() {
        return eruptionType;
    }

    public void setEruptionType(EruptionType eruptionType) {
        this.eruptionType = eruptionType;
    }

    public double getRandom() {
        return random;
    }
}
