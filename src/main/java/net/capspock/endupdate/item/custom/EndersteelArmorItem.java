package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.particle.ModParticles;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EndersteelArmorItem extends ArmorItem {
    public boolean isEchoScheduled = false;
    private boolean shouldStartCooldown = false;
    public int cooldown = 0;
    public float amount = 0;

    public EndersteelArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_armor.tooltip"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!level.isClientSide()) {
            if(isEchoScheduled && cooldown == 0) {
                ((ServerLevel) level).sendParticles(ModParticles.ECHO_SONIC_BOOM_PARTICLES.get(), player.getX() + 0.5, player.getY() + 1.5, player.getZ(), 1, 0.1, 0.1, 0.1, 1);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2, 1);
                List<LivingEntity> entities = new ArrayList<>();
                for(Entity entity : level.getEntities(player, AABB.ofSize(player.getEyePosition(), 5, 5, 5))) {
                    if(entity instanceof LivingEntity livingEntity) {
                        entities.add(livingEntity);
                    }
                }

                for(LivingEntity livingEntity : entities) {
                    livingEntity.hurt(livingEntity.damageSources().fellOutOfWorld(), amount);
                    livingEntity.knockback(2F, (-Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F))), Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F)));
                    double d1 = (-Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F)));
                    double d2 = Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F));
                    ((ServerLevel) level).sendParticles(ModParticles.ECHO_SWEEP_ATTACK_PARTICLES.get(), livingEntity.getX() + d1, livingEntity.getY(0.5D), livingEntity.getZ() + d2, 0, d1, 0.0D, d2, 0.0D);
                }

                isEchoScheduled = false;
                shouldStartCooldown = true;
                cooldown = 100;
            }

            if(shouldStartCooldown) {
                System.out.println("cooldown Time");
                cooldown--;

                if(cooldown == 0) {
                    shouldStartCooldown = false;
                }
            }
        }
    }
}
