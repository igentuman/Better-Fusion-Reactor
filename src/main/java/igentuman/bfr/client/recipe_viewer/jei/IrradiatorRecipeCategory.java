package igentuman.bfr.client.recipe_viewer.jei;

import igentuman.bfr.common.recipe.impl.IrradiatorRecipe;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static igentuman.bfr.common.BetterFusionReactor.rl;

public class IrradiatorRecipeCategory extends HolderRecipeCategory<ItemStackToItemStackRecipe> {

    private final GuiSlot input;
    private final GuiSlot output;
    private final Map<Integer, TickTimer> dynamicTimer = new HashMap<>();
    private final IGuiHelper guiHlp;
    private final Map<Integer, IDrawable> progressArrow = new HashMap<>();


    public IrradiatorRecipeCategory(IGuiHelper helper, RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> recipeType) {
        super(helper, recipeType);
        input = addSlot(SlotType.INPUT, 54, 35);
        output = addSlot(SlotType.OUTPUT, 116, 35);
        this.guiHlp = helper;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder,  RecipeHolder<ItemStackToItemStackRecipe> recipeHolder, @NotNull IFocusGroup focusGroup) {
        // Set up input and output slots
        initItem(builder, RecipeIngredientRole.INPUT, input, recipeHolder.value().getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipeHolder.value().getOutputDefinition());

        if(!(recipeHolder.value() instanceof IrradiatorRecipe recipe)) {
            return; // Safety check: ensure the recipe is of the correct type
        }
        // Get the recipe processing time
        int recipeTicks = recipe.getTicks();
        
        // Create dynamic timer for this recipe duration if it doesn't exist
        if (!dynamicTimer.containsKey(recipeTicks)) {
            // Timer cycles every recipeTicks/5 milliseconds, with max value 36 (progress bar width), counting down
            dynamicTimer.put(recipeTicks, new TickTimer(recipeTicks / 5, 36, true));
        }
        
        // Create animated progress arrow for this recipe duration if it doesn't exist
        if (!progressArrow.containsKey(recipeTicks)) {
            progressArrow.put(recipeTicks, guiHlp.drawableBuilder(rl("gui/progress.png"), 0, 0, 36, 15)
                    .buildAnimated(dynamicTimer.get(recipeTicks), IDrawableAnimated.StartDirection.LEFT));
        }
    }


    /**
     * Gets all irradiator recipes from the recipe registry
     * @return List of all available irradiator recipes
     */
    public static List<IrradiatorRecipe> getRecipes() {
        List<IrradiatorRecipe> irradiatorRecipes = new ArrayList<>();
        ClientLevel world = getWorld();
        if (world != null) {
            for (RecipeHolder<ItemStackToItemStackRecipe> recipe : BfrRecipes.IRRADIATING.getRecipes(world)) {
                if (recipe.value() instanceof IrradiatorRecipe irradiatorRecipe) {
                    irradiatorRecipes.add(irradiatorRecipe);
                }
            }
        }
        return irradiatorRecipes;
    }

    private static ClientLevel getWorld() {
        return Minecraft.getInstance().level;
    }

    /**
     * Clears the cached timers and progress arrows.
     * This can be called to free memory if needed.
     */
    public void clearCaches() {
        dynamicTimer.clear();
        progressArrow.clear();
    }


    @Override
    public void draw(RecipeHolder<ItemStackToItemStackRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

        if(!(recipe.value() instanceof IrradiatorRecipe irradiatorRecipe)) {
            return; // Safety check: ensure the recipe is of the correct type
        }
        // Draw the animated progress arrow for this specific recipe
        int recipeTicks = irradiatorRecipe.getTicks();
        IDrawable arrow = progressArrow.get(recipeTicks);
        if (arrow != null) {
            // Position the progress arrow between input and output slots
            arrow.draw(guiGraphics, 46, 20);
        }
    }
}