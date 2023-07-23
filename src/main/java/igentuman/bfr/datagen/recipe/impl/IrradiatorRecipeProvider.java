package igentuman.bfr.datagen.recipe.impl;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.datagen.recipe.ISubRecipeProvider;
import igentuman.bfr.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import igentuman.bfr.datagen.recipe.builder.ItemStackToItemStackWithTimeRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class IrradiatorRecipeProvider implements ISubRecipeProvider {

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {
        String basePath = "irradiating/";

        //Charcoal -> Charcoal Dust
        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
              IngredientCreatorAccess.item().from(Items.CHARCOAL),
              MekanismItems.CHARCOAL_DUST.getItemStack(),
                1000
        ).build(consumer, BetterFusionReactor.rl(basePath + "charcoal_dust"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(Items.COAL),
                MekanismItems.COAL_DUST.getItemStack(),
                500
        ).build(consumer, BetterFusionReactor.rl(basePath + "coal_dust"));
    }
}