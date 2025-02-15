package net.capspock.endupdate.item;

import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {
    public static final Tier ENDERSTEEL = new ForgeTier(3328, 12, 5f, 24,
            ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL, () -> Ingredient.of(ModItems.ENDERSTEEL_INGOT.get()),
            ModTags.Blocks.INCORRECT_FOR_ENDERSTEEL_TOOL);
    public static final Tier ENDERSTEEL_BLOCK = new ForgeTier(3328, 12, 5f, 24,
            ModTags.Blocks.NEEDS_ENDERSTEEL_TOOL, () -> Ingredient.of(ModBlocks.ENDERSTEEL_BLOCK.get()),
            ModTags.Blocks.INCORRECT_FOR_ENDERSTEEL_TOOL);

    public static final Tier NETHERITE_BLOCK = new ForgeTier(2031, 9.0F, 4f, 15,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Blocks.NETHERITE_BLOCK),
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL);
    public static final Tier DIAMOND_BLOCK = new ForgeTier(1561, 8.0F, 3f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Blocks.DIAMOND_BLOCK),
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
    public static final Tier IRON_BLOCK = new ForgeTier(250, 6.0F, 2f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Blocks.IRON_BLOCK),
            BlockTags.INCORRECT_FOR_IRON_TOOL);
}
