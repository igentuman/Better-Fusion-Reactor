package igentuman.bfr.common.registries;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.tile.TileEntityIrradiator;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;

public class BfrContainerTypes {

    private BfrContainerTypes() {
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(BetterFusionReactor.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityIrradiator>> IRRADIATOR = CONTAINER_TYPES.register(BfrBlocks.IRRADIATOR, TileEntityIrradiator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_CONTROLLER = CONTAINER_TYPES.custom(BfrBlocks.FUSION_REACTOR_CONTROLLER, TileEntityFusionReactorController.class).offset(5, 0).build();
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_FUEL = CONTAINER_TYPES.registerEmpty("fusion_reactor_fuel", TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_HEAT = CONTAINER_TYPES.registerEmpty("fusion_reactor_heat", TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> FUSION_REACTOR_LOGIC_ADAPTER = CONTAINER_TYPES.registerEmpty(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, TileEntityFusionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_STATS = CONTAINER_TYPES.registerEmpty("fusion_reactor_stats", TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> FUSION_REACTOR_LOGIC_IN = CONTAINER_TYPES.registerEmpty("fusion_reactor_logic_inl", TileEntityFusionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> FUSION_REACTOR_LOGIC_OUT = CONTAINER_TYPES.registerEmpty("fusion_reactor_logic_out", TileEntityFusionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_EFFICIENCY = CONTAINER_TYPES.registerEmpty("fusion_reactor_efficiency", TileEntityFusionReactorController.class);

}