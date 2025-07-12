package igentuman.bfr.client.recipe_viewer.jei;

import igentuman.bfr.client.recipe_viewer.alias.GeneratorsAliasMapping;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import mekanism.client.recipe_viewer.jei.JEIAliasHelper;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class GeneratorsJEI implements IModPlugin {

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        //Note: Can't use MekanismGenerators.rl, as JEI needs this in the constructor and the class may not be loaded yet.
        // we can still reference the modid though because of constant inlining
        return ResourceLocation.fromNamespaceAndPath(BetterFusionReactor.MODID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registry) {
        if (MekanismJEI.shouldLoad()) {
            MekanismJEI.registerItemSubtypes(registry, BfrItems.ITEMS.getEntries());
            MekanismJEI.registerItemSubtypes(registry, BfrBlocks.BLOCKS.getSecondaryEntries());
        }
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registry) {
        if (!MekanismJEI.shouldLoad()) {
            return;
        }
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
       // registry.addRecipeCategories(new FissionReactorRecipeCategory(guiHelper, GeneratorsRVRecipeType.FISSION));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registry) {
        if (!MekanismJEI.shouldLoad()) {
            return;
        }
      //  CatalystRegistryHelper.register(registry, GeneratorsRVRecipeType.FISSION);
    }

    @Override
    public void registerIngredientAliases(@NotNull IIngredientAliasRegistration registration) {
        new GeneratorsAliasMapping().addAliases(new JEIAliasHelper(registration));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        if (!MekanismJEI.shouldLoad()) {
            return;
        }
        //RecipeRegistryHelper.register(registry, GeneratorsRVRecipeType.FISSION, FissionRecipeViewerRecipe.getFissionRecipes());
    }
}