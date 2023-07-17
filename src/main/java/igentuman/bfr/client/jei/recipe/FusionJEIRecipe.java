package igentuman.bfr.client.jei.recipe;

import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.InputIngredient;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FusionJEIRecipe {

    private final GasStackIngredient inputGasCoolant;
    private final FluidStackIngredient inputFluidCoolant;
    private final GasStack outputCoolant;
    private final GasStackIngredient fuel;

    public FusionJEIRecipe(@Nullable GasStackIngredient inputCoolant, GasStackIngredient fuel, GasStack outputCoolant) {
        this.inputGasCoolant = inputCoolant;
        this.inputFluidCoolant = null;
        this.outputCoolant = outputCoolant;
        this.fuel = fuel;
    }


    public FusionJEIRecipe(@Nullable FluidStackIngredient inputCoolant, GasStackIngredient fuel, GasStack outputCoolant) {
        this.inputGasCoolant = null;
        this.inputFluidCoolant = inputCoolant;
        this.outputCoolant = outputCoolant;
        this.fuel = fuel;
    }

    public GasStack outputCoolant() {
        return outputCoolant;
    }

    public Object inputCoolant() {
        if (inputGasCoolant != null) {
            return inputGasCoolant;
        } else {
            return inputFluidCoolant;
        }
    }

    public GasStackIngredient fuel() {
        return fuel;
    }
}

