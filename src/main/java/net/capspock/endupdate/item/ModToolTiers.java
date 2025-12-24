package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
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

    public static final Tier NETHERITE_BLOCK = TierSortingRegistry.registerTier(
            new ForgeTier(4, 3328, 12f, 4f, 15,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(Blocks.NETHERITE_BLOCK)),
            new ResourceLocation(EndUpdate.MOD_ID, "netherite_block"), List.of(Tiers.DIAMOND), List.of());

    public static final Tier DIAMOND_BLOCK = TierSortingRegistry.registerTier(
            new ForgeTier(3, 1561, 8f, 3f, 10,
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Blocks.DIAMOND_BLOCK)),
            new ResourceLocation(EndUpdate.MOD_ID, "diamond_block"), List.of(Tiers.IRON), List.of());

    public static final Tier IRON_BLOCK = TierSortingRegistry.registerTier(
            new ForgeTier(2, 250, 6f, 2f, 14,
                    BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Blocks.IRON_BLOCK)),
            new ResourceLocation(EndUpdate.MOD_ID, "iron_block"), List.of(Tiers.STONE), List.of());
}
