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
    private static final List<ItemLike> ENDERSTEEL_SMELTABLES = List.of(ModBlocks.ENDERSTEEL_ORE.get(), ModItems.RAW_ENDERSTEEL.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENDERSTEEL_BLOCK.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get(), 9)
                .requires(ModBlocks.ENDERSTEEL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pWriter, EndUpdate.MOD_ID + ":endersteel_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get())
                .requires(ModItems.ENDERSTEEL_SHARD.get(), 4)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.ENDER_EYE, 4)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_SHARD.get()), has(ModItems.ENDERSTEEL_SHARD.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERSTEEL_NUGGET.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pWriter, EndUpdate.MOD_ID + ":endersteel_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_NUGGET.get(), 9)
                .requires(ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pWriter);

        oreSmelting(pWriter, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL_SHARD.get(),
                3, 200, "endersteel");
        oreBlasting(pWriter, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL_SHARD.get(),
                3, 100, "endersteel");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_SWORD.get())
                .pattern("E")
                .pattern("E")
                .pattern("R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_PICKAXE.get())
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_SHOVEL.get())
                .pattern("E")
                .pattern("R")
                .pattern("R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_AXE.get())
                .pattern("EE")
                .pattern("ER")
                .pattern(" R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_HOE.get())
                .pattern("EE")
                .pattern(" R")
                .pattern(" R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_HAMMER.get())
                .pattern("BBB")
                .pattern(" R ")
                .pattern(" R ")
                .define('B', ModBlocks.ENDERSTEEL_BLOCK.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_HELMET.get())
                .pattern("###")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_CHESTPLATE.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_LEGGINGS.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_BOOTS.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pWriter);
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
