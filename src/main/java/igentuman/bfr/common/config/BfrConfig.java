package igentuman.bfr.common.config;

import mekanism.common.config.BaseMekanismConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public class BfrConfig extends BaseMekanismConfig {

    private static final String FUSION_CATEGORY = "fusion_reactor";

    private final ForgeConfigSpec configSpec;

    BfrConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Mekanism Generators Config. This config is synced between server and client.").push("bfr");

        builder.comment("Fusion Settings").push(FUSION_CATEGORY);
        //fusionThermocoupleEfficiency = CachedDoubleValue.wrap(this, builder.comment("The fraction of the heat dissipated from the case that is converted to Joules.")
             // .defineInRange("thermocoupleEfficiency", 0.05D, 0D, 1D));
        //fusionCasingThermalConductivity = CachedDoubleValue.wrap(this, builder.comment("The fraction fraction of heat from the casing that can be transferred to all sources that are not water. Will impact max heat, heat transfer to thermodynamic conductors, and power generation.")
            //  .defineInRange("casingThermalConductivity", 0.1D, 0.001D, 1D));
       // fusionWaterHeatingRatio = CachedDoubleValue.wrap(this, builder.comment("The fraction of the heat from the casing that is dissipated to water when water cooling is in use. Will impact max heat, and steam generation.")
          //    .defineInRange("waterHeatingRatio", 0.3D, 0D, 1D));
        builder.pop();


        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "bfr";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }
}
