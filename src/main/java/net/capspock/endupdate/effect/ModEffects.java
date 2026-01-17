package net.capspock.endupdate.effect;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.effect.custom.StickyEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EndUpdate.MOD_ID);

    public static final RegistryObject<MobEffect> STICKY_EFFECT = MOB_EFFECTS.register("sticky",
            () -> new StickyEffect(MobEffectCategory.HARMFUL,  0x9D00FF)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, String.valueOf(UUID.nameUUIDFromBytes("endupdate:sticky".getBytes())),
                            -0.6f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, String.valueOf(UUID.nameUUIDFromBytes("endupdate:sticky".getBytes())),
                            -0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
