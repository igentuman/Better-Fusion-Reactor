package igentuman.bfr.client.recipe_viewer.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import igentuman.bfr.common.config.BetterFusionReactorConfig;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.client.recipe_viewer.emi.INamedRVRecipe;
import mekanism.common.util.HeatUtils;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static igentuman.bfr.common.BetterFusionReactor.rl;

public record FusionRecipeViewerRecipe(ResourceLocation id, @Nullable ChemicalStackIngredient gasCoolant,@Nullable FluidStackIngredient fluidCoolant, ChemicalStackIngredient fuel,
                                       ChemicalStack hotCoolant)
      implements INamedRVRecipe {

    public static final Codec<FusionRecipeViewerRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          ResourceLocation.CODEC.fieldOf(SerializationConstants.ID).forGetter(FusionRecipeViewerRecipe::id),
          ChemicalStackIngredient.CODEC.optionalFieldOf(SerializationConstants.EXTRA_INPUT).forGetter(recipe -> Optional.ofNullable(recipe.gasCoolant())),
          FluidStackIngredient.CODEC.optionalFieldOf(SerializationConstants.EXTRA_INPUT).forGetter(recipe -> Optional.ofNullable(recipe.fluidCoolant())),
          ChemicalStackIngredient.CODEC.fieldOf(SerializationConstants.INPUT).forGetter(FusionRecipeViewerRecipe::fuel),
          ChemicalStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(FusionRecipeViewerRecipe::hotCoolant)
    ).apply(instance, (id, gasCoolant, fluidCoolant, fuel, outputCoolant) ->
          new FusionRecipeViewerRecipe(id, gasCoolant.orElse(null), fluidCoolant.orElse(null), fuel, outputCoolant)));

    public FluidStackIngredient waterInput() {
        return IngredientCreatorAccess.fluid().from(FluidTags.WATER, MathUtils.clampToInt(hotCoolant().getAmount()));
    }

    @SuppressWarnings("removal")
    public static List<FusionRecipeViewerRecipe> getFusionRecipes() {
        List<FusionRecipeViewerRecipe> recipes = new ArrayList<>();
        double energyPerFuel = MekanismGeneratorsConfig.generators.energyPerFusionFuel.get();
        BetterFusionReactorConfig.bfr.initFusionCoolants();
        long coolantAmount = Math.round(energyPerFuel * HeatUtils.getSteamEnergyEfficiency() / HeatUtils.getWaterThermalEnthalpy());
        for(Chemical hot: BetterFusionReactorConfig.bfr.coolantMap.keySet()) {
            ChemicalStack outputGas = new ChemicalStack(hot, coolantAmount);
            Object cold = BetterFusionReactorConfig.bfr.coolantMap.get(hot);
            if(cold instanceof Fluid) {
                FluidStack inputFluid = new FluidStack((Fluid) cold, (int)coolantAmount);
                recipes.add(new FusionRecipeViewerRecipe(rl("fusion_"+outputGas.getChemical().getTranslationKey()), null, IngredientCreatorAccess.fluid().from(inputFluid), IngredientCreatorAccess.chemicalStack().from(GeneratorsChemicals.FUSION_FUEL.asStack(1)),
                        outputGas));
            } else {
                ChemicalStackIngredient inputGas = IngredientCreatorAccess.chemicalStack().from((Chemical) cold, (int)coolantAmount);
                recipes.add(new FusionRecipeViewerRecipe(rl("fusion_"+outputGas.getChemical().getTranslationKey()), inputGas, null, IngredientCreatorAccess.chemicalStack().from(GeneratorsChemicals.FUSION_FUEL.asStack(1)),
                        outputGas));
            }
        }
        return recipes;
    }
}