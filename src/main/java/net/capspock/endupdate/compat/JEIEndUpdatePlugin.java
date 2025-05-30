package net.capspock.endupdate.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.recipe.ModRecipes;
import net.capspock.endupdate.recipe.VoidInfuserRecipe;
import net.capspock.endupdate.screen.custom.VoidInfuserScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIEndUpdatePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new VoidInfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<VoidInfuserRecipe> voidInfuserRecipes = recipeManager.getAllRecipesFor(ModRecipes.VOID_INFUSER_TYPE.get())
                .stream().map(RecipeHolder::value).toList();

        registration.addRecipes(VoidInfuserRecipeCategory.VOID_INFUSER_RECIPE_RECIPE_TYPE, voidInfuserRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(VoidInfuserScreen.class, 70, 30, 25, 20,
                VoidInfuserRecipeCategory.VOID_INFUSER_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VOID_INFUSER.get().asItem()),
                VoidInfuserRecipeCategory.VOID_INFUSER_RECIPE_RECIPE_TYPE);
    }
}
