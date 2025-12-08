package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ENDERANIUM_SMELTABLES = List.of(ModBlocks.ENDERANIUM_ORE.get(), ModItems.RAW_ENDERANIUM.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENDERANIUM_BLOCK.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERANIUM_INGOT.get()), has(ModItems.ENDERANIUM_INGOT.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERANIUM_INGOT.get(), 9)
                .requires(ModBlocks.ENDERANIUM_BLOCK.get())
                .unlockedBy(getHasName(ModItems.ENDERANIUM_INGOT.get()), has(ModItems.ENDERANIUM_INGOT.get()))
                .save(pWriter, EndUpdate.MOD_ID + ":enderanium_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERANIUM_INGOT.get())
                .requires(ModItems.ENDERANIUM_SHARD.get(), 4)
                .requires(Items.ENDER_EYE, 4)
                .requires(Items.DRAGON_BREATH)
                .unlockedBy(getHasName(ModItems.ENDERANIUM_SHARD.get()), has(ModItems.ENDERANIUM_SHARD.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENDERANIUM_INGOT.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERANIUM_NUGGET.get())
                .unlockedBy(getHasName(ModItems.ENDERANIUM_INGOT.get()), has(ModItems.ENDERANIUM_INGOT.get()))
                .save(pWriter, EndUpdate.MOD_ID + ":enderanium_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERANIUM_NUGGET.get(), 9)
                .requires(ModItems.ENDERANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERANIUM_INGOT.get()), has(ModItems.ENDERANIUM_INGOT.get()))
                .save(pWriter);

        oreSmelting(pWriter, ENDERANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERANIUM_SHARD.get(),
                3, 200, "enderanium");
        oreBlasting(pWriter, ENDERANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERANIUM_SHARD.get(),
                3, 100, "enderanium");
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, EndUpdate.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
