package net.capspock.endupdate.enchantment;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.enchantment.custom.LevitationEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EndUpdate.MOD_ID);

    public static final RegistryObject<Enchantment> LEVITATION = ENCHANTMENTS.register("levitation", LevitationEnchantment::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
