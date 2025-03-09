package net.capspock.endupdate.util;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_ENDERSTEEL_TOOL = createTag("needs_endersteel_tool");
        public static final TagKey<Block> INCORRECT_FOR_ENDERSTEEL_TOOL = createTag("incorrect_for_endersteel_tool");


        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> ALLOWS_SURFACE_ENDER_SLIME_SPAWNS = createTag("allows_surface_ender_slime_spawns");

        private static TagKey<Biome> createTag(String name) {
            return BiomeTags.create(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }
}
