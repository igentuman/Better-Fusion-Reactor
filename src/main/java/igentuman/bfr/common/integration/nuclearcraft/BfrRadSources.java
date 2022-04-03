package igentuman.bfr.common.integration.nuclearcraft;
import mekanism.generators.common.GeneratorsItems;
import nc.radiation.RadSources;

public class BfrRadSources {
    public static void init()
    {
        RadSources.put(0.005, GeneratorsItems.Hohlraum);
        RadSources.putFluid(0.05, "deuterium");
        RadSources.putFluid(0.05, "tritium");
        RadSources.putFluid(0.1, "liquidtritium");
        RadSources.putFluid(0.1, "liquiddeuterium");
        RadSources.putFluid(0.1, "liquidfusionfuel");
    }
}
