package eu.pb4.justpolydrawers.recipe;

import com.mojang.serialization.Codec;
import eu.pb4.factorytools.api.recipe.LazyRecipeSerializer;
import eu.pb4.justpolydrawers.ModInit;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FactoryRecipeSerializers {
    public static final LazyRecipeSerializer<ShapelessNbtCopyRecipe> CRAFTING_SHAPELESS_NBT_COPY = register("crafting/shapeless_nbt_copy", ShapelessNbtCopyRecipe.CODEC);

    public static void register() {

    }

    public static <T extends RecipeSerializer<?>> T register(String path, T recipeSerializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ModInit.ID, path), recipeSerializer);
    }

    public static <T extends Recipe<?>> LazyRecipeSerializer<T> register(String path, Codec<T> codec) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ModInit.ID, path), new LazyRecipeSerializer<>(codec));
    }
}
