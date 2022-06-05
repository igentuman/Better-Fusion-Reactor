package igentuman.bfr.common.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public interface IRecipeIngredient extends Predicate<Object> {
    static IRecipeIngredient of(FluidStack stack)
    {
        return new IngredientIFluidStack(stack);
    }
    @Nonnull
    String getName();

    @Nonnull
    List<FluidStack> getFluidStacks();

    @Override
    boolean test(Object input);

    default boolean testIgnoreCount(Object input)
    {
        return test(input);
    }

    boolean matches(IRecipeIngredient other);
}
