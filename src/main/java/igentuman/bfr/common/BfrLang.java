package igentuman.bfr.common;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

@NothingNullByDefault
public enum BfrLang implements ILangEntry {
    SOLIDIFIED_WASTE("item", "solidified_waste"),
    IRRADIATOR_NO_SOURCE("irradiator", "no_source"),
    IRRADIATOR_HAS_SOURCE("irradiator", "has_source"),
    IRRADIATOR_FLUX("irradiator", "flux"),
    IRRADIATOR_SOURCE_GUIDE("irradiator", "source_guide"),
    REACTOR_LOGIC_HELP1("reactor", "logic_help1"),
    REACTOR_LOGIC_HELP2("reactor", "logic_help2"),
    REACTOR_LOGIC_HELP3("reactor", "logic_help3"),
    REACTOR_LOGIC_HELP4("reactor", "logic_help4"),
    REACTOR_HELP_HEAT_MULTIPLIER1("reactor", "reactor_help_heat_multiplier1"),
    REACTOR_HELP_HEAT_MULTIPLIER2("reactor", "reactor_help_heat_multiplier2"),
    REACTOR_HELP_HEAT_MULTIPLIER3("reactor", "reactor_help_heat_multiplier3"),
    EFFICIENCY_GOOD("reactor", "efficiency_good"),
    EFFICIENCY_BAD("reactor", "efficiency_bad"),
    MIGHT_EXPLODE("reactor", "might_explode"),
    MIGHT_TURNOFF("reactor", "might_turnoff"),
    REACTOR_HELP1("reactor", "reactor_help1"),
    REACTOR_HELP2("reactor", "reactor_helps2"),
    REACTOR_HELP3("reactor", "reactor_help3"),
    REACTOR_CR("reactor", "efficiency.cr"),
    REACTOR_CURRENT_REACTIVITY("reactor", "efficiency.current_reactivity"),
    REACTOR_BUTTON_REACTIVITY_UP("reactor", "efficiency.reactivity_up_button"),
    REACTOR_BUTTON_REACTIVITY_DOWN("reactor", "efficiency.reactivity_down_button"),
    REACTOR_TARGET_REACTIVITY("reactor", "efficiency.target_reactivity"),
    REACTOR_ERROR_LEVEL("reactor", "efficiency.error_level"),
    REACTOR_EFFICIENCY("reactor", "efficiency.efficiency"),
    REACTOR_HEAT_MULTIPLIER("reactor", "efficiency.heat_multiplier"),
    REACTOR_LASER_READY_BUTTON("reactor", "efficiency.laser_ready_button"),
    REACTOR_LASER_MIN_ENERGY("reactor", "efficiency.laser_min_energy"),
    REACTOR_LASER_MIN_ENERGY_DESCR("reactor", "efficiency.laser_min_energy_descr"),
    REACTOR_TR("reactor", "efficiency.tr"),
    REACTOR_EF("reactor", "efficiency.ef"),
    REACTOR_ER("reactor", "efficiency.er"),
    MEKANISM_GENERATORS("constants", "mod_name"),
    PACK_DESCRIPTION("constants", "pack_description"),
    PRODUCING_AMOUNT("gui", "producing.amount"),
    STORED_BIO_FUEL("gui", "bio_generator.bio_fuel"),
    INSUFFICIENT_FUEL("tooltip", "hohlraum.insufficient_fuel"),
    READY_FOR_REACTION("tooltip", "hohlraum.ready_for_reaction"),
    GAS_BURN_RATE("gui", "gas_burning_generator.burn_rate"),
    POWER("gui", "power"),
    OUTPUT_RATE_SHORT("gui", "output_rate.short"),
    SKY_BLOCKED("wind_generator", "sky_blocked"),
    NO_WIND("wind_generator", "no_wind"),

