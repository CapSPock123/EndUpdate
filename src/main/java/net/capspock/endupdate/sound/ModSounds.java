package net.capspock.endupdate.sound;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
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

    public static final ForgeSoundType ENDER_BOG_SOUNDS = new ForgeSoundType(1f, 1f,
            () -> SoundEvents.FROGLIGHT_BREAK, () -> SoundEvents.SLIME_BLOCK_STEP,  () -> SoundEvents.FROGLIGHT_PLACE,
            () -> SoundEvents.FROGLIGHT_HIT, () -> SoundEvents.SLIME_BLOCK_FALL);

    private static RegistryObject<SoundEvent> registerSoundEvents(String string) {
        return SOUD_EVENTS.register(string, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, string)));
    }

    public static void register(IEventBus eventBus) {
        SOUD_EVENTS.register(eventBus);
    }
}
