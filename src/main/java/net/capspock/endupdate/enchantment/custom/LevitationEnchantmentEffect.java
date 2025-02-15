package net.capspock.endupdate.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;

public record LevitationEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LevitationEnchantmentEffect> CODEC = MapCodec.unit(LevitationEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        if(pEntity instanceof LivingEntity livingEntity) {
            if(!livingEntity.hasEffect(MobEffects.LEVITATION)) {
                double num = Math.random();
                DecimalFormat decimalFormat = new DecimalFormat("0.#");

                if (decimalFormat.format(num).equals("0.1") || decimalFormat.format(num).equals("0.2")) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, pEnchantmentLevel * 25, 0));
                }
            }
        }
    }
        @Override
        public MapCodec<? extends EnchantmentEntityEffect> codec () {
            return CODEC;
    }
}