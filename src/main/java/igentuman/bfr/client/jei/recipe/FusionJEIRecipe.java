package igentuman.bfr.client.jei.recipe;

import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;

import javax.annotation.Nullable;

//If null -> coolant is water
public record FusionJEIRecipe(@Nullable GasStackIngredient inputCoolant, GasStackIngredient fuel, GasStack outputCoolant, GasStack waste) {
}