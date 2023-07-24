package igentuman.bfr.datagen.recipe.impl;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import igentuman.bfr.datagen.recipe.ISubRecipeProvider;
import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.datagen.recipe.builder.GasToGasRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import mekanism.common.registration.impl.SlurryRegistryObject;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismSlurries;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.util.EnumUtils;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class CustomRecipes implements ISubRecipeProvider {

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {
        String basePath = "slurry/";

        for (PrimaryResource resource : EnumUtils.PRIMARY_RESOURCES) {
            SlurryRegistryObject<?, ?> slurry = MekanismSlurries.PROCESSED_RESOURCES.get(resource);
            ChemicalDissolutionRecipeBuilder.dissolution(
                    IngredientCreatorAccess.item().from(BfrBlocks.ORE_BLOCKS.get(resource.getRegistrySuffix()).getItemStack()),
                    IngredientCreatorAccess.gas().from(MekanismGases.SULFURIC_ACID, 1),
                    slurry.getDirtySlurry().getStack(1_200)
            ).build(consumer, BetterFusionReactor.rl(basePath + "from_"+resource.getRegistrySuffix()+"irradiated_ore"));
        }

        basePath = "crystallizing/";

        ChemicalCrystallizerRecipeBuilder.crystallizing(
                IngredientCreatorAccess.gas().from(MekanismGases.NUCLEAR_WASTE, 2000),
                BfrItems.SOLIDIFIED_WASTE.getItemStack()
        ).build(consumer, Mekanism.rl(basePath + "waste_to_solid_waste"));

    }
}