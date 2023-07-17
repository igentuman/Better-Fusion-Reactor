package igentuman.bfr.client.jei;

import igentuman.bfr.client.jei.recipe.FusionJEIRecipe;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.config.BetterFusionReactorConfig;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
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
import mekanism.common.Mekanism;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.generators.common.GeneratorsLang;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.registries.GeneratorsGases;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FusionReactorRecipeCategory extends BaseRecipeCategory<FusionJEIRecipe> {

    private static final ResourceLocation iconRL = BetterFusionReactor.rl(ResourceType.GUI.getPrefix() + "fuel.png");
    private final GuiGauge<?> coolantFluidTank;
    private final GuiGauge<?> coolantGasTank;
    private final GuiGauge<?> fuelTank;
    private final GuiGauge<?> heatedCoolantTank;


    public FusionReactorRecipeCategory(IGuiHelper helper, MekanismJEIRecipeType<FusionJEIRecipe> recipeType) {
        super(helper, recipeType, GeneratorsLang.FUSION_REACTOR.translate(), createIcon(helper, iconRL), 6, 13, 182, 60);
        addElement(new GuiInnerScreen(this, 45, 17, 105, 56, () -> Arrays.asList(
              GeneratorsLang.GAS_BURN_RATE.translate(1.0)
        )).spacing(2));
        coolantFluidTank = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)));
        coolantGasTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 6, 13).setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA)));
        fuelTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 25, 13).setLabel(GeneratorsLang.FISSION_FUEL_TANK.translateColored(EnumColor.DARK_GREEN)));
        heatedCoolantTank = addElement(GuiGasGauge.getDummy(GaugeType.STANDARD, this, 152, 13).setLabel(GeneratorsLang.FISSION_HEATED_COOLANT_TANK.translateColored(EnumColor.GRAY)));
    }



    @Nonnull
    public Class<? extends FusionJEIRecipe> getRecipeClass() {
        return FusionJEIRecipe.class;
    }


    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, FusionJEIRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        //Input coolant can be gas or fluid
        if (recipe.inputCoolant() instanceof FluidStackIngredient) {
            FluidStackIngredient fs = (FluidStackIngredient) recipe.inputCoolant();
            initFluid(builder, RecipeIngredientRole.INPUT, coolantFluidTank, fs.getRepresentations());
            coolantGasTank.visible = false;
        } else {
            ChemicalStackIngredient.GasStackIngredient gs = (ChemicalStackIngredient.GasStackIngredient) recipe.inputCoolant();
            initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, coolantGasTank, gs.getRepresentations());
            coolantFluidTank.visible = false;
        }
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.INPUT, fuelTank, recipe.fuel().getRepresentations());
        initChemical(builder, MekanismJEI.TYPE_GAS, RecipeIngredientRole.OUTPUT, heatedCoolantTank, Collections.singletonList(recipe.outputCoolant()));
    }

    private static FluidStack resolveFluidIgredient(String name, int amount)
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("FluidName", name);
        tag.putInt("Amount", amount);
        return FluidStack.loadFluidStackFromNBT(tag);
    }

    private static GasStack resolveGasIgredient(String name, long amount)
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("gasName", name);
        tag.putLong(NBTConstants.AMOUNT, amount);
        return GasStack.readFromNBT(tag);
    }

    public static List<FusionJEIRecipe> getFusionRecipes() {
         List<FusionJEIRecipe> recipes = new ArrayList<>();
        double energyPerFuel = MekanismGeneratorsConfig.generators.energyPerFusionFuel.get().doubleValue();

        long coolantAmount = Math.round(energyPerFuel * HeatUtils.getSteamEnergyEfficiency() / HeatUtils.getWaterThermalEnthalpy());
        for(String recipe: BetterFusionReactorConfig.bfr.fusionCoolants.get()) {
            String cold = recipe.split(";")[0];
            String hot = recipe.split(";")[1];
            GasStack inputGas = resolveGasIgredient(cold, coolantAmount);
            GasStack outputGas = resolveGasIgredient(hot, coolantAmount);

            if(inputGas.isEmpty()) {
                //Probably liquid
                FluidStack inputFluid = resolveFluidIgredient(cold, (int)coolantAmount);
                if(inputFluid.isEmpty()) {
                    Mekanism.logger.warn("Invalid fusion recipe for ingredient: " + cold);
                    continue;
                }
                if(outputGas.isEmpty()) {
                    Mekanism.logger.warn("Invalid fusion recipe for ingredient: " + hot);
                    continue;
                }
                recipes.add(new FusionJEIRecipe(IngredientCreatorAccess.fluid().from(inputFluid), IngredientCreatorAccess.gas().from(GeneratorsGases.FUSION_FUEL, 1),
                        outputGas));
                continue;
            }
            if(outputGas.isEmpty()) {
                Mekanism.logger.warn("Invalid fusion recipe for ingredient: " + hot);
                continue;
            }
            recipes.add(new FusionJEIRecipe(IngredientCreatorAccess.gas().from(inputGas), IngredientCreatorAccess.gas().from(GeneratorsGases.FUSION_FUEL, 1),
                    outputGas));
        }

        return recipes;
    }

}