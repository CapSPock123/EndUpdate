package net.capspock.endupdate.enchantment;

import com.mojang.serialization.MapCodec;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.enchantment.custom.LevitationEnchantmentEffect;
import net.capspock.endupdate.enchantment.custom.VoidStrikeEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, EndUpdate.MOD_ID);

    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LEVITATION =
            ENTITY_ENCHANTMENT_EFFECTS.register("levitation", () -> LevitationEnchantmentEffect.CODEC);

    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> VOID_STRIKE =
            ENTITY_ENCHANTMENT_EFFECTS.register("void_strike", () -> VoidStrikeEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
