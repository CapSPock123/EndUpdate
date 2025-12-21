package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier ENDERSTEEL = TierSortingRegistry.registerTier(
            new ForgeTier(5, 3328, 12f, 5f, 25,
                    ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL, () -> Ingredient.of(ModItems.ENDERSTEEL_INGOT.get())),
            new ResourceLocation(EndUpdate.MOD_ID, "endersteel"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier ENDERSTEEL_BLOCK = TierSortingRegistry.registerTier(
            new ForgeTier(5, 3328, 12f, 5f, 25,
                    ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL, () -> Ingredient.of(ModBlocks.ENDERSTEEL_BLOCK.get())),
            new ResourceLocation(EndUpdate.MOD_ID, "endersteel_block"), List.of(Tiers.NETHERITE), List.of());
}
