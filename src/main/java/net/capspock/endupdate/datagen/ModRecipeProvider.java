package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(pOutput, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> ENDERSTEEL_SMELTABLES = List.of(ModItems.RAW_ENDERSTEEL.get(),
                ModBlocks.ENDERSTEEL_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ENDERSTEEL_BLOCK.get())
                .pattern("EEE")
                .pattern("EEE")
                .pattern("EEE")
                .define('E', ModItems.ENDERSTEEL_INGOT.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHULKER_PLATE.get())
                .pattern("BSB")
                .pattern("SES")
                .pattern("OSO")
                .define('E', Items.ENDER_EYE)
                .define('B', Items.END_STONE_BRICKS)
                .define('S', Items.SHULKER_SHELL)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(Items.SHULKER_SHELL), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHULKER_SMITHING_TEMPLATE.get(), 2)
                .pattern("DTD")
                .pattern("DSD")
                .pattern("DDD")
                .define('T', ModItems.SHULKER_SMITHING_TEMPLATE.get())
                .define('D', Items.DIAMOND)
                .define('S', Items.SHULKER_SHELL)
                .unlockedBy(getHasName(ModItems.SHULKER_SMITHING_TEMPLATE.get()), has(ModItems.SHULKER_SMITHING_TEMPLATE.get())).save(pRecipeOutput);


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
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', ModBlocks.ENDERSTEEL_BLOCK.get())
                .define('R', Items.END_ROD)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_HAMMER.get())
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', Blocks.IRON_BLOCK)
                .define('R', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Blocks.IRON_BLOCK)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_HAMMER.get())
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', Blocks.DIAMOND_BLOCK)
                .define('R', Items.STICK)
                .unlockedBy(getHasName(Items.DIAMOND_BLOCK), has(Blocks.DIAMOND_BLOCK)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.NETHERITE_HAMMER.get())
                .pattern("EEE")
                .pattern(" R ")
                .pattern(" R ")
                .define('E', Blocks.NETHERITE_BLOCK)
                .define('R', Items.STICK)
                .unlockedBy(getHasName(Items.NETHERITE_BLOCK), has(Blocks.NETHERITE_BLOCK)).save(pRecipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AURORA_LAMP.get())
                .pattern(" S ")
                .pattern("SBS")
                .pattern(" S ")
                .define('B', Items.REDSTONE_LAMP)
                .define('S', ModItems.AURORA_ASHES.get())
                .unlockedBy(getHasName(ModItems.AURORA_ASHES.get()), has(ModItems.AURORA_ASHES.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHULKER_HELMET.get())
                .pattern("SSS")
                .pattern("S S")
                .define('S', ModItems.SHULKER_PLATE.get())
                .unlockedBy(getHasName(ModItems.SHULKER_PLATE.get()), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHULKER_CHESTPLATE.get())
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SHULKER_PLATE.get())
                .unlockedBy(getHasName(ModItems.SHULKER_PLATE.get()), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHULKER_LEGGINGS.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.SHULKER_PLATE.get())
                .unlockedBy(getHasName(ModItems.SHULKER_PLATE.get()), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHULKER_BOOTS.get())
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.SHULKER_PLATE.get())
                .unlockedBy(getHasName(ModItems.SHULKER_PLATE.get()), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ENDER_BOW.get())
                .pattern(" #S")
                .pattern("OAS")
                .pattern(" #S")
                .define('S', Items.STRING)
                .define('#', ModItems.ENDERSTEEL_INGOT.get())
                .define('O', ModItems.SHULKER_PLATE.get())
                .define('A', ModItems.AURORA_POWDER.get())
                .unlockedBy(getHasName(ModItems.ENDERSTEEL_INGOT.get()), has(ModItems.ENDERSTEEL_INGOT.get()) ).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ABYSSAL_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.ABYSSAL_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_LOG.get()), has(ModBlocks.ABYSSAL_LOG.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_ABYSSAL_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.STRIPPED_ABYSSAL_LOG.get())
                .unlockedBy(getHasName(ModBlocks.STRIPPED_ABYSSAL_LOG.get()), has(ModBlocks.STRIPPED_ABYSSAL_LOG.get())).save(pRecipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AURORA_POWDER.get())
                .requires(ModItems.AURORA_ASHES.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.FLINT)
                .unlockedBy(getHasName(ModItems.AURORA_ASHES.get()), has(ModItems.AURORA_ASHES.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get())
                .requires(ModItems.ENDERSTEEL.get())
                .requires(ModItems.ENDERSTEEL.get())
                .requires(ModItems.ENDERSTEEL.get())
                .requires(ModItems.ENDERSTEEL.get())
                .requires(Items.ENDER_EYE)
                .requires(Items.ENDER_EYE)
                .requires(Items.ENDER_EYE)
                .requires(Items.ENDER_EYE)
                .unlockedBy(getHasName(ModItems.ENDERSTEEL.get()), has(ModItems.ENDERSTEEL.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENDERSTEEL_INGOT.get(), 9)
                .requires(ModBlocks.ENDERSTEEL_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ENDERSTEEL_BLOCK.get()), has(ModBlocks.ENDERSTEEL_BLOCK.get())).save(pRecipeOutput, EndUpdate.MOD_ID + ":endersteel_from_endersteel_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ABYSSAL_PLANKS.get(), 4)
                .requires(ModBlocks.ABYSSAL_LOG.get())
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_LOG.get()), has(ModBlocks.ABYSSAL_LOG.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ABYSSAL_PLANKS.get(), 4)
                .requires(ModBlocks.ABYSSAL_WOOD.get())
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_WOOD.get()), has(ModBlocks.ABYSSAL_WOOD.get())).save(pRecipeOutput, EndUpdate.MOD_ID + ":abyssal_planks_from_wood");

        oreSmelting(pRecipeOutput, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL.get(), 3f, 200, "endersteel");
        oreBlasting(pRecipeOutput, ENDERSTEEL_SMELTABLES, RecipeCategory.MISC, ModItems.ENDERSTEEL.get(), 3f, 100, "endersteel");

        shulkerSmithing(pRecipeOutput, Items.NETHERITE_HELMET, RecipeCategory.MISC, ModItems.SHULKER_PLATED_NETHERITE_HELMET.get());
        shulkerSmithing(pRecipeOutput, Items.NETHERITE_CHESTPLATE, RecipeCategory.MISC, ModItems.SHULKER_PLATED_NETHERITE_CHESTPLATE.get());
        shulkerSmithing(pRecipeOutput, Items.NETHERITE_LEGGINGS, RecipeCategory.MISC, ModItems.SHULKER_PLATED_NETHERITE_LEGGINGS.get());
        shulkerSmithing(pRecipeOutput, Items.NETHERITE_BOOTS, RecipeCategory.MISC, ModItems.SHULKER_PLATED_NETHERITE_BOOTS.get());

        trimSmithing(pRecipeOutput, ModItems.SHULKER_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "ender"));

        stairBuilder(ModBlocks.ABYSSAL_STAIRS.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ABYSSAL_SLAB.get(), ModBlocks.ABYSSAL_PLANKS.get());
        buttonBuilder(ModBlocks.ABYSSAL_BUTTON.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
        pressurePlate(pRecipeOutput, ModBlocks.ABYSSAL_PRESSURE_PLATE.get(), ModBlocks.ABYSSAL_PLANKS.get());
        fenceBuilder(ModBlocks.ABYSSAL_FENCE.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
        fenceGateBuilder(ModBlocks.ABYSSAL_FENCE_GATE.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
        doorBuilder(ModBlocks.ABYSSAL_DOOR.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
        trapdoorBuilder(ModBlocks.ABYSSAL_TRAPDOOR.get(), Ingredient.of(ModBlocks.ABYSSAL_PLANKS.get())).group("abyssal")
                .unlockedBy(getHasName(ModBlocks.ABYSSAL_PLANKS.get()), has(ModBlocks.ABYSSAL_PLANKS.get())).save(pRecipeOutput);
    }

    protected static void shulkerSmithing(RecipeOutput pRecipeOutput, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}), Ingredient.of(new ItemLike[]{pIngredientItem}), Ingredient.of(new ItemLike[]{ModItems.SHULKER_PLATE.get()}), pCategory, pResultItem)
                .unlocks(getHasName(ModItems.SHULKER_PLATE.get()), has(ModItems.SHULKER_PLATE.get())).save(pRecipeOutput, getItemName(pResultItem) + "_smithing");
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE,  SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, EndUpdate.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
