package igentuman.bfr.client.recipe_viewer.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import mekanism.client.recipe_viewer.emi.MekanismEmi;

@EmiEntrypoint
public class GeneratorsEmi implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        addCategories(registry);

        MekanismEmi.registerItemSubtypes(registry, BfrItems.ITEMS.getEntries());
        MekanismEmi.registerItemSubtypes(registry, BfrBlocks.BLOCKS.getSecondaryEntries());
    }

    private void addCategories(EmiRegistry registry) {
       // MekanismEmi.addCategoryAndRecipes(registry, GeneratorsRVRecipeType.FISSION, FissionReactorEmiRecipe::new, FissionRecipeViewerRecipe.getFissionRecipes());
    }
}