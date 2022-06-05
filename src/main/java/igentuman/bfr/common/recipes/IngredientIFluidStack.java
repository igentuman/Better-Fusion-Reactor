package igentuman.bfr.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class IngredientIFluidStack implements IRecipeIngredient {
    protected final FluidStack stack;
    private final List<FluidStack> stacks;

    IngredientIFluidStack(@Nonnull FluidStack stack)
    {
        this.stack = stack;
        stacks = Collections.singletonList(stack);
    }

    @Nonnull
    @Override
    public String getName()
    {
        return stack.getLocalizedName();
    }

    @Nonnull
    @Override
    public List<FluidStack> getFluidStacks()
    {
        return stacks;
    }

    @Override
    public boolean test(Object obj)
    {
        return testIgnoreCount(obj) && ((FluidStack) obj).amount >= stack.amount;
    }

    @Override
    public boolean testIgnoreCount(Object obj)
    {
        return obj instanceof FluidStack && StackHelper.doFluidStacksMatch(stack, (FluidStack) obj);
    }

    @Override
    public boolean matches(IRecipeIngredient other)
    {
        return other instanceof IngredientIFluidStack && StackHelper.doFluidStacksMatch(stack, ((IngredientIFluidStack) other).stack);
    }
}
