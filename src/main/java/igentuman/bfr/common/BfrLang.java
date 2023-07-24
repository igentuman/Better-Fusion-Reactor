package igentuman.bfr.common;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public enum BfrLang implements ILangEntry {
    SOLIDIFIED_WASTE("item", "solidified_waste"),
    DESCRIPTION_IRRADIATOR("irradiator", "irradiator_desc"),
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
    REACTOR_ACTIVE("reactor", "stats.active"),
    HEAT_TAB("reactor", "heat"),
    STATS_TAB("reactor", "stats"),
    EFFICIENCY_TAB("reactor", "efficiency_tab"),
    FUEL_TAB("reactor", "fuel"),
    LOGIC_GENERAL_TAB("reactor", "logic_general_tab"),
    LOGIC_IN_TAB("reactor", "logic_in_tab"),
    LOGIC_OUT_TAB("reactor", "logic_out_tab"),
    REACTOR_LOGIC_REACTIVITY_DOWN("reactor", "logic.reactivity_down"),
    REACTOR_LOGIC_ERROR_LEVEL("reactor", "logic.error_level"),
    REACTOR_LOGIC_INJECTION_UP("reactor", "logic.injection_up"),
    REACTOR_LOGIC_INJECTION_DOWN("reactor", "logic.injection_down"),
    REACTOR_LOGIC_REACTIVITY_UP("reactor", "logic.reactivity_up"),
    REACTOR_LOGIC_EFFICIENCY("reactor", "logic.efficiency"),
    REACTOR_LOGIC_READY("reactor", "logic.ready"),
    REACTOR_LOGIC_CAPACITY("reactor", "logic.capacity"),
    REACTOR_LOGIC_DEPLETED("reactor", "logic.depleted"),

    //Descriptions
    DESCRIPTION_REACTOR_INJECTION_DOWN("description", "reactor.logic.injection_down"),
    DESCRIPTION_REACTOR_REACTIVITY_DOWN("description", "reactor.logic.reactivity_down"),
    DESCRIPTION_REACTOR_REACTIVITY_UP("description", "reactor.logic.reactivity_up"),
    DESCRIPTION_REACTOR_INJECTION_UP("description", "reactor.logic.injection_up"),
    DESCRIPTION_REACTOR_ERROR_LEVEL("description", "reactor.logic.error_level"),
    DESCRIPTION_REACTOR_EFFICIENCY("description", "reactor.logic.efficiency"),
    DESCRIPTION_REACTOR_READY("description", "reactor.logic.ready"),
    DESCRIPTION_REACTOR_CAPACITY("description", "reactor.logic.capacity"),
    DESCRIPTION_REACTOR_DEPLETED("description", "reactor.logic.depleted");

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