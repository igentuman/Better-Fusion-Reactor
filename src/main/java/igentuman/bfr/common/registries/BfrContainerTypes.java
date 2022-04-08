package igentuman.bfr.common.registries;

import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.container.FusionReactorFuelTabContainer;
import igentuman.bfr.common.container.FusionReactorHeatTabContainer;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;

public class BfrContainerTypes {

    private BfrContainerTypes() {
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(BetterFusionReactor.MODID);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_CONTROLLER = CONTAINER_TYPES.register(BfrBlocks.FUSION_REACTOR_CONTROLLER, TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_FUEL = CONTAINER_TYPES.register("fusion_reactor_fuel", TileEntityFusionReactorController.class, FusionReactorFuelTabContainer::new);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_HEAT = CONTAINER_TYPES.register("fusion_reactor_heat", TileEntityFusionReactorController.class, FusionReactorHeatTabContainer::new);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> FUSION_REACTOR_LOGIC_ADAPTER = CONTAINER_TYPES.registerEmpty(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, TileEntityFusionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_STATS = CONTAINER_TYPES.registerEmpty("fusion_reactor_stats", TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_EFFICIENCY = CONTAINER_TYPES.registerEmpty("fusion_reactor_efficiency", TileEntityFusionReactorController.class);
}