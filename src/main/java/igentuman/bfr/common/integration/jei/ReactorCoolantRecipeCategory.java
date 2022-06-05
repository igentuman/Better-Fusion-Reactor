package igentuman.bfr.common.integration.jei;

import com.google.common.collect.ImmutableList;
import igentuman.bfr.common.recipes.ReactorCoolantRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

import static igentuman.bfr.common.BFR.MODID;


@ParametersAreNonnullByDefault
public class ReactorCoolantRecipeCategory implements IRecipeCategory<ReactorCoolantRecipeCategory.Wrapper>, ITooltipCallback<FluidStack>
{
    private static final ResourceLocation GUI_LOCATION = new ResourceLocation(MODID, "textures/gui/jei/reactor_coolant.png");
    private static final String TRANSLATION_KEY = MODID + ".jei.category.reactor_coolant";

    private final IDrawable background;
    private final IDrawableAnimated animatedArrow;

    public ReactorCoolantRecipeCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(GUI_LOCATION, 0, 0, 74, 20);
        IDrawableStatic staticArrow = guiHelper.createDrawable(GUI_LOCATION, 0, 20, 26, 20);
        animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }



    @Nonnull
    @Override
    public String getUid()
    {
        return MODID+"_reactor_coolant";
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return I18n.format(TRANSLATION_KEY);
    }

    @Nonnull
    @Override
    public String getModName()
    {
        return MODID;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawExtras(Minecraft minecraft)
    {
        animatedArrow.draw(minecraft, 25, 2);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients ingredients)
    {
        int index = 0;
        recipeLayout.getFluidStacks().init(index, true, 1, 1);
        recipeLayout.getFluidStacks().set(index, ingredients.getInputs(FluidStack.class).get(0));

        index++;
        recipeLayout.getFluidStacks().init(index, false, 57, 1);
        recipeLayout.getFluidStacks().set(index, ingredients.getOutputs(FluidStack.class).get(0));

        recipeLayout.getFluidStacks().addTooltipCallback(this);

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, FluidStack ingredient, List<String> tooltip) {

        String last = tooltip.get(tooltip.size()-1);
        tooltip.remove(tooltip.size()-1);
        tooltip.add(TextFormatting.YELLOW + String.valueOf(ingredient.amount) + " mB");
        if(!input) {
            tooltip.add(TextFormatting.AQUA +  I18n.format("bfr.jei.coolant_title"));

        }
        tooltip.add(last);
    }

    public static class Wrapper implements IRecipeWrapper
    {
        private final List<List<FluidStack>> input;
        private final List<List<FluidStack>> output;

        public Wrapper(ReactorCoolantRecipe recipe)
        {
            ImmutableList.Builder<List<FluidStack>> builder = ImmutableList.builder();

            // Add the ingredient
            builder.add(recipe.getInput().getFluidStacks());

            // Set the input
            this.input = builder.build();

            // Reset builder and add output
            builder = ImmutableList.builder();
            builder.add((List<FluidStack>) ImmutableList.of(recipe.getOutput()));

            // Set the output
            output = builder.build();

        }

        @Override
        public void getIngredients(@Nonnull IIngredients ingredients)
        {
            ingredients.setInputLists(FluidStack.class, input);
            ingredients.setOutputLists(FluidStack.class, output);
        }
    }
}
