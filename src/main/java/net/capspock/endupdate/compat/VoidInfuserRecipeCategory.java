package net.capspock.endupdate.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.recipe.VoidInfuserRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class VoidInfuserRecipeCategory implements IRecipeCategory<VoidInfuserRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "void_infuser");
    public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,
            "textures/gui/void_infuser/void_infuser_gui.png");

    public static final RecipeType<VoidInfuserRecipe> VOID_INFUSER_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, VoidInfuserRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public VoidInfuserRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(GUI_TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.VOID_INFUSER.get()));
    }

    @Override
    public RecipeType<VoidInfuserRecipe> getRecipeType() {
        return VOID_INFUSER_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.endupdate.void_infuser");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, VoidInfuserRecipe voidInfuserRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 56, 17).addIngredients(voidInfuserRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 56, 53).addIngredients(voidInfuserRecipe.getIngredients().get(1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(voidInfuserRecipe.getResultItem(null));
    }
}
