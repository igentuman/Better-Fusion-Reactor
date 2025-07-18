package igentuman.bfr.common.config;

import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.*;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BfrConfig extends BaseMekanismConfig {

    private final ModConfigSpec configSpec;

    public final CachedLongValue energyPerFusionFuel;
    public final CachedDoubleValue fusionThermocoupleEfficiency;
    public final CachedDoubleValue fusionCasingThermalConductivity;
    public final CachedDoubleValue fusionWaterHeatingRatio;
    public final CachedLongValue fusionFuelCapacity;
    public final CachedLongValue fusionEnergyCapacity;
    public final CachedIntValue fusionWaterPerInjection;
    public final CachedLongValue fusionSteamPerInjection;
    public final CachedIntValue irradiatorBaseProcessTicks;
    public final CachedIntValue irradiatorCoolingRate;
    public final CachedIntValue reactionDifficulty;
    public final CachedBooleanValue reactorMeltdown;
    public final CachedBooleanValue hideMekanismRecipes;
    public final CachedFloatValue reactorExplosionRadius;
    public final ModConfigSpec.ConfigValue<List<? extends String>> fusionCoolants;

    public List<Fluid> allowedCoolantFluids;
    public List<Chemical> allowedCoolantGases;
    public List<Chemical> allowedCoolantHotGases;

    public HashMap<Chemical, Object> coolantMap;

    private static FluidStack resolveFluidIgredient(String name, int amount)
    {
        try {
            ResourceLocation fluidId = ResourceLocation.parse(name);
            Fluid fluid = BuiltInRegistries.FLUID.get(fluidId);
            if (fluid != null && !fluid.isSame(net.minecraft.world.level.material.Fluids.EMPTY)) {
                return new FluidStack(fluid, amount);
            }
        } catch (Exception e) {
            Mekanism.logger.warn("Failed to resolve fluid: " + name, e);
        }
        return FluidStack.EMPTY;
    }

    private static ChemicalStack resolveGasIgredient(String name, long amount)
    {
        try {
            ResourceLocation chemicalId = ResourceLocation.parse(name);
            Chemical chemical = MekanismAPI.CHEMICAL_REGISTRY.get(chemicalId);
            if (chemical != null) {
                return new ChemicalStack(chemical, amount);
            }
        } catch (Exception e) {
            Mekanism.logger.warn("Failed to resolve chemical: " + name, e);
        }
        return ChemicalStack.EMPTY;
    }

    public void initFusionCoolants()
    {
        if(coolantMap != null) {
            return;
        }
        coolantMap = new HashMap<>();
        allowedCoolantFluids = new ArrayList<>();
        allowedCoolantGases = new ArrayList<>();
        allowedCoolantHotGases = new ArrayList<>();
        for(String recipe: BetterFusionReactorConfig.bfr.fusionCoolants.get()) {
            String cold = recipe.split(";")[0];
            String hot = recipe.split(";")[1];
            ChemicalStack inputGas = resolveGasIgredient(cold, 1);
            ChemicalStack outputGas = resolveGasIgredient(hot, 1);
            Object coolantCold = null;
            if(inputGas.isEmpty()) {
                //Probably liquid
                FluidStack inputFluid = resolveFluidIgredient(cold,1);
                coolantCold = inputFluid.getFluid();
                if(!inputFluid.isEmpty()) {
                    allowedCoolantFluids.add(inputFluid.getFluid());
                } else {
                    Mekanism.logger.warn("Invalid coolant input: " + cold);
                }
            } else {
                coolantCold = inputGas.getChemical();
                allowedCoolantGases.add(inputGas.getChemical());
            }

            if(!outputGas.isEmpty()) {
                if(coolantCold != null) {
                    coolantMap.put(outputGas.getChemical(), coolantCold);
                }
                allowedCoolantHotGases.add(outputGas.getChemical());
            } else {
                Mekanism.logger.warn("Invalid coolant output: " + hot);
            }
        }
    }

    BfrConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        BfrConfigTranslations.SERVER_FUSION.applyToBuilder(builder).push("better_fusion_reactor");
        fusionCoolants = builder.comment("List of fluids that can be used as coolants in the fusion reactor (; separated)")
                .defineList("fusion_coolants", () -> {
                    return List.of(
                            "water;mekanism:steam",
                            "mekanism:sodium;mekanism:superheated_sodium"
                    );
                }, o -> o instanceof String);
        reactionDifficulty = CachedIntValue.wrap(this, builder.comment("How often Reactivity changes and error level change speed. default 10")
                .defineInRange("reaction_difficulty", 10, 1, 20));
        hideMekanismRecipes = CachedBooleanValue.wrap(this, builder.comment("Hide original Mekanism Fusion Reactor recipes")
                .define("hide_original_mek_recipes", true));
        reactorMeltdown = CachedBooleanValue.wrap(this, builder.comment("Explosion when reactor reaches 100% error level")
                .define("reactor_meltdown", false));
        reactorExplosionRadius = CachedFloatValue.wrap(this, builder.comment("Radius of Explosion (default 4 - TNT size)")
                .define("reactor_explosion_radius", 4.0));
        energyPerFusionFuel = CachedLongValue.definePositive(this, builder, BfrConfigTranslations.SERVER_FUSION_FUEL_ENERGY,
              "fuelEnergy", 10_000_000L);
        fusionThermocoupleEfficiency = CachedDoubleValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_THERMOCOUPLE_EFFICIENCY.applyToBuilder(builder)
              .defineInRange("thermocoupleEfficiency", 0.05D, 0D, 1D));
        fusionCasingThermalConductivity = CachedDoubleValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_THERMAL_CONDUCTIVITY.applyToBuilder(builder)
              .defineInRange("casingThermalConductivity", 0.1D, 0.001D, 1D));
        fusionWaterHeatingRatio = CachedDoubleValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_HEATING_RATE.applyToBuilder(builder)
              .defineInRange("waterHeatingRatio", 0.3D, 0D, 1D));
        fusionFuelCapacity = CachedLongValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_FUEL_CAPACITY.applyToBuilder(builder)
              .defineInRange("fuelCapacity", FluidType.BUCKET_VOLUME, 2, 1_000L * FluidType.BUCKET_VOLUME));
        fusionEnergyCapacity = CachedLongValue.define(this, builder, BfrConfigTranslations.SERVER_FUSION_ENERGY_CAPACITY,
              "energyCapacity", 1_000_000_000, 1, Long.MAX_VALUE);
        int baseMaxWater = 1_000 * FluidType.BUCKET_VOLUME;
        fusionWaterPerInjection = CachedIntValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_WATER_INJECTION.applyToBuilder(builder)
              .defineInRange("waterPerInjection", 1_000 * FluidType.BUCKET_VOLUME, 1, Integer.MAX_VALUE / BFReactorMultiblockData.MAX_INJECTION));
        fusionSteamPerInjection = CachedLongValue.wrap(this, BfrConfigTranslations.SERVER_FUSION_STEAM_INJECTION.applyToBuilder(builder)
              .defineInRange("steamPerInjection", 100L * baseMaxWater, 1, Long.MAX_VALUE / BFReactorMultiblockData.MAX_INJECTION));
        builder.pop();
        BfrConfigTranslations.IRRADIATOR.applyToBuilder(builder).push("irradiator");
        irradiatorBaseProcessTicks = CachedIntValue.wrap(this, builder.comment("Default process time in ticks", "Recipe can override this value")
                .defineInRange("base_process_time", 200, 1, 10000));
        irradiatorCoolingRate = CachedIntValue.wrap(this, builder.comment("How much temperature production by reactor will be suppressed by Irradiator")
                .defineInRange("reactor_cooling", 5, 1, 10));
        builder.pop();
        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "bfr";
    }

    @Override
    public String getTranslation() {
        return "General Config";
    }

    @Override
    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }
}
