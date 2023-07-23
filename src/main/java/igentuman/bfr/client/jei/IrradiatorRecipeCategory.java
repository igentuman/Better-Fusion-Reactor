package igentuman.bfr.client.jei;

import igentuman.bfr.common.recipe.impl.IrradiatingIRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.gui.element.GuiUpArrow;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IrradiatorRecipeCategory extends BaseRecipeCategory<IrradiatingIRecipe> {

    private final GuiSlot input;
    private final GuiSlot output;

    public IrradiatorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<IrradiatingIRecipe> recipeType) {
        super(helper, recipeType, BfrBlocks.IRRADIATOR, 28, 16, 144, 54);
        addElement(new GuiUpArrow(this, 68, 38));
        input = addSlot(SlotType.INPUT, 64, 17);
        output = addSlot(SlotType.OUTPUT, 116, 35);
        addSlot(SlotType.POWER, 39, 35).with(SlotOverlay.POWER);
        addSimpleProgress(ProgressType.BAR, 86, 38);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, IrradiatingIRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }


    public static List<IrradiatingIRecipe> getRecipes()
    {
        List<IrradiatingIRecipe> irradiatorRecipes = new ArrayList<>();
        for(ItemStackToItemStackRecipe recipe : BfrRecipes.IRRADIATING.getRecipes(getWorld())) {
            irradiatorRecipes.add((IrradiatingIRecipe) recipe);
        }
        return irradiatorRecipes;
    }


    private static ClientLevel getWorld() {
        return Minecraft.getInstance().level;
    }
}