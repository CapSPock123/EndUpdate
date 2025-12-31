package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EndersteelSwordItem extends SwordItem {
    private static int tickCount = 0;
    private static boolean isEchoScheduled = false;
    private static LivingEntity entity;
    private static Player player;

    public EndersteelSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player pPlayer, Entity pEntity) {
        if(pEntity instanceof LivingEntity livingEntity && !isEchoScheduled && !livingEntity.level().isClientSide && Math.random() <= 0.2) {
            isEchoScheduled = true;
            tickCount = 0;
            entity = livingEntity;
            player = pPlayer;
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(!pLevel.isClientSide()) {
            tickCount++;
            if(isEchoScheduled) {
                if(tickCount == 10) {
                    float baseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    float damageBonus = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), entity.getMobType());
                    if(baseDamage > 0f || damageBonus > 0f) {
                        float baseKnockback = (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                        baseKnockback += EnchantmentHelper.getKnockbackBonus(player);
                        if (player.isSprinting()) {
                            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.0F, 1.0F);
                            ++baseKnockback;
                        }
                        baseDamage += damageBonus;
                        float originalEntityHealth = entity.getHealth();
                        Vec3 vec3 = entity.getDeltaMovement();
                        boolean flag5 = entity.hurt(entity.damageSources().fellOutOfWorld(), baseDamage);
                        if (flag5) {
                            if (baseKnockback > 0) {
                                entity.knockback(baseKnockback * 0.5F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                                player.setDeltaMovement(player.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                                player.setSprinting(false);
                            }
                            entity.knockback(0.4F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                            entity.hurt(entity.damageSources().fellOutOfWorld(), baseDamage * 0.5f);
                            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                            double d1 = (-Mth.sin(entity.getYRot() * ((float)Math.PI / 180F)));
                            double d2 = Mth.cos(entity.getYRot() * ((float)Math.PI / 180F));
                            if (pLevel instanceof ServerLevel) {
                                ((ServerLevel) pLevel).sendParticles(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), entity.getX() + d1, entity.getY(0.5D), entity.getZ() + d2, 0, d1, 0.0D, d2, 0.0D);
                            }
                            if (entity instanceof ServerPlayer && entity.hurtMarked) {
                                ((ServerPlayer) entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
                                entity.hurtMarked = false;
                                entity.setDeltaMovement(vec3);
                            }
                            player.setLastHurtMob(entity);
                            EnchantmentHelper.doPostHurtEffects(entity, player);
                            EnchantmentHelper.doPostDamageEffects(player, entity);
                            ItemStack itemstack1 = player.getMainHandItem();
                            if (!pLevel.isClientSide && !itemstack1.isEmpty()) {
                                itemstack1.hurtEnemy(entity, player);
                            }
                            float f5 = originalEntityHealth - entity.getHealth();
                            player.awardStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                        } else {
                            pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
                        }
                    }
                    entity.invulnerableTime = 0;
                    isEchoScheduled = false;
                }
            }
        }
    }
}
