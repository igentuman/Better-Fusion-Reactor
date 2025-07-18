package igentuman.bfr.client.recipe_viewer.recipe;

import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;

public class BfrRecipeViewerType {
    private BfrRecipeViewerType() {
    }

    public static final RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> IRRADIATING = new RVRecipeTypeWrapper<>(BfrRecipes.IRRADIATING, ItemStackToItemStackRecipe.class, -28, -16, 144, 54, BfrBlocks.IRRADIATOR);

}
