package net.capspock.endupdate.util;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_ENDERSTEEL_TOOL = createTag("needs_endersteel_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(new ResourceLocation(EndUpdate.MOD_ID, name));
        }
    }
}
