package igentuman.bfr.common.registries;

import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.DataComponentDeferredRegister;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
import net.minecraft.core.component.DataComponentType;

public class GeneratorsDataComponents {

    private GeneratorsDataComponents() {
    }

    public static final DataComponentDeferredRegister DATA_COMPONENTS = new DataComponentDeferredRegister(BetterFusionReactor.MODID);

    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<FusionReactorLogic>> FUSION_LOGIC_TYPE = DATA_COMPONENTS.simple("fusion_logic",
          builder -> builder.persistent(FusionReactorLogic.CODEC)
                .networkSynchronized(FusionReactorLogic.STREAM_CODEC)
    );

    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> ACTIVE_COOLED = DATA_COMPONENTS.registerBoolean("active_cooled");
}