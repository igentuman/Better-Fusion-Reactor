package igentuman.bfr.common.advancements;

import mekanism.common.advancements.MekanismAdvancement;
import mekanism.common.advancements.MekanismAdvancements;
import igentuman.bfr.common.BetterFusionReactor;
import org.jetbrains.annotations.Nullable;

public class GeneratorsAdvancements {

    private GeneratorsAdvancements() {
    }

    private static MekanismAdvancement advancement(@Nullable MekanismAdvancement parent, String name) {
        return new MekanismAdvancement(parent, BetterFusionReactor.rl(name));
    }

    public static final MekanismAdvancement HEAT_GENERATOR = advancement(MekanismAdvancements.MATERIALS, "heat_generator");
    public static final MekanismAdvancement SOLAR_GENERATOR = advancement(HEAT_GENERATOR, "solar_generator");
    public static final MekanismAdvancement WIND_GENERATOR = advancement(HEAT_GENERATOR, "wind_generator");
    public static final MekanismAdvancement BURN_THE_GAS = advancement(WIND_GENERATOR, "burn_the_gas");
}