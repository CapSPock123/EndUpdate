package net.capspock.endupdate.recipe;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EndUpdate.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, EndUpdate.MOD_ID);

    public static final RegistryObject<RecipeSerializer<VoidInfuserRecipe>> VOID_INFUSER_SERIALIZER =
            SERIALIZERS.register("void_infuser", VoidInfuserRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<VoidInfuserRecipe>> VOID_INFUSER_TYPE =
            TYPES.register("void_infuser", () -> new RecipeType<VoidInfuserRecipe>() {
                @Override
                public String toString() {
                    return "void_infuser";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
