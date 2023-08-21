package igentuman.bfr.common.registries;

import igentuman.bfr.common.recipe.ItemStackToItemStackWithTimeModRecipe;
import igentuman.bfr.common.recipe.ItemToItemWithTimeModRecipeSerializer;
import igentuman.bfr.common.recipe.impl.IrradiatingIRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeSerializerRegistryObject;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static mekanism.common.registries.MekanismRecipeSerializers.RECIPE_SERIALIZERS;

public class BfrRecipes {

    public static RecipeTypeRegistryObject<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> IRRADIATING;
    public static final RecipeSerializerRegistryObject<ItemStackToItemStackWithTimeModRecipe> IRRADIATING_SERIALIZER = RECIPE_SERIALIZERS.register("irradiating", () -> new ItemToItemWithTimeModRecipeSerializer<>(IrradiatingIRecipe::new));

    public static void init() {
        try {
            Method register = MekanismRecipeType.class
                    .getDeclaredMethod("register", String.class, Function.class);
            register.setAccessible(true);
            IRRADIATING = (RecipeTypeRegistryObject<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>>) register
                    .invoke(null, "irradiating", (Function<MekanismRecipeType<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackWithTimeModRecipe>>, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>>) recipeType -> new InputRecipeCache.SingleItem<>(recipeType, ItemStackToItemStackRecipe::getInput));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
