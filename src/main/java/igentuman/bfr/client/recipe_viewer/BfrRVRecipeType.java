package igentuman.bfr.client.recipe_viewer;

import igentuman.bfr.client.recipe_viewer.recipe.FusionRecipeViewerRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.recipe.impl.IrradiatorRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.client.recipe_viewer.type.FakeRVRecipeType;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.common.GeneratorsLang;

public class BfrRVRecipeType {

    public static final FakeRVRecipeType<FusionRecipeViewerRecipe> FUSION = new FakeRVRecipeType<>(BetterFusionReactor.rl("fusion"), MekanismUtils.getResource(ResourceType.GUI, "radioactive.png"), GeneratorsLang.FUSION_REACTOR, FusionRecipeViewerRecipe.class, -6, -13, 182, 60,
          BfrBlocks.FUSION_REACTOR_CONTROLLER, BfrBlocks.FUSION_REACTOR_PORT, BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.FUSION_REACTOR_FRAME);
    
    public static final FakeRVRecipeType<IrradiatorRecipe> IRRADIATING = new FakeRVRecipeType<>(BetterFusionReactor.rl("irradiating"), MekanismUtils.getResource(ResourceType.GUI, "radioactive.png"), GeneratorsLang.FUSION_REACTOR, IrradiatorRecipe.class, -28, -16, 144, 54,
          BfrBlocks.IRRADIATOR);
}