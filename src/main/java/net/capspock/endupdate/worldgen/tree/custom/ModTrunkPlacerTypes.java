package net.capspock.endupdate.worldgen.tree.custom;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, EndUpdate.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<AbyssalTrunkPlacer>> ABYSSAL_TRUNK_PLACER =
            TRUNK_PLACER.register("abyssal_trunk_placer", () -> new TrunkPlacerType<>(AbyssalTrunkPlacer.CODEC));


    public static void register(IEventBus eventBus) {
        TRUNK_PLACER.register(eventBus);
    }
}
