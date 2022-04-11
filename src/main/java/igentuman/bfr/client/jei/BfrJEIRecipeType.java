package igentuman.bfr.client.jei;

import igentuman.bfr.client.jei.recipe.FusionJEIRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.generators.client.jei.recipe.FissionJEIRecipe;
import mekanism.generators.common.MekanismGenerators;

public class BfrJEIRecipeType {

    public static final MekanismJEIRecipeType<FusionJEIRecipe> FUSION = new MekanismJEIRecipeType<>(BetterFusionReactor.rl("fusion"), FusionJEIRecipe.class);
}