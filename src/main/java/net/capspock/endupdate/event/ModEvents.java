package net.capspock.endupdate.event;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.core.particles.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static int tickCount = 0;
    private static boolean actionScheduled = false;

    private static LivingEntity entity;
    private static float amount;
    private static DamageSource source;

    @SubscribeEvent
    public static void onEndersteelSwordHit (LivingDamageEvent event) {
        if(event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == ModItems.ENDERSTEEL_SWORD.get()) {
                if(Math.random() <= 0.2) {
                tickCount = 0;
                entity = event.getEntity();
                amount = event.getAmount();
                source = event.getSource();
                actionScheduled = true;
                }
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.LevelTickEvent event) {
        tickCount++;

        if(tickCount >= 20) {
            if(actionScheduled) {
                entity.hurt(source, amount);
                ((ServerLevel) event.level).sendParticles(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), entity.getX() + 0.5,
                        entity.getY() + 1.5, entity.getZ() + 0.5, 1, 0, 0, 0, 1);
                tickCount = 0;
                actionScheduled = false;
            }
        }
    }
}
