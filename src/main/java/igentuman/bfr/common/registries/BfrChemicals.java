package igentuman.bfr.common.registries;

import mekanism.api.chemical.Chemical;
import mekanism.common.Mekanism;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class BfrChemicals {

    private BfrChemicals() {
    }

    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(Mekanism.MODID);

    public static final DeferredChemical<Chemical> ENRICHED_NUCLEAR_WASTE = CHEMICALS.register("enriched_nuclear_waste", 0x00BBAA);
}