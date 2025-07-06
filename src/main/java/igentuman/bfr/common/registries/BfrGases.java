package igentuman.bfr.common.registries;

import mekanism.api.chemical.gas.Gas;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;

public class BfrGases {
    public static final GasDeferredRegister GASES = new GasDeferredRegister("mekanism");
    public static final GasRegistryObject<Gas> ENRICHED_NUCLEAR_WASTE;

    static {
        ENRICHED_NUCLEAR_WASTE = GASES.register("enriched_nuclear_waste", 0x00AAAA);
    }
}
