package net.capspock.endupdate.item.custom;

import com.google.common.collect.ImmutableMap;
import net.capspock.endupdate.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class EnderSlimeArmorItem extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.ENDER_SLIME,
                            List.of(new MobEffectInstance(MobEffects.JUMP, 50, 14, false, false)))
                    .build();

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if(!level.isClientSide() && hasBootsOn(player)) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for(Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapEffect = entry.getValue();

            if(hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().allMatch(effect -> player.hasEffect(effect.getEffect()));

        if(player.isShiftKeyDown()) {
            if(!hasPlayerEffect) {
                for (MobEffectInstance effect : mapEffect) {
                    player.addEffect(new MobEffectInstance(effect.getEffect(),
                            effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
                }
            }
        } else {
            for(MobEffectInstance effect : mapEffect)
                player.removeEffect(effect.getEffect());
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> mapArmorMaterial, Player player) {
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());

        return boots.getMaterial() == mapArmorMaterial;
    }

    private boolean hasBootsOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);

        return !boots.isEmpty();
    }

    public EnderSlimeArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }
}
