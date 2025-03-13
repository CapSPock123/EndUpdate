package net.capspock.endupdate.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EnderSlimeBlock extends HalfTransparentBlock {

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {

    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounceUp(pEntity);
        }
    }

    private void bounceUp(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d0 = pEntity instanceof LivingEntity ? 1.4 : 1.2;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        double d0 = Math.abs(pEntity.getDeltaMovement().y);
        if (d0 < 0.1 && !pEntity.isSteppingCarefully()) {
            double d1 = 0.4 + d0 * 0.2;
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(d1, 1.0, d1));
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }


    public EnderSlimeBlock(Properties properties) {
        super(properties);
    }
}
