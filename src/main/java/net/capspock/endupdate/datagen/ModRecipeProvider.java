package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ENDERSTEEL_SMELTABLES = List.of(ModBlocks.ENDERSTEEL_ORE.get(), ModItems.RAW_ENDERSTEEL.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ENDERSTEEL_BLOCK.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get(), 9)
                .requires(ModBlocks.ENDERSTEEL_BLOCK.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pRecipeOutput, EndUpdate.MOD_ID + ":endersteel_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get())
                .requires(ModItems.ENDERSTEEL_SHARD.get(), 4)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.ENDER_EYE, 4)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_SHARD.get()), has(ModItems.ENDERSTEEL_SHARD.get()))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERSTEEL_NUGGET.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pRecipeOutput, EndUpdate.MOD_ID + ":endersteel_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_NUGGET.get(), 9)
                .requires(ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()))
                .save(pRecipeOutput);

        oreSmelting(pRecipeOutput, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL_SHARD.get(),
                3, 200, "endersteel");
        oreBlasting(pRecipeOutput, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL_SHARD.get(),
                3, 100, "endersteel");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_SWORD.get())
                .pattern("E")
                .pattern("E")
                .pattern("R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_PICKAXE.get())
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_SHOVEL.get())
                .pattern("E")
                .pattern("R")
                .pattern("R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_AXE.get())
                .pattern("EE")
                .pattern("ER")
                .pattern(" R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_HOE.get())
                .pattern("EE")
                .pattern(" R")
                .pattern(" R")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDERSTEEL_HAMMER.get())
                .pattern("BBB")
                .pattern(" R ")
                .pattern(" R ")
                .define('B', ModBlocks.ENDERSTEEL_BLOCK.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDER_BOW.get())
                .pattern(" #S")
                .pattern("EAS")
                .pattern(" #S")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .define('S', Items.STRING)
                .define('E', Items.ENDER_EYE)
                .define('A', ModItems.AURORA_POWDER.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_HELMET.get())
                .pattern("###")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_CHESTPLATE.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_LEGGINGS.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDERSTEEL_BOOTS.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), 2)
                .pattern("#T#")
                .pattern("#P#")
                .pattern("###")
                .define('T', ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get())
                .define('#', Items.END_STONE)
                .define('P', Items.PHANTOM_MEMBRANE)
                .unlockedBy(getHasName(ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get()),
                        has(ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SLINGSHOT.get())
                .pattern("SRS")
                .pattern("#S#")
                .pattern(" S ")
                .define('#', ModItems.ENDER_SLIMEBALL.get())
                .define('S', Items.STICK)
                .define('R', Items.STRING)
                .unlockedBy(getHasName(ModItems.ENDER_SLIMEBALL.get()), has(ModItems.ENDER_SLIMEBALL.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.ENDER_SLIME_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ENDER_SLIMEBALL.get())
                .unlockedBy(getHasName(ModItems.ENDER_SLIMEBALL.get()), has(ModItems.ENDER_SLIMEBALL.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ENDER_SLIME_BOOTS.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.ENDER_SLIMEBALL.get())
                .unlockedBy(getHasName(ModItems.ENDER_SLIMEBALL.get()), has(ModItems.ENDER_SLIMEBALL.get())).save(pRecipeOutput);

        netheriteSmithing(pRecipeOutput, ModItems.DIAMOND_ELYTRA_CHESTPLATE.get(), RecipeCategory.COMBAT, ModItems.NETHERITE_ELYTRA_CHESTPLATE.get());
        smithingUpgrade(pRecipeOutput, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), Items.DIAMOND_CHESTPLATE, Items.ELYTRA, RecipeCategory.COMBAT, ModItems.DIAMOND_ELYTRA_CHESTPLATE.get());
        smithingUpgrade(pRecipeOutput, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), Items.NETHERITE_CHESTPLATE, Items.ELYTRA, RecipeCategory.COMBAT, ModItems.NETHERITE_ELYTRA_CHESTPLATE.get(), EndUpdate.MOD_ID + ":netherite_elytra_chestplate_from_elytra_chestplate_upgrade_smithing_template");
        smithingUpgrade(pRecipeOutput, ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE.get(), ModItems.ENDERSTEEL_CHESTPLATE.get(), Items.ELYTRA, RecipeCategory.COMBAT, ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE.get());
    }

    protected static void smithingUpgrade(RecipeOutput recipeOutput, Item pTemplateItem, Item pBaseItem, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(pTemplateItem), Ingredient.of(pBaseItem), Ingredient.of(pIngredientItem),
                pCategory, pResultItem).unlocks(getHasName(pIngredientItem), has(pIngredientItem)).save(recipeOutput, EndUpdate.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
    }

    protected static void smithingUpgrade(RecipeOutput recipeOutput, Item pTemplateItem, Item pBaseItem, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem, String pRecipeId) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(pTemplateItem), Ingredient.of(pBaseItem), Ingredient.of(pIngredientItem),
                pCategory, pResultItem).unlocks(getHasName(pIngredientItem), has(pIngredientItem)).save(recipeOutput, pRecipeId);
    }

    protected static void oreSmelting(@NotNull RecipeOutput recipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull RecipeOutput recipeOutput, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(@NotNull RecipeOutput recipeOutput, @NotNull RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                     List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, EndUpdate.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
