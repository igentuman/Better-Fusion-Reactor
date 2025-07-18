package igentuman.bfr.client.recipe_viewer.jei;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import mekanism.api.heat.HeatAPI;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.recipe_viewer.jei.BaseRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.BooleanStateDisplay.ActiveDisabled;
import mekanism.common.util.text.TextUtils;
import igentuman.bfr.client.recipe_viewer.recipe.FusionRecipeViewerRecipe;
import igentuman.bfr.common.BfrLang;
import mekanism.generators.common.GeneratorsLang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.ICodecHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FusionReactorRecipeCategory extends BaseRecipeCategory<FusionRecipeViewerRecipe> {

    private final GuiGauge<?> coolantFluidTank;
    private final GuiGauge<?> coolantGasTank;
    private final GuiGauge<?> fuelTank;
    private final GuiGauge<?> heatedCoolantTank;

    public FusionReactorRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<FusionRecipeViewerRecipe> recipeType) {
        super(helper, recipeType);
        addElement(new GuiInnerScreen(this, 45, 17, 105, 56, () -> List.of(
                GeneratorsLang.GAS_BURN_RATE.translate(2.0)
        )).spacing(1));
        coolantFluidTank = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)));
        fuelTank = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 25, 13).setLabel(GeneratorsLang.FISSION_FUEL_TANK.translateColored(EnumColor.DARK_GREEN)));
        heatedCoolantTank = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 152, 13).setLabel(GeneratorsLang.FISSION_HEATED_COOLANT_TANK.translateColored(EnumColor.GRAY)));
        coolantGasTank = addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_WASTE_TANK.translateColored(EnumColor.BROWN)));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, FusionRecipeViewerRecipe recipe, @NotNull IFocusGroup focusGroup) {
        if (recipe.gasCoolant() == null) {
            initFluid(builder, RecipeIngredientRole.INPUT, coolantFluidTank, recipe.fluidCoolant().getRepresentations());
        } else {
            initChemical(builder, RecipeIngredientRole.INPUT, coolantGasTank, recipe.gasCoolant().getRepresentations());
        }
        initChemical(builder, RecipeIngredientRole.INPUT, fuelTank, recipe.fuel().getRepresentations());
        initChemical(builder, RecipeIngredientRole.OUTPUT, heatedCoolantTank, Collections.singletonList(recipe.hotCoolant()));
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName(@NotNull FusionRecipeViewerRecipe recipe) {
        return recipe.id();
    }

    @NotNull
    @Override
    public Codec<FusionRecipeViewerRecipe> getCodec(@NotNull ICodecHelper codecHelper, @NotNull IRecipeManager recipeManager) {
        return FusionRecipeViewerRecipe.CODEC;
    }
}