package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ENDERSTEEL_SMELTABLES = List.of(ModBlocks.ENDERSTEEL_ORE.get(), ModItems.RAW_ENDERSTEEL.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
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

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDER_BOW.get())
                .pattern(" #S")
                .pattern("EAS")
                .pattern(" #S")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .define('S', Items.STRING)
                .define('E', Items.ENDER_EYE)
                .define('A', ModItems.AURORA_POWDER.get())
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

        netheriteSmithing(pWriter, ModItems.DIAMOND_ELYTRA_CHESTPLATE.get(), RecipeCategory.COMBAT, ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());
        smithingUpgrade(pWriter, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), Items.DIAMOND_CHESTPLATE, Items.ELYTRA, RecipeCategory.COMBAT, ModItems.DIAMOND_ELYTRA_CHESTPLATE.get());
        smithingUpgrade(pWriter, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERITE_CHESTPLATE, Items.ELYTRA, RecipeCategory.COMBAT, ModItems.NETHERITE_ELYTRA_CHESTPLATE.get(), EndUpdate.MOD_ID + ":netherite_elytra_chestplate_from_elytra_chestplate_upgrade_smithing_template");
        smithingUpgrade(pWriter, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), ModItems.ENDERSTEEL_CHESTPLATE.get(), Items.ELYTRA, RecipeCategory.COMBAT, ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE.get());
    }

    protected static void smithingUpgrade(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item pTemplateItem, Item pBaseItem, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(pTemplateItem), Ingredient.of(pBaseItem), Ingredient.of(pIngredientItem),
                pCategory, pResultItem).unlocks(getHasName(pIngredientItem), has(pIngredientItem)).save(pFinishedRecipeConsumer, EndUpdate.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
    }

    protected static void smithingUpgrade(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item pTemplateItem, Item pBaseItem, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem, String pRecipeId) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(pTemplateItem), Ingredient.of(pBaseItem), Ingredient.of(pIngredientItem),
                pCategory, pResultItem).unlocks(getHasName(pIngredientItem), has(pIngredientItem)).save(pFinishedRecipeConsumer, pRecipeId);
    }

    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, EndUpdate.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
