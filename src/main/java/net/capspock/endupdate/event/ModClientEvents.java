package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem()) {
            if(event.getPlayer().getUseItem().getItem() == ModItems.ENDER_BOW.get() || event.getPlayer().getUseItem().getItem() == ModItems.SLINGSHOT.get()) {
                float fovModifier = 1f;
                int ticksUsingItem = event.getPlayer().getTicksUsingItem();
                float deltaTicks = (float)ticksUsingItem / 20f;
                if(deltaTicks > 1f) {
                    deltaTicks = 1f;
                } else {
                    deltaTicks *= deltaTicks;
                }
                fovModifier *= 1f - deltaTicks * 0.15f;
                event.setNewFovModifier(fovModifier);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent livingDamageEvent) {
        if(livingDamageEvent.getEntity() instanceof Sheep sheep && livingDamageEvent.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " JUST HIT A SHEEP WITH AN END ROD! YOU SICK FRICK!!!!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 4));
                player.getMainHandItem().shrink(1);
            }
        }
    }
}
