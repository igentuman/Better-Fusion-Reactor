package igentuman.bfr.client.jei;

import igentuman.bfr.client.jei.recipe.FusionJEIRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.jei.BaseRecipeCategory;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.registries.MekanismGases;
import mekanism.common.tags.TagUtils;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.registries.GeneratorsGases;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FusionReactorRecipeCategory extends BaseRecipeCategory<FusionJEIRecipe> {

    private static final ResourceLocation iconRL = BetterFusionReactor.rl(ResourceType.GUI.getPrefix() + "fuel.png");
    private final GuiGauge<?> coolantTank;
    private final GuiGauge<?> fuelTank;
    private final GuiGauge<?> heatedCoolantTank;
    //private final GuiGauge<?> wasteTank;

    public FusionReactorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<FusionJEIRecipe> recipeType) {
        super(helper, recipeType, BfrLang.FUSION_REACTOR.translate(), createIcon(helper, iconRL), 6, 13, 182, 60);
        addElement(new GuiInnerScreen(this, 45, 17, 105, 56, () -> Arrays.asList(
              //MekanismLang.STATUS.translate(EnumColor.BRIGHT_GREEN, ActiveDisabled.of(true)),
              GeneratorsLang.GAS_BURN_RATE.translate(1.0),
              GeneratorsLang.FISSION_HEATING_RATE.translate(0)
             // MekanismLang.TEMPERATURE.translate(EnumColor.BRIGHT_GREEN, MekanismUtils.getTemperatureDisplay(HeatAPI.AMBIENT_TEMP, TemperatureUnit.KELVIN, true)))
        )).spacing(2));
        coolantTank = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)));
        fuelTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 25, 13).setLabel(GeneratorsLang.FISSION_FUEL_TANK.translateColored(EnumColor.DARK_GREEN)));
        heatedCoolantTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 152, 13).setLabel(GeneratorsLang.FISSION_HEATED_COOLANT_TANK.translateColored(EnumColor.GRAY)));
       // wasteTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 171, 13).setLabel(GeneratorsLang.FISSION_WASTE_TANK.translateColored(EnumColor.BROWN)));
    }

    private List<FluidStack> getWaterInput(FusionJEIRecipe recipe) {
        int amount = MathUtils.clampToInt(recipe.outputCoolant().getAmount());
        return TagUtils.tag(ForgeRegistries.FLUIDS, FluidTags.WATER).stream().map(fluid -> new FluidStack(fluid, amount)).collect(Collectors.toList());
    }

    @Nonnull
    public Class<? extends FusionJEIRecipe> getRecipeClass() {
        return FusionJEIRecipe.class;
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, FusionJEIRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //Handle the coolant either special cased water or the proper coolant
        if (recipe.inputCoolant() == null) {
            initFluid(builder, RecipeIngredientRole.INPUT, coolantTank, getWaterInput(recipe));
        } else {
            initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, coolantTank, recipe.inputCoolant().getRepresentations());
        }
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, fuelTank, recipe.fuel().getRepresentations());
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, heatedCoolantTank, Collections.singletonList(recipe.outputCoolant()));
        //initChemical(gasStacks, chemicalTankIndex, false, wasteTank, Collections.singletonList(recipe.reactionOutput));
    }

    public static List<FusionJEIRecipe> getFusionRecipes() {
         List<FusionJEIRecipe> recipes = new ArrayList<>();
        double energyPerFuel = MekanismGeneratorsConfig.generators.energyPerFusionFuel.get().doubleValue();

        long coolantAmount = Math.round(energyPerFuel * HeatUtils.getSteamEnergyEfficiency() / HeatUtils.getWaterThermalEnthalpy());
        recipes.add(new FusionJEIRecipe(null, IngredientCreatorAccess.gas().from(GeneratorsGases.FUSION_FUEL, 1),
              MekanismGases.STEAM.getStack(coolantAmount),null));

        return recipes;
    }

}