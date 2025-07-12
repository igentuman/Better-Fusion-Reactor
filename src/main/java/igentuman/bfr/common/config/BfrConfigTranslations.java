package igentuman.bfr.common.config;

import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.TranslationPreset;
import igentuman.bfr.common.BetterFusionReactor;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum BfrConfigTranslations implements IConfigTranslation {

    SERVER_FUSION("server.fusion", "Better Fusion Reactor", "Settings for configuring Better Fusion Reactors", "Edit Reactor Settings"),
    IRRADIATOR("irradiator", "Irradiator", "Settings for configuring Irradiator", "Edit Irradiator Settings"),
    SERVER_FUSION_FUEL_ENERGY("server.fusion.fuel_energy", "Energy Per D-T Fuel", "Affects the Injection Rate, Max Temp, and Ignition Temp."),
    SERVER_FUSION_THERMOCOUPLE_EFFICIENCY("server.fusion.thermocouple_efficiency", "Thermocouple Efficiency",
          "The fraction of the heat dissipated from the case that is converted to Joules."),
    SERVER_FUSION_THERMAL_CONDUCTIVITY("server.fusion.casing_thermal_conductivity", "Casing Thermal Conductivity",
          "The fraction of heat from the casing that can be transferred to all sources that are not water. Will impact max heat, heat transfer to "
          + "thermodynamic conductors, and power generation."),
    SERVER_FUSION_HEATING_RATE("server.fusion.water_heating_ratio", "Water Heating Ratio",
          "The fraction of the heat from the casing that is dissipated to water when water cooling is in use. Will impact max heat, and steam generation."),
    SERVER_FUSION_FUEL_CAPACITY("server.fusion.capacity.fuel", "Fuel Capacity", "Amount of fuel (mB) that the fusion reactor can store."),
    SERVER_FUSION_ENERGY_CAPACITY("server.fusion.capacity.energy", "Energy Capacity", "Amount of energy (Joules) the fusion reactor can store."),
    SERVER_FUSION_WATER_INJECTION("server.fusion.injection.water", "Water Per Injection",
          "Amount of water (mB) per injection rate that the fusion reactor can store. Max = injectionRate * waterPerInjection"),
    SERVER_FUSION_STEAM_INJECTION("server.fusion.injection.steam", "Steam Per Injection",
          "Amount of steam (mB) per injection rate that the fusion reactor can store. Max = injectionRate * steamPerInjection");

    private final String key;
    private final String title;
    private final String tooltip;
    @Nullable
    private final String button;

    BfrConfigTranslations(TranslationPreset preset, String type) {
        this(preset.path(type), preset.title(type), preset.tooltip(type));
    }

    BfrConfigTranslations(String path, String title, String tooltip) {
        this(path, title, tooltip, false);
    }

    BfrConfigTranslations(String path, String title, String tooltip, boolean isSection) {
        this(path, title, tooltip, IConfigTranslation.getSectionTitle(title, isSection));
    }

    BfrConfigTranslations(String path, String title, String tooltip, @Nullable String button) {
        this.key = Util.makeDescriptionId("configuration", BetterFusionReactor.rl(path));
        this.title = title;
        this.tooltip = tooltip;
        this.button = button;
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return key;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String tooltip() {
        return tooltip;
    }

    @Nullable
    @Override
    public String button() {
        return button;
    }
}