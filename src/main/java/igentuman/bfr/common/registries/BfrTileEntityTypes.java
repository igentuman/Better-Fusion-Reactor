package igentuman.bfr.common.registries;

import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.tile.TileEntityAdvancedSolarGenerator;
import igentuman.bfr.common.tile.TileEntityBioGenerator;
import igentuman.bfr.common.tile.TileEntityGasGenerator;
import igentuman.bfr.common.tile.TileEntityHeatGenerator;
import igentuman.bfr.common.tile.TileEntityReactorGlass;
import igentuman.bfr.common.tile.TileEntitySolarGenerator;
import igentuman.bfr.common.tile.TileEntityWindGenerator;
import igentuman.bfr.common.tile.fission.TileEntityControlRodAssembly;
import igentuman.bfr.common.tile.fission.TileEntityFissionFuelAssembly;
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorCasing;
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorLogicAdapter;
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorPort;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import igentuman.bfr.common.tile.fusion.TileEntityLaserFocusMatrix;
import igentuman.bfr.common.tile.turbine.TileEntityElectromagneticCoil;
import igentuman.bfr.common.tile.turbine.TileEntityRotationalComplex;
import igentuman.bfr.common.tile.turbine.TileEntitySaturatingCondenser;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineCasing;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineRotor;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineValve;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineVent;

public class BfrTileEntityTypes {

    private BfrTileEntityTypes() {
    }

    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(BetterFusionReactor.MODID);

    //Generators
    public static final TileEntityTypeRegistryObject<TileEntityAdvancedSolarGenerator> ADVANCED_SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.ADVANCED_SOLAR_GENERATOR, TileEntityAdvancedSolarGenerator::new);
    public static final TileEntityTypeRegistryObject<TileEntityBioGenerator> BIO_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.BIO_GENERATOR, TileEntityBioGenerator::new);
    public static final TileEntityTypeRegistryObject<TileEntityGasGenerator> GAS_BURNING_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.GAS_BURNING_GENERATOR, TileEntityGasGenerator::new);
    public static final TileEntityTypeRegistryObject<TileEntityHeatGenerator> HEAT_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.HEAT_GENERATOR, TileEntityHeatGenerator::new);
    public static final TileEntityTypeRegistryObject<TileEntitySolarGenerator> SOLAR_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.SOLAR_GENERATOR, TileEntitySolarGenerator::new);
    public static final TileEntityTypeRegistryObject<TileEntityWindGenerator> WIND_GENERATOR = TILE_ENTITY_TYPES.register(BfrBlocks.WIND_GENERATOR, TileEntityWindGenerator::new);
    //Misc
    public static final TileEntityTypeRegistryObject<TileEntityReactorGlass> REACTOR_GLASS = TILE_ENTITY_TYPES.register(BfrBlocks.REACTOR_GLASS, TileEntityReactorGlass::new);
    //Fission Reactor
    public static final TileEntityTypeRegistryObject<TileEntityFissionReactorCasing> FISSION_REACTOR_CASING = TILE_ENTITY_TYPES.register(BfrBlocks.FISSION_REACTOR_CASING, TileEntityFissionReactorCasing::new);
    public static final TileEntityTypeRegistryObject<TileEntityFissionReactorPort> FISSION_REACTOR_PORT = TILE_ENTITY_TYPES.register(BfrBlocks.FISSION_REACTOR_PORT, TileEntityFissionReactorPort::new);
    public static final TileEntityTypeRegistryObject<TileEntityFissionReactorLogicAdapter> FISSION_REACTOR_LOGIC_ADAPTER = TILE_ENTITY_TYPES.register(BfrBlocks.FISSION_REACTOR_LOGIC_ADAPTER, TileEntityFissionReactorLogicAdapter::new);
    public static final TileEntityTypeRegistryObject<TileEntityFissionFuelAssembly> FISSION_FUEL_ASSEMBLY = TILE_ENTITY_TYPES.register(BfrBlocks.FISSION_FUEL_ASSEMBLY, TileEntityFissionFuelAssembly::new);
    public static final TileEntityTypeRegistryObject<TileEntityControlRodAssembly> CONTROL_ROD_ASSEMBLY = TILE_ENTITY_TYPES.register(BfrBlocks.CONTROL_ROD_ASSEMBLY, TileEntityControlRodAssembly::new);
    //Fusion Reactor
    public static final TileEntityTypeRegistryObject<TileEntityFusionReactorController> FUSION_REACTOR_CONTROLLER = TILE_ENTITY_TYPES.register(BfrBlocks.FUSION_REACTOR_CONTROLLER, TileEntityFusionReactorController::new);
    public static final TileEntityTypeRegistryObject<TileEntityFusionReactorBlock> FUSION_REACTOR_FRAME = TILE_ENTITY_TYPES.register(BfrBlocks.FUSION_REACTOR_FRAME, TileEntityFusionReactorBlock::new);
    public static final TileEntityTypeRegistryObject<TileEntityLaserFocusMatrix> LASER_FOCUS_MATRIX = TILE_ENTITY_TYPES.register(BfrBlocks.LASER_FOCUS_MATRIX, TileEntityLaserFocusMatrix::new);
    public static final TileEntityTypeRegistryObject<TileEntityFusionReactorLogicAdapter> FUSION_REACTOR_LOGIC_ADAPTER = TILE_ENTITY_TYPES.register(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, TileEntityFusionReactorLogicAdapter::new);
    public static final TileEntityTypeRegistryObject<TileEntityFusionReactorPort> FUSION_REACTOR_PORT = TILE_ENTITY_TYPES.register(BfrBlocks.FUSION_REACTOR_PORT, TileEntityFusionReactorPort::new);
    //Turbine
    public static final TileEntityTypeRegistryObject<TileEntityElectromagneticCoil> ELECTROMAGNETIC_COIL = TILE_ENTITY_TYPES.register(BfrBlocks.ELECTROMAGNETIC_COIL, TileEntityElectromagneticCoil::new);
    public static final TileEntityTypeRegistryObject<TileEntityRotationalComplex> ROTATIONAL_COMPLEX = TILE_ENTITY_TYPES.register(BfrBlocks.ROTATIONAL_COMPLEX, TileEntityRotationalComplex::new);
    public static final TileEntityTypeRegistryObject<TileEntitySaturatingCondenser> SATURATING_CONDENSER = TILE_ENTITY_TYPES.register(BfrBlocks.SATURATING_CONDENSER, TileEntitySaturatingCondenser::new);
    public static final TileEntityTypeRegistryObject<TileEntityTurbineCasing> TURBINE_CASING = TILE_ENTITY_TYPES.register(BfrBlocks.TURBINE_CASING, TileEntityTurbineCasing::new);
    public static final TileEntityTypeRegistryObject<TileEntityTurbineRotor> TURBINE_ROTOR = TILE_ENTITY_TYPES.register(BfrBlocks.TURBINE_ROTOR, TileEntityTurbineRotor::new);
    public static final TileEntityTypeRegistryObject<TileEntityTurbineValve> TURBINE_VALVE = TILE_ENTITY_TYPES.register(BfrBlocks.TURBINE_VALVE, TileEntityTurbineValve::new);
    public static final TileEntityTypeRegistryObject<TileEntityTurbineVent> TURBINE_VENT = TILE_ENTITY_TYPES.register(BfrBlocks.TURBINE_VENT, TileEntityTurbineVent::new);
}