package igentuman.bfr.client;

import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.registries.*;
import mekanism.client.lang.BaseLanguageProvider;
import net.minecraft.data.DataGenerator;

public class BfrLangProvider extends BaseLanguageProvider {

    public BfrLangProvider(DataGenerator gen) {
        super(gen, MekanismGenerators.MODID);
    }

    @Override
    protected void addTranslations() {
        addItems();
        addBlocks();
        addFluids();
        addGases();
        addSubtitles();
        addMisc();
    }

    private void addItems() {
        add(BfrItems.SOLAR_PANEL, "Solar Panel");
        add(BfrItems.HOHLRAUM, "Hohlraum");
        add(BfrItems.TURBINE_BLADE, "Turbine Blade");
    }

    private void addBlocks() {
        add(BfrBlocks.ADVANCED_SOLAR_GENERATOR, "Advanced Solar Generator");
        add(BfrBlocks.BIO_GENERATOR, "Bio-Generator");
        add(BfrBlocks.ELECTROMAGNETIC_COIL, "Electromagnetic Coil");
        add(BfrBlocks.GAS_BURNING_GENERATOR, "Gas-Burning Generator");
        add(BfrBlocks.HEAT_GENERATOR, "Heat Generator");
        add(BfrBlocks.SOLAR_GENERATOR, "Solar Generator");
        add(BfrBlocks.WIND_GENERATOR, "Wind Generator");
        add(BfrBlocks.REACTOR_GLASS, "Reactor Glass");
        add(BfrBlocks.FISSION_REACTOR_CASING, "Fission Reactor Casing");
        add(BfrBlocks.FISSION_REACTOR_PORT, "Fission Reactor Port");
        add(BfrBlocks.FISSION_REACTOR_LOGIC_ADAPTER, "Fission Reactor Logic Adapter");
        add(BfrBlocks.FISSION_FUEL_ASSEMBLY, "Fission Fuel Assembly");
        add(BfrBlocks.CONTROL_ROD_ASSEMBLY, "Control Rod Assembly");
        add(BfrBlocks.LASER_FOCUS_MATRIX, "Laser Focus Matrix");
        add(BfrBlocks.FUSION_REACTOR_CONTROLLER, "Fusion Reactor Controller");
        add(BfrBlocks.FUSION_REACTOR_FRAME, "Fusion Reactor Frame");
        add(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, "Fusion Reactor Logic Adapter");
        add(BfrBlocks.FUSION_REACTOR_PORT, "Fusion Reactor Port");
        add(BfrBlocks.ROTATIONAL_COMPLEX, "Rotational Complex");
        add(BfrBlocks.SATURATING_CONDENSER, "Saturating Condenser");
        add(BfrBlocks.TURBINE_CASING, "Turbine Casing");
        add(BfrBlocks.TURBINE_ROTOR, "Turbine Rotor");
        add(BfrBlocks.TURBINE_VALVE, "Turbine Valve");
        add(BfrBlocks.TURBINE_VENT, "Turbine Vent");
    }

    private void addFluids() {
        addFluid(BfrFluids.BIOETHANOL, "Bioethanol");
        addFluid(BfrFluids.DEUTERIUM, "Liquid Deuterium");
        addFluid(BfrFluids.FUSION_FUEL, "Liquid D-T Fuel");
        addFluid(BfrFluids.TRITIUM, "Liquid Tritium");
    }

    private void addGases() {
        add(BfrGases.DEUTERIUM, "Deuterium");
        add(BfrGases.FUSION_FUEL, "D-T Fuel");
        add(BfrGases.TRITIUM, "Tritium");
    }

