package igentuman.bfr.common.registries;

import igentuman.bfr.common.recipe.ItemStackToItemStackWithTimeModRecipe;
import igentuman.bfr.common.recipe.ItemToItemWithTimeModRecipeSerializer;
import igentuman.bfr.common.recipe.impl.IrradiatorRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import static mekanism.common.registries.MekanismRecipeSerializersInternal.RECIPE_SERIALIZERS;


public class BfrRecipes {

    public static RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> IRRADIATING;
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ItemStackToItemStackWithTimeModRecipe>> IRRADIATING_SERIALIZER = RECIPE_SERIALIZERS.register("irradiating", () -> ItemToItemWithTimeModRecipeSerializer.itemToItem(IrradiatorRecipe::new));

    public static void init() {
        try {
            Method register = MekanismRecipeType.class
                    .getDeclaredMethod("register", String.class, Function.class);
            register.setAccessible(true);
            /*IRRADIATING = (RecipeTypeRegistryObject<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>>) register
                    .invoke(null, "irradiating", (Function<MekanismRecipeType<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackWithTimeModRecipe>>, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>>) recipeType -> new InputRecipeCache.SingleItem<>(recipeType, ItemStackToItemStackRecipe::getInput));*/
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
