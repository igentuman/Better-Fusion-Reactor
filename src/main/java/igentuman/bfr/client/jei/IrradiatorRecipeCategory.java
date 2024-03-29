package igentuman.bfr.client.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.config.BetterFusionReactorConfig;
import igentuman.bfr.common.recipe.impl.IrradiatingIRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.client.gui.element.GuiUpArrow;
import mekanism.client.gui.element.progress.IProgressInfoHandler;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.generators.common.GeneratorsLang;
import mezz.jei.api.gui.ITickTimer;
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
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static igentuman.bfr.common.BetterFusionReactor.rl;

public class IrradiatorRecipeCategory extends BaseRecipeCategory<IrradiatingIRecipe> {

    private final GuiSlot input;
    private final GuiSlot output;
    HashMap<Integer, TickTimer> dynamicTimer = new HashMap<>();
    private final IGuiHelper guiHlp;
    HashMap<Integer, IDrawable> progressArrow = new HashMap<>();


    public IrradiatorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<IrradiatingIRecipe> recipeType) {
        super(helper, recipeType, BfrBlocks.IRRADIATOR, 28, 16, 144, 54);
        input = addSlot(SlotType.INPUT, 54, 35);
        output = addSlot(SlotType.OUTPUT, 116, 35);
        this.guiHlp = helper;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, IrradiatingIRecipe recipe, @NotNull IFocusGroup focusGroup) {
        initItem(builder, RecipeIngredientRole.INPUT, input, recipe.getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
        if(!dynamicTimer.containsKey(recipe.getTicks())) {
            dynamicTimer.put(recipe.getTicks(), new TickTimer(recipe.getTicks() / 5, 36, true));
        }
        if(!progressArrow.containsKey(recipe.getTicks())) {
            progressArrow.put(recipe.getTicks(), guiHlp.drawableBuilder(rl("gui/progress.png"), 0, 0, 36, 15)
                    .buildAnimated(dynamicTimer.get(recipe.getTicks()), IDrawableAnimated.StartDirection.LEFT));
        }
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


    @Override
    public void draw(IrradiatingIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        if(progressArrow.containsKey(recipe.getTicks())) {
            progressArrow.get(recipe.getTicks()).draw(guiGraphics, 46, 19);
        }
    }
}