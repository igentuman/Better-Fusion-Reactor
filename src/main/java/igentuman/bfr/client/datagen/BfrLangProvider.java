package igentuman.bfr.client;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.registries.*;
import mekanism.client.lang.BaseLanguageProvider;
import net.minecraft.data.DataGenerator;

public class BfrLangProvider extends BaseLanguageProvider {

    public BfrLangProvider(DataGenerator gen) {
        super(gen, BetterFusionReactor.MODID);
    }

    @Override
    protected void addTranslations() {
        addItems();
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
        add(BfrLang.REACTOR_LOGIC_ACTIVE_COOLING, "Active cooling: %s");
        add(BfrLang.STATS_TAB, "Stats");
        add(BfrLang.FUEL_TAB, "Fuel");
        add(BfrLang.HEAT_TAB, "Heat");
        add(BfrLang.INSUFFICIENT_FUEL, "Insufficient Fuel");
        add(BfrLang.REACTOR_LOGIC_OUTPUTTING, "Outputting");
        add(BfrLang.REACTOR_LOGIC_ACTIVATION, "Activation");
        add(BfrLang.REACTOR_LOGIC_TEMPERATURE, "High Temperature");
        add(BfrLang.REACTOR_LOGIC_POWERED, "Powered");
        add(BfrLang.POWER, "Power: %s");
        add(BfrLang.PRODUCING_AMOUNT, "Producing: %s/t");
        add(BfrLang.REACTOR_ACTIVE, "Water-Cooled");
        add(BfrLang.REACTOR_LOGIC_CAPACITY, "Heat Capacity Met");
        add(BfrLang.REACTOR_CASE, "Case: %s");
        add(BfrLang.REACTOR_LOGIC_DEPLETED, "Insufficient Fuel");
        add(BfrLang.REACTOR_LOGIC_DISABLED, "Disabled");
        add(BfrLang.REACTOR_EDIT_RATE, "Edit Rate:");
        add(BfrLang.REACTOR_IGNITION, "Ignition Temp: %s");
        add(BfrLang.REACTOR_INJECTION_RATE, "Injection Rate: %s");
        add(BfrLang.REACTOR_MAX_CASING, "Max. Casing Temp: %s");
        add(BfrLang.REACTOR_MAX_PLASMA, "Max. Plasma Temp: %s");
        add(BfrLang.REACTOR_MIN_INJECTION, "Min. Inject Rate: %s");
        add(BfrLang.FUSION_REACTOR, "Fusion Reactor");
        add(BfrLang.REACTOR_PASSIVE, "Air-Cooled");
        add(BfrLang.REACTOR_PASSIVE_RATE, "Passive Generation: %s/t");
        add(BfrLang.REACTOR_PLASMA, "Plasma: %s");
        add(BfrLang.REACTOR_PORT_EJECT, "Toggled Reactor Port eject mode to: %s.");
        add(BfrLang.REACTOR_LOGIC_READY, "Ready for Ignition");
        add(BfrLang.REACTOR_STEAM_PRODUCTION, "Steam Production: %s mB/t");
        add(BfrLang.READY_FOR_REACTION, "Ready for Reaction!");
        add(BfrLang.REACTOR_LOGIC_REDSTONE_MODE, "Redstone mode: %s");
        add(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING, "Toggle Cooling Measurements");
      //Descriptions
        add(BfrLang.DESCRIPTION_REACTOR_CAPACITY, "Output when the reactor's core heat capacity has been met");
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
        add(BfrModules.GEOTHERMAL_GENERATOR_UNIT, "Geothermal Generator Unit", "Harnesses geothermal energy from the surrounding environment, and improves protection against damage from heat sources. Install multiple for faster charging and greater protection.");
    }
}