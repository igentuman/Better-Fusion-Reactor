package igentuman.bfr.common.config;

import mekanism.common.config.MekanismConfigHelper;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class BetterFusionReactorConfig {

    private BetterFusionReactorConfig() {
    }

    public static final BfrConfig generators = new BfrConfig();
    public static final GeneratorsGearConfig gear = new GeneratorsGearConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getActiveContainer();
        MekanismConfigHelper.registerConfig(modContainer, generators);
        MekanismConfigHelper.registerConfig(modContainer, gear);
    }
}