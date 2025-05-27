package net.capspock.endupdate.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record VoidInfuserRecipeInput(ItemStack input, ItemStack inputEssence) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        if(pIndex == 1) {
            return inputEssence;
        } else {
            return input;
        }
    }

    @Override
    public int size() {
        return 2;
    }
}
