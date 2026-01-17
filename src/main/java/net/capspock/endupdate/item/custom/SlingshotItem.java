package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.entity.custom.EnderSlimeballProjectile;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

import static net.minecraftforge.event.ForgeEventFactory.onArrowLoose;
import static net.minecraftforge.event.ForgeEventFactory.onArrowNock;

public class SlingshotItem extends ProjectileWeaponItem {
    public SlingshotItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return itemStack -> itemStack.is(ModTags.Items.SLINGSHOT_PROJECTILES);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();
        InteractionResultHolder<ItemStack> ret = onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;
        if (!pPlayer.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        if (pLivingEntity instanceof Player player) {
            ItemStack itemstack = player.getProjectile(pStack);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            boolean canInstabuild = player.getAbilities().instabuild;

            i = onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || player.getAbilities().instabuild);
            if (i < 0) return;
            float power = BowItem.getPowerForTime(i);

            if (!itemstack.isEmpty() || canInstabuild) {
                if (itemstack.isEmpty() || (itemstack.getItem() == Items.ARROW && canInstabuild)) {
                    itemstack = new ItemStack(ModItems.ENDER_SLIMEBALL.get());
                }

                if (!((double)power < 0.1D)) {
                    if (!pLevel.isClientSide) {
                        if(itemstack.getItem() instanceof EnderSlimeballItem enderSlimeballItem) {
                            EnderSlimeballProjectile enderSlimeball = enderSlimeballItem.createEnderSlimeball(pLevel, player);
                            enderSlimeball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, power * 3f, 1f);
                            pLevel.addFreshEntity(enderSlimeball);
                        } else if(itemstack.getItem() instanceof FireChargeItem) {
                            Vec3 lookAngle = player.getLookAngle();
                            SmallFireball fireball = new SmallFireball(pLevel, player, lookAngle.x, lookAngle.y, lookAngle.z);
                            fireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, power * 3f, 1f);
                            fireball.setPos(player.getEyePosition());
                            pLevel.addFreshEntity(fireball);
                        }

                        pStack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(player.getUsedItemHand()));
                    }

                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT,
                            SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                    if (!canInstabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
