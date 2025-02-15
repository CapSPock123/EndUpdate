package net.capspock.endupdate.potion;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, EndUpdate.MOD_ID);

    public static final RegistryObject<Potion> VOID_POTION = POTIONS.register("void_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.VOID_EFFECT.getHolder().get(), 300, 0)));

    public static final RegistryObject<Potion> LONG_VOID_POTION = POTIONS.register("long_void_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.VOID_EFFECT.getHolder().get(), 600, 0)));
    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
