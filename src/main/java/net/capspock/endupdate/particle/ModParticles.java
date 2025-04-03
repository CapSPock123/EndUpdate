package net.capspock.endupdate.particle;

import com.ibm.icu.text.MessagePattern;
import net.capspock.endupdate.EndUpdate;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EndUpdate.MOD_ID);

    public static final RegistryObject<SimpleParticleType> ITEM_ENDER_SLIME =
            PARTICLE_TYPES.register("item_ender_slime", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