    private void addMisc() {
        add(BfrLang.REACTOR_LOGIC_ACTIVE_COOLING, "Active cooling: %s");
        add(BfrLang.GAS_BURN_RATE, "Burn Rate: %s mB/t");
        add(BfrLang.STATS_TAB, "Stats");
        add(BfrLang.FUEL_TAB, "Fuel");
        add(BfrLang.HEAT_TAB, "Heat");
        add(BfrLang.INSUFFICIENT_FUEL, "Insufficient Fuel");
        add(BfrLang.IS_LIMITING, "(Limiting)");
        add(BfrLang.TURBINE_MAX_WATER_OUTPUT, "Max Water Output: %s mB/t");
        add(BfrLang.NO_WIND, "No wind");
        add(BfrLang.REACTOR_LOGIC_OUTPUTTING, "Outputting");
        add(BfrLang.REACTOR_LOGIC_ACTIVATION, "Activation");
        add(BfrLang.REACTOR_LOGIC_TEMPERATURE, "High Temperature");
        add(BfrLang.REACTOR_LOGIC_EXCESS_WASTE, "Excess Waste");
        add(BfrLang.REACTOR_LOGIC_DAMAGED, "Damage Critical");
        add(BfrLang.REACTOR_LOGIC_POWERED, "Powered");
        add(BfrLang.OUTPUT_RATE_SHORT, "Out: %s/t");
        add(BfrLang.POWER, "Power: %s");
        add(BfrLang.PRODUCING_AMOUNT, "Producing: %s/t");
        add(BfrLang.TURBINE_PRODUCTION, "Production");
        add(BfrLang.TURBINE_PRODUCTION_AMOUNT, "Production: %s");
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
        add(BfrLang.SKY_BLOCKED, "Sky blocked");
        add(BfrLang.TURBINE_STEAM_FLOW, "Steam Flow");
        add(BfrLang.TURBINE_STEAM_INPUT_RATE, "Steam Input: %s mB/t");
        add(BfrLang.STORED_BIO_FUEL, "BioFuel: %s");
        add(BfrLang.TURBINE_TANK_VOLUME, "Tank Volume: %s");
        add(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING, "Toggle Cooling Measurements");

        //Industrial Turbine
        add(BfrLang.TURBINE_INVALID_BAD_COMPLEX, "Couldn't form, found improperly placed Rotational Complex at %s. Complex must be centered above Turbine Rotors.");
        add(BfrLang.TURBINE_INVALID_BAD_ROTOR, "Couldn't form, found invalid Turbine Rotor at %s. Turbine Rotors must be centered below Rotational Complex.");
        add(BfrLang.TURBINE_INVALID_BAD_ROTORS, "Couldn't form, invalid Turbine Rotor arrangement.");
        add(BfrLang.TURBINE_INVALID_CONDENSER_BELOW_COMPLEX, "Couldn't form, found improperly placed Saturating Condenser at %s. Saturating Condensers must be above Pressure Disperser layer.");
        add(BfrLang.TURBINE_INVALID_EVEN_LENGTH, "Couldn't form, width and length of structure must be odd.");
        add(BfrLang.TURBINE_INVALID_MALFORMED_COILS, "Couldn't form, Electromagnetic Coil arrangement is malformed. Coils must be connected to Rotational Complex and adjacently connected.");
        add(BfrLang.TURBINE_INVALID_MALFORMED_DISPERSERS, "Couldn't form, Pressure Disperser arrangement is malformed. Dispersers must create complete horizontal layer surrounding Rotational Complex.");
        add(BfrLang.TURBINE_INVALID_MISSING_COMPLEX, "Couldn't form, no Rotational Complex present.");
        add(BfrLang.TURBINE_INVALID_MISSING_DISPERSER, "Couldn't form, expected but didn't find Pressure Disperser at %s.");
        add(BfrLang.TURBINE_INVALID_ROTORS_NOT_CONTIGUOUS, "Couldn't form, rotors are invalid (non-contiguous).");
        add(BfrLang.TURBINE_INVALID_TOO_NARROW, "Couldn't form, structure is too narrow to support turbine size.");
        add(BfrLang.TURBINE_INVALID_VENT_BELOW_COMPLEX, "Couldn't form, found a Turbine Vent below Pressure Disperser layer. Vents must be at or above vertical position of disperser layer.");
        add(BfrLang.TURBINE_INVALID_MISSING_COILS, "Couldn't form, no Electromagnetic Coils present.");

        add(BfrLang.TURBINE, "Industrial Turbine");
        add(BfrLang.TURBINE_BLADES, "Blades: %s %s");
        add(BfrLang.TURBINE_CAPACITY, "Capacity: %s mB");
        add(BfrLang.TURBINE_COILS, "Coils: %s %s");
        add(BfrLang.TURBINE_DISPERSERS, "Dispersers: %s %s");
        add(BfrLang.TURBINE_FLOW_RATE, "Flow rate: %s mB/t");
        add(BfrLang.TURBINE_MAX_FLOW, "Max flow: %s mB/t");
        add(BfrLang.TURBINE_MAX_PRODUCTION, "Max Production: %s");
        add(BfrLang.TURBINE_STATS, "Turbine Statistics");
        add(BfrLang.TURBINE_VENTS, "Vents: %s %s");
        //Fission Reactor
        add(BfrLang.FISSION_INVALID_BAD_CONTROL_ROD, "Couldn't form, improper placement for Control Rod Assembly at %s.");
        add(BfrLang.FISSION_INVALID_MISSING_CONTROL_ROD, "Couldn't form, missing control rod for fuel assembly at %s.");
        add(BfrLang.FISSION_INVALID_BAD_FUEL_ASSEMBLY, "Couldn't form, missing fuel assembly for control rod at %s.");
        add(BfrLang.FISSION_INVALID_EXTRA_CONTROL_ROD, "Couldn't form, found extra Control Rod Assembly at %s.");
        add(BfrLang.FISSION_INVALID_MALFORMED_FUEL_ASSEMBLY, "Couldn't form, invalid Fission Fuel Assembly placement at %s.");
        add(BfrLang.FISSION_INVALID_MISSING_FUEL_ASSEMBLY, "Couldn't form, no fuel assembly structures present.");

        add(BfrLang.FISSION_REACTOR, "Fission Reactor");
        add(BfrLang.FISSION_REACTOR_STATS, "Fission Reactor Statistics");
        add(BfrLang.FISSION_ACTIVATE, "Activate");
        add(BfrLang.FISSION_SCRAM, "SCRAM");
        add(BfrLang.FISSION_DAMAGE, "Damage: %s");
        add(BfrLang.FISSION_HEAT_STATISTICS, "Heat Statistics");
        add(BfrLang.FISSION_FORCE_DISABLED, "Reactor must reach safe damage and temperature levels before it can be reactivated.");
        add(BfrLang.FISSION_FUEL_STATISTICS, "Fuel Statistics");
        add(BfrLang.FISSION_HEAT_CAPACITY, "Heat Capacity: %s J/K");
        add(BfrLang.FISSION_SURFACE_AREA, "Fuel Surface Area: %s m2");
        add(BfrLang.FISSION_BOIL_EFFICIENCY, "Boil Efficiency: %s");
        add(BfrLang.FISSION_MAX_BURN_RATE, "Max Burn Rate: %s mB/t");
        add(BfrLang.FISSION_RATE_LIMIT, "Rate Limit: %s mB/t");
        add(BfrLang.FISSION_CURRENT_BURN_RATE, "Current Burn Rate:");
        add(BfrLang.FISSION_HEATING_RATE, "Heating Rate: %s mB/t");
        add(BfrLang.FISSION_SET_RATE_LIMIT, "Set Rate Limit:");
        add(BfrLang.FISSION_COOLANT_TANK, "Coolant Tank");
        add(BfrLang.FISSION_FUEL_TANK, "Fuel Tank");
        add(BfrLang.FISSION_HEATED_COOLANT_TANK, "Heated Coolant Tank");
        add(BfrLang.FISSION_WASTE_TANK, "Waste Tank");
        add(BfrLang.FISSION_HEAT_GRAPH, "Heat Graph:");
        add(BfrLang.FISSION_PORT_MODE_CHANGE, "Port mode changed to: %s");
        add(BfrLang.FISSION_PORT_MODE_INPUT, "input only");
        add(BfrLang.FISSION_PORT_MODE_OUTPUT_WASTE, "output waste");
        add(BfrLang.FISSION_PORT_MODE_OUTPUT_COOLANT, "output coolant");
        //Descriptions
        add(BfrLang.DESCRIPTION_REACTOR_CAPACITY, "Output when the reactor's core heat capacity has been met");
        add(BfrLang.DESCRIPTION_REACTOR_ACTIVATION, "Activate the reactor when powered, and deactivate when unpowered");
        add(BfrLang.DESCRIPTION_REACTOR_TEMPERATURE, "Output when the reactor reaches dangerous temperatures");
        add(BfrLang.DESCRIPTION_REACTOR_DAMAGED, "Output when the reactor reaches critical damage levels (100%+).");
        add(BfrLang.DESCRIPTION_REACTOR_EXCESS_WASTE, "Output when the reactor has excess waste");
        add(BfrLang.DESCRIPTION_REACTOR_DEPLETED, "Output when the reactor has insufficient fuel to sustain a reaction");
        add(BfrLang.DESCRIPTION_REACTOR_DISABLED, "Will not handle redstone");
        add(BfrLang.DESCRIPTION_REACTOR_READY, "Output when the reactor has reached the required heat level to ignite");
        //Generators
        add(BfrLang.DESCRIPTION_ADVANCED_SOLAR_GENERATOR, "An advanced generator that directly absorbs the sun's rays with little loss to produce energy.");
        add(BfrLang.DESCRIPTION_BIO_GENERATOR, "A generator that burns organic materials of the world to produce energy.");
        add(BfrLang.DESCRIPTION_ELECTROMAGNETIC_COIL, "A block that converts kinetic energy from a Rotational Complex into usable electricity. These can be placed in any arrangement above your Rotational Complex, as long as they are all touching each other and the complex itself.");
        add(BfrLang.DESCRIPTION_GAS_BURNING_GENERATOR, "A generator that harnesses the varying molecular gases to produce energy.");
        add(BfrLang.DESCRIPTION_HEAT_GENERATOR, "A generator that uses the heat of lava or other burnable resources to produce energy.");
        add(BfrLang.DESCRIPTION_SOLAR_GENERATOR, "A generator that uses the power of the sun to produce energy.");
        add(BfrLang.DESCRIPTION_WIND_GENERATOR, "A generator that uses the strength of the wind to produce energy, with greater efficiency at higher levels.");
        //Fission Reactor
        add(BfrLang.DESCRIPTION_FISSION_REACTOR_CASING, "Lead-infused steel casing used to create a Fission Reactor. Mostly heat-resistant, mostly radiation-resistant, and mostly safe!");
        add(BfrLang.DESCRIPTION_FISSION_REACTOR_PORT, "A port which can be placed on a Fission Reactor multiblock to transfer coolant, fuel, and waste.");
        add(BfrLang.DESCRIPTION_FISSION_REACTOR_LOGIC_ADAPTER, "A block that can be used to monitor or control the Fission Reactor with redstone.");
        add(BfrLang.DESCRIPTION_FISSION_FUEL_ASSEMBLY, "A cluster of fuel rods used to house fission fuel within a Fission Reactor. These can be stacked on top of each other.");
        add(BfrLang.DESCRIPTION_CONTROL_ROD_ASSEMBLY, "A collection of control rods used to halt a fission chain reaction. Placed on top of a tower of Fission Fuel Assemblies.");
        //Fusion Reactor
        add(BfrLang.DESCRIPTION_LASER_FOCUS_MATRIX, "A panel of Fusion Reactor Glass that is capable of absorbing optical energy and thereby heating up the Fusion Reactor.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_CONTROLLER, "The controlling block for the entire Fusion Reactor structure.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_FRAME, "Reinforced framing that can be used in the Fusion Reactor multiblock.");
        add(BfrLang.DESCRIPTION_REACTOR_GLASS, "Reinforced glass that can be used in the Fission Reactor and Fusion Reactor multiblocks (as well as any others!).");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_LOGIC_ADAPTER, "A block that can be used to allow basic monitoring of a reactor using redstone.");
        add(BfrLang.DESCRIPTION_FUSION_REACTOR_PORT, "A block of reinforced framing that is capable of managing both the gas and energy transfer of the Fusion Reactor.");
        //Turbine
        add(BfrLang.DESCRIPTION_ROTATIONAL_COMPLEX, "A connector that is placed on the highest Turbine Rotor of an Industrial Turbine to carry kinetic energy into its Electromagnetic Coils.");
        add(BfrLang.DESCRIPTION_SATURATING_CONDENSER, "A block that condenses steam processed by an Industrial Turbine into reusable water. These can be placed in any arrangement above your rotational complex.");
        add(BfrLang.DESCRIPTION_TURBINE_CASING, "Pressure-resistant casing used in the creation of an Industrial Turbine.");
        add(BfrLang.DESCRIPTION_TURBINE_ROTOR, "The steel rod that is used to house Turbine Blades within an Industrial Turbine.");
        add(BfrLang.DESCRIPTION_TURBINE_VALVE, "A type of Turbine Casing that includes a port for the transfer of energy and steam.");
        add(BfrLang.DESCRIPTION_TURBINE_VENT, "A type of Turbine Casing with an integrated vent for the release of steam. These should be placed on the level of or above the turbine's Rotational Complex.");

        //Modules
        add(BfrModules.GEOTHERMAL_GENERATOR_UNIT, "Geothermal Generator Unit", "Harnesses geothermal energy from the surrounding environment, and improves protection against damage from heat sources. Install multiple for faster charging and greater protection.");
    }
}