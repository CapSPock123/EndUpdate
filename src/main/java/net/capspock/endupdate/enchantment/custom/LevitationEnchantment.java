package net.capspock.endupdate.enchantment.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class LevitationEnchantment extends Enchantment {
    public LevitationEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    @Override
    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if(!pAttacker.level().isClientSide() && !pTarget.level().isClientSide()) {
            if(Math.random() < 0.2 && pTarget instanceof LivingEntity livingEntity && !livingEntity.hasEffect(MobEffects.LEVITATION)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 30, pLevel - 1));
            }
            super.doPostAttack(pAttacker, pTarget, pLevel);
        }
    }
}
