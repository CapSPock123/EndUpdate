package net.capspock.endupdate.sound;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUD_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EndUpdate.MOD_ID);

    /*Sound from Forge TutorialMod 1.21.X by Kaupenjoe
    Distributed under the MIT License*/
    public static final RegistryObject<SoundEvent> ENDERSTEEL_ECHO_ACTIVATE = registerSoundEvents("endersteel_echo_activate");

    private static RegistryObject<SoundEvent> registerSoundEvents(String string) {
        return SOUD_EVENTS.register(string, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, string)));
    }

    public static void register(IEventBus eventBus) {
        SOUD_EVENTS.register(eventBus);
    }
}
