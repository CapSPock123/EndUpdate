package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onComputerFovModifierEvent(ComputeFovModifierEvent event) {
        if(event.getPlayer().isUsingItem()) {
            if(event.getPlayer().getUseItem().getItem() == ModItems.ENDER_BOW.get()) {
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
}
