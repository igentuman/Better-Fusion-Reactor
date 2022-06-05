package igentuman.bfr.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class ReactorCoolantRecipe {

    protected final IRecipeIngredient ingredient;
    protected final FluidStack outputStack;
    protected final int inputAmount;



    public ReactorCoolantRecipe(FluidStack outputStack, FluidStack inputStack)
    {
        this.outputStack = outputStack;
        this.inputAmount = inputStack.amount == 0 ? 0 : inputStack.amount;
        this.ingredient = IRecipeIngredient.of(inputStack);
    }


    public boolean test(Object input)
    {
        return ingredient.test(input);
    }


    @Deprecated
    public boolean test(Object... inputs)
    {
        throw new UnsupportedOperationException("This recipe does not support access by multiple inputs");
    }


    public boolean matches(Object input)
    {
        return input instanceof IRecipeIngredient && ingredient.matches((IRecipeIngredient) input);
    }


    @Deprecated
    public boolean matches(Object... inputs)
    {
        throw new UnsupportedOperationException("This recipe does not support access by multiple inputs");
    }

    public FluidStack consumeInput(FluidStack stack)
    {
        return StackHelper.consumeFluid(stack, inputAmount);
    }

    @Nonnull
    public FluidStack getOutput()
    {
        return outputStack.copy();
    }


    public IRecipeIngredient getInput()
    {
        return ingredient;
    }


    public String getName()
    {
        return ingredient.getName();
    }
}
