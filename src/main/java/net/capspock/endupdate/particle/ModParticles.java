package net.capspock.endupdate.particle;

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

    public static final RegistryObject<SimpleParticleType> ECHO_SWEEP_ATTACK_PARTICLES =
            PARTICLE_TYPES.register("echo_sweep_attack_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ECHO_SONIC_BOOM_PARTICLES =
            PARTICLE_TYPES.register("echo_sonic_boom_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