    FUSION_REACTOR("reactor", "fusion_reactor"),
    REACTOR_PASSIVE("reactor", "stats.passive"),
    REACTOR_MIN_INJECTION("reactor", "stats.min_inject"),
    REACTOR_IGNITION("reactor", "stats.ignition"),
    REACTOR_MAX_PLASMA("reactor", "stats.max_plasma"),
    REACTOR_MAX_CASING("reactor", "stats.max_casing"),
    REACTOR_PASSIVE_RATE("reactor", "stats.passive_generation"),
    REACTOR_STEAM_PRODUCTION("reactor", "stats.steam_production"),
    REACTOR_ACTIVE("reactor", "stats.active"),
    HEAT_TAB("reactor", "heat"),
    STATS_TAB("reactor", "stats"),
    EFFICIENCY_TAB("reactor", "efficiency_tab"),
    REACTOR_PLASMA("reactor", "heat.plasma"),
    REACTOR_CASE("reactor", "heat.case"),
    FUEL_TAB("reactor", "fuel"),
    REACTOR_INJECTION_RATE("reactor", "fuel.injection_rate"),
    REACTOR_EDIT_RATE("reactor", "fuel.edit_rate"),
    REACTOR_PORT_EJECT("reactor", "configurator.port_eject"),
    REACTOR_LOGIC_TOGGLE_COOLING("reactor", "logic.toggle_cooling"),
    REACTOR_LOGIC_REDSTONE_MODE("reactor", "logic.redstone_output_mode"),
    REACTOR_LOGIC_ACTIVE_COOLING("reactor", "logic.active_cooling"),
    REACTOR_LOGIC_ACTIVATION("reactor", "logic.activation"),
    REACTOR_LOGIC_TEMPERATURE("reactor", "logic.temperature"),
    REACTOR_LOGIC_CRITICAL_WASTE_LEVEL("reactor", "logic.critical_waste_level"),
    REACTOR_LOGIC_DAMAGED("reactor", "logic.damaged"),
    REACTOR_LOGIC_OUTPUTTING("reactor", "logic.outputting"),
    REACTOR_LOGIC_POWERED("reactor", "logic.powered"),
    REACTOR_LOGIC_DISABLED("reactor", "logic.disabled"),
    REACTOR_LOGIC_READY("reactor", "logic.ready"),
    REACTOR_LOGIC_CAPACITY("reactor", "logic.capacity"),
    REACTOR_LOGIC_DEPLETED("reactor", "logic.depleted"),
    IRRADIATOR_DESCRIPTION("description", "irradiator"),
    LOGIC_GENERAL_TAB("reactor", "logic_general_tab"),
    LOGIC_IN_TAB("reactor", "logic_in_tab"),
    LOGIC_OUT_TAB("reactor", "logic_out_tab"),
    REACTOR_LOGIC_REACTIVITY_DOWN("reactor", "logic.reactivity_down"),
    REACTOR_LOGIC_ERROR_LEVEL("reactor", "logic.error_level"),
    REACTOR_LOGIC_INJECTION_UP("reactor", "logic.injection_up"),
    REACTOR_LOGIC_INJECTION_DOWN("reactor", "logic.injection_down"),
    REACTOR_LOGIC_REACTIVITY_UP("reactor", "logic.reactivity_up"),
    REACTOR_LOGIC_EFFICIENCY("reactor", "logic.efficiency"),
    DESCRIPTION_REACTOR_INJECTION_DOWN("description", "reactor.logic.injection_down"),
    DESCRIPTION_REACTOR_REACTIVITY_DOWN("description", "reactor.logic.reactivity_down"),
    DESCRIPTION_REACTOR_REACTIVITY_UP("description", "reactor.logic.reactivity_up"),
    DESCRIPTION_REACTOR_INJECTION_UP("description", "reactor.logic.injection_up"),
    DESCRIPTION_REACTOR_ERROR_LEVEL("description", "reactor.logic.error_level"),
    DESCRIPTION_REACTOR_EFFICIENCY("description", "reactor.logic.efficiency");

    private final String key;

    BfrLang(String type, String path) {
        this(Util.makeDescriptionId(type, BetterFusionReactor.rl(path)));
    }

    BfrLang(String key) {
        this.key = key;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}