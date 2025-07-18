package igentuman.bfr.client.recipe_viewer.jei;

import igentuman.bfr.client.recipe_viewer.BfrRVRecipeType;
import igentuman.bfr.client.recipe_viewer.alias.GeneratorsAliasMapping;
import igentuman.bfr.client.recipe_viewer.recipe.BfrRecipeViewerType;
import igentuman.bfr.client.recipe_viewer.recipe.FusionRecipeViewerRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.JEIAliasHelper;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.jei.RecipeRegistryHelper;
import mekanism.client.recipe_viewer.jei.machine.ItemStackToItemStackRecipeCategory;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static igentuman.bfr.common.config.BetterFusionReactorConfig.bfr;

@JeiPlugin
public class BfrJEI implements IModPlugin {

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
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
        registry.addRecipeCategories(new FusionReactorRecipeCategory(guiHelper, BfrRVRecipeType.FUSION));
        registry.addRecipeCategories(new ItemStackToItemStackRecipeCategory(guiHelper, BfrRecipeViewerType.IRRADIATING));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registry) {
        if (!MekanismJEI.shouldLoad()) {
            return;
        }
        CatalystRegistryHelper.register(registry, BfrRVRecipeType.FUSION);
        CatalystRegistryHelper.register(registry, BfrRecipeViewerType.IRRADIATING);
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
        Collection<ItemStack> collection = Arrays.asList(
                GeneratorsBlocks.LASER_FOCUS_MATRIX,
                GeneratorsBlocks.FUSION_REACTOR_CONTROLLER,
                GeneratorsBlocks.FUSION_REACTOR_FRAME,
                GeneratorsBlocks.FUSION_REACTOR_PORT,
                GeneratorsBlocks.FUSION_REACTOR_LOGIC_ADAPTER
        ).stream().map(ItemStack::new).collect(Collectors.toList());
        if(bfr.hideMekanismRecipes.get()) {
            registry.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, collection);
        }
        RecipeRegistryHelper.register(registry, BfrRVRecipeType.FUSION, FusionRecipeViewerRecipe.getFusionRecipes());
        RecipeRegistryHelper.register(registry, BfrRecipeViewerType.IRRADIATING, BfrRecipes.IRRADIATING);
    }
}