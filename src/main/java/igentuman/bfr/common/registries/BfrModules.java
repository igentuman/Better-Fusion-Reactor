package igentuman.bfr.common.registries;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import igentuman.bfr.common.BetterFusionReactor;

//Note: We need to declare our item providers like we do so that they don't end up being null due to us referencing these objects from the items
@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
public class BfrModules {

    private BfrModules() {
    }

    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(BetterFusionReactor.MODID);

}