package net.capspock.endupdate.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record VoidInfuserRecipe(Ingredient inputItem, Ingredient infuseItem, ItemStack output) implements Recipe<VoidInfuserRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        list.add(infuseItem);
        return list;
    }

    @Override
    public boolean matches(VoidInfuserRecipeInput pInput, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        } else {
            return inputItem.test(pInput.getItem(0)) && infuseItem.test(pInput.getItem(1));
        }
    }

    @Override
    public ItemStack assemble(VoidInfuserRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.VOID_INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.VOID_INFUSER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<VoidInfuserRecipe> {
        public static final MapCodec<VoidInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("input_ingredient").forGetter(VoidInfuserRecipe::inputItem),
                Ingredient.CODEC_NONEMPTY.fieldOf("infuse_ingredient").forGetter(VoidInfuserRecipe::infuseItem),
                ItemStack.CODEC.fieldOf("result").forGetter(VoidInfuserRecipe::output)).apply(inst, VoidInfuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, VoidInfuserRecipe> STREAM_CODEC =
                StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC, VoidInfuserRecipe::inputItem,
                        Ingredient.CONTENTS_STREAM_CODEC, VoidInfuserRecipe::infuseItem,
                        ItemStack.STREAM_CODEC, VoidInfuserRecipe::output,
                        VoidInfuserRecipe::new);

        @Override
        public MapCodec<VoidInfuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VoidInfuserRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
