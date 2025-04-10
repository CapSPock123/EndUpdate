package net.capspock.endupdate.effect;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.effect.custom.SlimeyEffect;
import net.capspock.endupdate.effect.custom.StickyEffect;
import net.capspock.endupdate.effect.custom.VoidEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.w3c.dom.Attr;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EndUpdate.MOD_ID);

    public static final RegistryObject<MobEffect> VOID_EFFECT = MOB_EFFECTS.register("void",
            () -> new VoidEffect(MobEffectCategory.HARMFUL, 0x28003c)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "void"),
            -0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "void"),
                            -4.0, AttributeModifier.Operation.ADD_VALUE));

    public static final RegistryObject<MobEffect> STICKY_EFFECT = MOB_EFFECTS.register("sticky",
            () -> new StickyEffect(MobEffectCategory.HARMFUL,  0x9D00FF)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "sticky"),
                            -0.6f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "sticky"),
                            -0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    /*Texture from Forge TutorialMod 1.21.X by Kaupenjoe
    Distributed under the MIT License*/
    public static final RegistryObject<MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0xa159cb)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "slimey"),
                            -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "slimey"),
                            0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "slimey"),
                            1.0, AttributeModifier.Operation.ADD_VALUE));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
