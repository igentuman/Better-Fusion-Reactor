package igentuman.bfr.client.datagen;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.datagen.lang.BaseLanguageProvider;
import igentuman.bfr.common.registries.*;
import net.minecraft.data.DataGenerator;

public class BfrLangProvider extends BaseLanguageProvider {

    public BfrLangProvider(DataGenerator gen) {
        super(gen, BetterFusionReactor.MODID);
    }

    @Override
    protected void addTranslations() {
        addBlocks();
        addMisc();
    }

    private void addBlocks() {
        add(BfrBlocks.REACTOR_GLASS, "Reactor Glass");
        add(BfrBlocks.LASER_FOCUS_MATRIX, "Laser Focus Matrix");
        add(BfrBlocks.FUSION_REACTOR_CONTROLLER, "Fusion Reactor Controller");
        add(BfrBlocks.FUSION_REACTOR_FRAME, "Fusion Reactor Frame");
        add(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, "Fusion Reactor Logic Adapter");
        add(BfrBlocks.FUSION_REACTOR_PORT, "Fusion Reactor Port");

    }

    private void addMisc() {
        add(BfrLang.REACTOR_LOGIC_ACTIVE_COOLING, "Active cooling: %1$s");
        add(BfrLang.STATS_TAB, "Stats");
        add(BfrLang.FUEL_TAB, "Fuel");
        add(BfrLang.HEAT_TAB, "Heat");
        add(BfrLang.LOGIC_OUT_TAB, "Output Signals");
        add(BfrLang.LOGIC_IN_TAB, "Input Signals");
        add(BfrLang.LOGIC_GENERAL_TAB, "General");
        add(BfrLang.REACTOR_ER, "ER");
        add(BfrLang.REACTOR_CR, "CR");
        add(BfrLang.REACTOR_TR, "TR");
        add(BfrLang.REACTOR_EF, "EF");
        add(BfrLang.REACTOR_LASER_READY_BUTTON, "Ready for Laser Impulse");
        add(BfrLang.REACTOR_BUTTON_REACTIVITY_UP, "+5");
        add(BfrLang.REACTOR_BUTTON_REACTIVITY_DOWN, "-5");
        add(BfrLang.REACTOR_HEAT_MULTIPLIER, "Heat Multiplier: %1$s");
        add(BfrLang.REACTOR_CURRENT_REACTIVITY, "Current Reactivity: %1$s");
        add(BfrLang.REACTOR_TARGET_REACTIVITY, "Target Reactivity: %1$s");
        add(BfrLang.REACTOR_EFFICIENCY, "Efficiency: %1$s");
        add(BfrLang.REACTOR_ERROR_LEVEL, "Error Level: %1$s");
        add(BfrLang.EFFICIENCY_TAB, "Efficiency");
        add(BfrLang.INSUFFICIENT_FUEL, "Insufficient Fuel");
        add(BfrLang.REACTOR_LOGIC_OUTPUTTING, "Outputting");
        add(BfrLang.REACTOR_LOGIC_ACTIVATION, "Activation");
        add(BfrLang.REACTOR_LOGIC_TEMPERATURE, "High Temperature");
        add(BfrLang.REACTOR_LOGIC_POWERED, "Powered");
        add(BfrLang.POWER, "Power: %1$s");
        add(BfrLang.PRODUCING_AMOUNT, "Producing: %1$s/t");
        add(BfrLang.REACTOR_LASER_MIN_ENERGY, "Laser Impulse min energy: %1$s");
        add(BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR, "Avoid negative effects of Hm (1 minute)");
        add(BfrLang.REACTOR_ACTIVE, "Water-Cooled");
        add(BfrLang.REACTOR_LOGIC_CAPACITY, "Heat Capacity Met");
        add(BfrLang.REACTOR_CASE, "Case: %1$s");
        add(BfrLang.REACTOR_LOGIC_DEPLETED, "Insufficient Fuel");
        add(BfrLang.REACTOR_LOGIC_DISABLED, "Disabled");
        add(BfrLang.REACTOR_EDIT_RATE, "Edit Rate:");
        add(BfrLang.REACTOR_IGNITION, "Ignition Temp: %1$s");
        add(BfrLang.REACTOR_INJECTION_RATE, "Injection Rate: %1$s");
        add(BfrLang.REACTOR_MAX_CASING, "Max. Casing Temp: %1$s");
        add(BfrLang.REACTOR_MAX_PLASMA, "Max. Plasma Temp: %1$s");
        add(BfrLang.REACTOR_MIN_INJECTION, "Min. Inject Rate: %1$s");
        add(BfrLang.FUSION_REACTOR, "Fusion Reactor");
        add(BfrLang.REACTOR_PASSIVE, "Air-Cooled");
        add(BfrLang.REACTOR_PASSIVE_RATE, "Passive Generation: %1$s/t");
        add(BfrLang.REACTOR_PLASMA, "Plasma: %1$s");
        add(BfrLang.REACTOR_PORT_EJECT, "Toggled Reactor Port eject mode to: %1$s.");
        add(BfrLang.REACTOR_LOGIC_READY, "Ready for Ignition");
        add(BfrLang.REACTOR_STEAM_PRODUCTION, "Steam Production: %1$s mB/t");
        add(BfrLang.READY_FOR_REACTION, "Ready for Reaction!");
        add(BfrLang.REACTOR_LOGIC_REDSTONE_MODE, "Redstone mode: %1$s");
        add(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING, "Toggle Cooling Measurements");
        add(BfrLang.REACTOR_LOGIC_REACTIVITY_UP, "+ Reactivity");
        add(BfrLang.REACTOR_LOGIC_REACTIVITY_DOWN, "- Reactivity");
        add(BfrLang.REACTOR_LOGIC_INJECTION_DOWN, "-2 Injection Rate");
        add(BfrLang.REACTOR_LOGIC_INJECTION_UP, "+2 Injection Rate");
        add(BfrLang.REACTOR_LOGIC_EFFICIENCY, "Efficiency");
        add(BfrLang.REACTOR_LOGIC_ERROR_LEVEL, "Error Level");
      //Descriptions
        add(BfrLang.DESCRIPTION_REACTOR_CAPACITY, "Output when the reactor's core heat capacity has been met");
        add(BfrLang.DESCRIPTION_REACTOR_ERROR_LEVEL, "100% = full redstone signal strength (15 blocks). 1 block ~ 7%");
        add(BfrLang.DESCRIPTION_REACTOR_EFFICIENCY, "100% = full redstone signal strength (15 blocks). 1 block ~ 7%");
        add(BfrLang.DESCRIPTION_REACTOR_INJECTION_UP, "Input redstone impulse any strength");
        add(BfrLang.DESCRIPTION_REACTOR_INJECTION_DOWN, "Input redstone impulse any strength");
        add(BfrLang.DESCRIPTION_REACTOR_REACTIVITY_DOWN, "Redstone impulse will decrease CR by the strength of impulse (1-15)");
        add(BfrLang.DESCRIPTION_REACTOR_REACTIVITY_UP, "Redstone impulse will increase CR by the strength of impulse (1-15)");
        add(BfrLang.DESCRIPTION_REACTOR_ACTIVATION, "Activate the reactor when powered, and deactivate when unpowered");
        add(BfrLang.DESCRIPTION_REACTOR_TEMPERATURE, "Output when the reactor reaches dangerous temperatures");
        add(BfrLang.DESCRIPTION_REACTOR_DAMAGED, "Output when the reactor reaches critical damage levels (100%+).");
        add(BfrLang.DESCRIPTION_REACTOR_EXCESS_WASTE, "Output when the reactor has excess waste");
        add(BfrLang.DESCRIPTION_REACTOR_DEPLETED, "Output when the reactor has insufficient fuel to sustain a reaction");
        add(BfrLang.DESCRIPTION_REACTOR_DISABLED, "Will not handle redstone");
        add(BfrLang.DESCRIPTION_REACTOR_READY, "Output when the reactor has reached the required heat level to ignite");
        //Fusion Reactor
        add(BfrLang.DESCRIPTION_LASER_FOCUS_MATRIX, "A panel of Fusion Reactor Glass that is capable of absorbing optical energy and thereby heating up the Fusion Reactor.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_CONTROLLER, "The controlling block for the entire Fusion Reactor structure.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_FRAME, "Reinforced framing that can be used in the Fusion Reactor multiblock.");
        add(BfrLang.DESCRIPTION_REACTOR_GLASS, "Reinforced glass that can be used in the Fission Reactor and Fusion Reactor multiblocks (as well as any others!).");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_LOGIC_ADAPTER, "A block that can be used to allow basic monitoring of a reactor using redstone.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_PORT, "A block of reinforced framing that is capable of managing both the gas and energy transfer of the Fusion Reactor.");
               //Modules
        }
}