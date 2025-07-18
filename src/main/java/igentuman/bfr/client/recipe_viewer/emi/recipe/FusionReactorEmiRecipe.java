package igentuman.bfr.client.recipe_viewer.emi.recipe;

import dev.emi.emi.api.widget.WidgetHolder;
import java.util.List;
import mekanism.api.heat.HeatAPI;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiRecipe;
import mekanism.common.MekanismLang;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.BooleanStateDisplay.ActiveDisabled;
import mekanism.common.util.text.TextUtils;
import igentuman.bfr.client.recipe_viewer.recipe.FusionRecipeViewerRecipe;
import igentuman.bfr.common.BfrLang;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.resources.ResourceLocation;

public class FusionReactorEmiRecipe extends MekanismEmiRecipe<FusionRecipeViewerRecipe> {

    public FusionReactorEmiRecipe(MekanismEmiRecipeCategory category, ResourceLocation id, FusionRecipeViewerRecipe recipe) {
        super(category, id, recipe);
        addInputDefinition(recipe.fluidCoolant());
        addInputDefinition(recipe.gasCoolant());
        addInputDefinition(recipe.fuel());
        addChemicalOutputDefinition(List.of(recipe.hotCoolant()));
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        addElement(widgetHolder, new GuiInnerScreen(this, 45, 17, 105, 56, () -> List.of(
              BfrLang.GAS_BURN_RATE.translate(2.0)
        )).spacing(1));
        initTank(widgetHolder, GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)), input(0));
        initTank(widgetHolder, GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 25, 13).setLabel(GeneratorsLang.FISSION_FUEL_TANK.translateColored(EnumColor.DARK_GREEN)), input(1));
        initTank(widgetHolder, GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 152, 13).setLabel(GeneratorsLang.FISSION_HEATED_COOLANT_TANK.translateColored(EnumColor.GRAY)), output(0)).recipeContext(this);
        initTank(widgetHolder, GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)), input(0)).recipeContext(this);
    }
}