package igentuman.bfr.client.jei;

import igentuman.bfr.client.jei.recipe.FusionJEIRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.recipe.impl.IrradiatingIRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.registries.MekanismBlocks;
import mekanism.generators.client.jei.recipe.FissionJEIRecipe;
import mekanism.generators.common.MekanismGenerators;

public class BfrJEIRecipeType {
    public static final MekanismJEIRecipeType<IrradiatingIRecipe> IRRADIATOR = new MekanismJEIRecipeType<>(BfrBlocks.IRRADIATOR, IrradiatingIRecipe.class);

    public static final MekanismJEIRecipeType<FusionJEIRecipe> FUSION = new MekanismJEIRecipeType<>(BetterFusionReactor.rl("fusion"), FusionJEIRecipe.class);
}