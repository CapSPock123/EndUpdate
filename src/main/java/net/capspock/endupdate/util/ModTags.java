package net.capspock.endupdate.util;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_ENDERSTEEL_TOOL = createTag("needs_endersteel_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ELYTRA_CHESTPLATE = createTag("elytra_chestplate");
        public static final TagKey<Item> SLINGSHOT_PROJECTILES = createTag("slingshot_projectiles");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> OUTER_END_ISLANDS = createTag("outer_end_islands");
        public static final TagKey<Biome> HAS_END_ALTAR = createTag("has_structure/has_end_altar");

        private static TagKey<Biome> createTag(String name) {
            return createBiomeTag(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, name));
        }
    }

    public static TagKey<Biome> createBiomeTag(ResourceLocation name) {
        return TagKey.create(Registries.BIOME, name);
    }
}
