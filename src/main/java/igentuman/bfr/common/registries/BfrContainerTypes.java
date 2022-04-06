package igentuman.bfr.common.registries;

import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.container.FusionReactorFuelTabContainer;
import igentuman.bfr.common.container.FusionReactorHeatTabContainer;
import igentuman.bfr.common.tile.TileEntityAdvancedSolarGenerator;
import igentuman.bfr.common.tile.TileEntityBioGenerator;
import igentuman.bfr.common.tile.TileEntityGasGenerator;
import igentuman.bfr.common.tile.TileEntityHeatGenerator;
import igentuman.bfr.common.tile.TileEntitySolarGenerator;
import igentuman.bfr.common.tile.TileEntityWindGenerator;
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorCasing;
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineCasing;

public class BfrContainerTypes {

    private BfrContainerTypes() {
    }

    public static final ContainerTypeDeferredRegister CONTAINER_TYPES = new ContainerTypeDeferredRegister(BetterFusionReactor.MODID);

    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityBioGenerator>> BIO_GENERATOR = CONTAINER_TYPES.register(BfrBlocks.BIO_GENERATOR, TileEntityBioGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityGasGenerator>> GAS_BURNING_GENERATOR = CONTAINER_TYPES.register(BfrBlocks.GAS_BURNING_GENERATOR, TileEntityGasGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityHeatGenerator>> HEAT_GENERATOR = CONTAINER_TYPES.register(BfrBlocks.HEAT_GENERATOR, TileEntityHeatGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityTurbineCasing>> INDUSTRIAL_TURBINE = CONTAINER_TYPES.register("industrial_turbine", TileEntityTurbineCasing.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFissionReactorCasing>> FISSION_REACTOR = CONTAINER_TYPES.custom("fission_reactor", TileEntityFissionReactorCasing.class).offset(10, 91).build();
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFissionReactorCasing>> FISSION_REACTOR_STATS = CONTAINER_TYPES.registerEmpty("fission_reactor_stats", TileEntityFissionReactorCasing.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFissionReactorLogicAdapter>> FISSION_REACTOR_LOGIC_ADAPTER = CONTAINER_TYPES.registerEmpty(BfrBlocks.FISSION_REACTOR_LOGIC_ADAPTER, TileEntityFissionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_CONTROLLER = CONTAINER_TYPES.register(BfrBlocks.FUSION_REACTOR_CONTROLLER, TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_FUEL = CONTAINER_TYPES.register("fusion_reactor_fuel", TileEntityFusionReactorController.class, FusionReactorFuelTabContainer::new);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_HEAT = CONTAINER_TYPES.register("fusion_reactor_heat", TileEntityFusionReactorController.class, FusionReactorHeatTabContainer::new);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> FUSION_REACTOR_LOGIC_ADAPTER = CONTAINER_TYPES.registerEmpty(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, TileEntityFusionReactorLogicAdapter.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityFusionReactorController>> FUSION_REACTOR_STATS = CONTAINER_TYPES.registerEmpty("fusion_reactor_stats", TileEntityFusionReactorController.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntitySolarGenerator>> SOLAR_GENERATOR = CONTAINER_TYPES.register("solar_generator", TileEntitySolarGenerator.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityAdvancedSolarGenerator>> ADVANCED_SOLAR_GENERATOR = CONTAINER_TYPES.register("advanced_solar_generator", TileEntityAdvancedSolarGenerator.class);
    public static final ContainerTypeRegistryObject<EmptyTileContainer<TileEntityTurbineCasing>> TURBINE_STATS = CONTAINER_TYPES.registerEmpty("turbine_stats", TileEntityTurbineCasing.class);
    public static final ContainerTypeRegistryObject<MekanismTileContainer<TileEntityWindGenerator>> WIND_GENERATOR = CONTAINER_TYPES.register(BfrBlocks.WIND_GENERATOR, TileEntityWindGenerator.class);
}