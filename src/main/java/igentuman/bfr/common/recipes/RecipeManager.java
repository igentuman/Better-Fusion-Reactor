package igentuman.bfr.common.recipes;

import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeManager<T extends ReactorCoolantRecipe> {
    private final List<T> recipes;
    private List<Fluid> allowedInputs;
    private List<Fluid> allowedOutputs;
    public RecipeManager()
    {
        recipes = new ArrayList<>();
    }

    public RecipeManager(int size)
    {
        recipes = new ArrayList<>(size);
    }

    public void add(T recipe)
    {
        recipes.add(recipe);
    }

    @Nullable
    @Deprecated
    public T get(Object... inputs)
    {
        return recipes.stream().filter(x -> x.test(inputs)).findFirst().orElse(null);
    }

    @Nullable
    public T get(Object input)
    {
        return recipes.stream().filter(x -> x.test(input)).findFirst().orElse(null);
    }

    @Nonnull
    public List<T> getAll()
    {
        return Collections.unmodifiableList(recipes);
    }

    public List<Fluid> getAllowedInputs() {
        if(allowedInputs == null) {
            allowedInputs = new ArrayList<>();
            for(ReactorCoolantRecipe recipe: getAll()) {
                allowedInputs.add(recipe.getInput().getFluidStacks().get(0).getFluid());
            }
        }
        return allowedInputs;
    }

    public List<Fluid> getAllowedOutputs()
    {
        if(allowedOutputs == null) {
            allowedOutputs = new ArrayList<>();
            for(ReactorCoolantRecipe recipe: getAll()) {
                allowedOutputs.add(recipe.getOutput().getFluid());
            }
        }
        return allowedOutputs;
    }

    @Deprecated
    public void remove(Object... inputs)
    {
        recipes.removeIf(x -> x.matches(inputs));
    }

    public void remove(Object input)
    {
        recipes.removeIf(x -> x.matches(input));
    }
}
