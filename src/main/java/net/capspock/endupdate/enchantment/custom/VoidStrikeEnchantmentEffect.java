package net.capspock.endupdate.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.capspock.endupdate.effect.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.text.DecimalFormat;

public record VoidStrikeEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<VoidStrikeEnchantmentEffect> CODEC = MapCodec.unit(VoidStrikeEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        if(pEntity instanceof LivingEntity livingEntity) {
                double num = Math.random();
                DecimalFormat decimalFormat = new DecimalFormat("0.#");

                if (decimalFormat.format(num).equals("0.1")) {
                    livingEntity.addEffect(new MobEffectInstance(ModEffects.VOID_EFFECT.getHolder().get(), pEnchantmentLevel * 180, pEnchantmentLevel - 1));
                }
            }
        }
        @Override
        public MapCodec<? extends EnchantmentEntityEffect> codec () {
            return CODEC;
    }
}