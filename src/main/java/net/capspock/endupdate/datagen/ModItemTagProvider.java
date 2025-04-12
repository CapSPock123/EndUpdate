package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.AURORA_ASHES.get())
                .add(Items.LAPIS_LAZULI)
                .add(Items.IRON_BLOCK);

        tag(ItemTags.HEAD_ARMOR)
                .add(ModItems.SHULKER_HELMET.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_HELMET.get());

        tag(ItemTags.CHEST_ARMOR)
                .add(ModItems.SHULKER_CHESTPLATE.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_CHESTPLATE.get())
                .add(ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());

        tag(ItemTags.LEG_ARMOR)
                .add(ModItems.SHULKER_LEGGINGS.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_LEGGINGS.get());

        tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.SHULKER_BOOTS.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_BOOTS.get())
                .add(ModItems.ENDER_SLIME_BOOTS.get());

        tag(ItemTags.SWORDS)
                .add(ModItems.ENDERSTEEL_SWORD.get());

        tag(ItemTags.PICKAXES)
                .add(ModItems.IRON_HAMMER.get())
                .add(ModItems.DIAMOND_HAMMER.get())
                .add(ModItems.NETHERITE_HAMMER.get())
                .add(ModItems.ENDERSTEEL_HAMMER.get())
                .add(ModItems.ENDERSTEEL_PICKAXE.get());

        tag(ItemTags.AXES)
                .add(ModItems.ENDERSTEEL_AXE.get());

        tag(ItemTags.SHOVELS)
                .add(ModItems.ENDERSTEEL_SHOVEL.get());

        tag(ItemTags.HOES)
                .add(ModItems.ENDERSTEEL_HOE.get());

        tag(ItemTags.BOW_ENCHANTABLE)
                .add(ModItems.ENDER_BOW.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.SLINGSHOT.get());

        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.SHULKER_HELMET.get())
                .add(ModItems.SHULKER_CHESTPLATE.get())
                .add(ModItems.SHULKER_LEGGINGS.get())
                .add(ModItems.SHULKER_BOOTS.get())

                .add(ModItems.SHULKER_PLATED_NETHERITE_HELMET.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_CHESTPLATE.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_LEGGINGS.get())
                .add(ModItems.SHULKER_PLATED_NETHERITE_BOOTS.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ENDERSTEEL_INGOT.get());

        tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.SHULKER_SMITHING_TEMPLATE.get());

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ABYSSAL_LOG.get().asItem())
                .add(ModBlocks.ABYSSAL_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ABYSSAL_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ABYSSAL_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.ABYSSAL_PLANKS.get().asItem());

        tag(ItemTags.SAPLINGS)
                .add(ModBlocks.ABYSSAL_SAPLING.get().asItem());


        tag(ItemTags.WOODEN_STAIRS)
                .add(ModBlocks.ABYSSAL_STAIRS.get().asItem());

        tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ABYSSAL_SLAB.get().asItem());

        tag(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.ABYSSAL_BUTTON.get().asItem());

        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ABYSSAL_PRESSURE_PLATE.get().asItem());

        tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ABYSSAL_FENCE.get().asItem());

        tag(ItemTags.FENCE_GATES)
                .add(ModBlocks.ABYSSAL_FENCE_GATE.get().asItem());

        tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ABYSSAL_DOOR.get().asItem());

        tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ABYSSAL_TRAPDOOR.get().asItem());

        tag(ModTags.Items.SLINGSHOT_PROJECTILES)
                .add(Items.FIRE_CHARGE)
                .add(ModItems.ENDER_SLIMEBALL.get());

        tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ModItems.ENDERSTEEL_INGOT.get());
    }
}
