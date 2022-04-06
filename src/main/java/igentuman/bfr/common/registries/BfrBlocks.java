package igentuman.bfr.common.registries;

import java.util.function.Supplier;
import mekanism.common.block.basic.BlockStructuralGlass;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.item.block.machine.ItemBlockMachine;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.block.fission.BlockFissionCasing;
import igentuman.bfr.common.block.fusion.BlockLaserFocusMatrix;
import igentuman.bfr.common.block.turbine.BlockTurbineRotor;
import igentuman.bfr.common.content.blocktype.Generator;
import igentuman.bfr.common.item.generator.ItemBlockAdvancedSolarGenerator;
import igentuman.bfr.common.item.generator.ItemBlockWindGenerator;
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
import igentuman.bfr.common.tile.turbine.TileEntityElectromagneticCoil;
import igentuman.bfr.common.tile.turbine.TileEntityRotationalComplex;
import igentuman.bfr.common.tile.turbine.TileEntitySaturatingCondenser;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineCasing;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineValve;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineVent;
import net.minecraft.block.Block;

public class BfrBlocks {

    private BfrBlocks() {
    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(BetterFusionReactor.MODID);

    public static final BlockRegistryObject<BlockTileModel<TileEntityHeatGenerator, Generator<TileEntityHeatGenerator>>, ItemBlockMachine> HEAT_GENERATOR = BLOCKS.register("heat_generator", () -> new BlockTileModel<>(BfrBlockTypes.HEAT_GENERATOR), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntitySolarGenerator, Generator<TileEntitySolarGenerator>>, ItemBlockMachine> SOLAR_GENERATOR = BLOCKS.register("solar_generator", () -> new BlockTileModel<>(BfrBlockTypes.SOLAR_GENERATOR), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityGasGenerator, Generator<TileEntityGasGenerator>>, ItemBlockMachine> GAS_BURNING_GENERATOR = BLOCKS.register("gas_burning_generator", () -> new BlockTileModel<>(BfrBlockTypes.GAS_BURNING_GENERATOR), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityBioGenerator, Generator<TileEntityBioGenerator>>, ItemBlockMachine> BIO_GENERATOR = BLOCKS.register("bio_generator", () -> new BlockTileModel<>(BfrBlockTypes.BIO_GENERATOR), ItemBlockMachine::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityAdvancedSolarGenerator, Generator<TileEntityAdvancedSolarGenerator>>, ItemBlockAdvancedSolarGenerator> ADVANCED_SOLAR_GENERATOR = BLOCKS.register("advanced_solar_generator", () -> new BlockTileModel<>(BfrBlockTypes.ADVANCED_SOLAR_GENERATOR), ItemBlockAdvancedSolarGenerator::new);
    public static final BlockRegistryObject<BlockTileModel<TileEntityWindGenerator, Generator<TileEntityWindGenerator>>, ItemBlockWindGenerator> WIND_GENERATOR = BLOCKS.register("wind_generator", () -> new BlockTileModel<>(BfrBlockTypes.WIND_GENERATOR), ItemBlockWindGenerator::new);

    public static final BlockRegistryObject<BlockTurbineRotor, ItemBlockTooltip<BlockTurbineRotor>> TURBINE_ROTOR = registerTooltipBlock("turbine_rotor", BlockTurbineRotor::new);
    public static final BlockRegistryObject<BlockTile<TileEntityRotationalComplex, BlockTypeTile<TileEntityRotationalComplex>>, ItemBlockTooltip<BlockTile<TileEntityRotationalComplex, BlockTypeTile<TileEntityRotationalComplex>>>> ROTATIONAL_COMPLEX = registerTooltipBlock("rotational_complex", () -> new BlockTile<>(BfrBlockTypes.ROTATIONAL_COMPLEX));
    public static final BlockRegistryObject<BlockTile<TileEntityElectromagneticCoil, BlockTypeTile<TileEntityElectromagneticCoil>>, ItemBlockTooltip<BlockTile<TileEntityElectromagneticCoil, BlockTypeTile<TileEntityElectromagneticCoil>>>> ELECTROMAGNETIC_COIL = registerTooltipBlock("electromagnetic_coil", () -> new BlockTile<>(BfrBlockTypes.ELECTROMAGNETIC_COIL));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityTurbineCasing>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityTurbineCasing>>> TURBINE_CASING = registerTooltipBlock("turbine_casing", () -> new BlockBasicMultiblock<>(BfrBlockTypes.TURBINE_CASING));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityTurbineValve>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityTurbineValve>>> TURBINE_VALVE = registerTooltipBlock("turbine_valve", () -> new BlockBasicMultiblock<>(BfrBlockTypes.TURBINE_VALVE));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityTurbineVent>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityTurbineVent>>> TURBINE_VENT = registerTooltipBlock("turbine_vent", () -> new BlockBasicMultiblock<>(BfrBlockTypes.TURBINE_VENT));
    public static final BlockRegistryObject<BlockTile<TileEntitySaturatingCondenser, BlockTypeTile<TileEntitySaturatingCondenser>>, ItemBlockTooltip<BlockTile<TileEntitySaturatingCondenser, BlockTypeTile<TileEntitySaturatingCondenser>>>> SATURATING_CONDENSER = registerTooltipBlock("saturating_condenser", () -> new BlockTile<>(BfrBlockTypes.SATURATING_CONDENSER));

    public static final BlockRegistryObject<BlockStructuralGlass<TileEntityReactorGlass>, ItemBlockTooltip<BlockStructuralGlass<TileEntityReactorGlass>>> REACTOR_GLASS = registerTooltipBlock("reactor_glass", () -> new BlockStructuralGlass<>(BfrBlockTypes.REACTOR_GLASS));

    public static final BlockRegistryObject<BlockFissionCasing<TileEntityFissionReactorCasing>, ItemBlockTooltip<BlockFissionCasing<TileEntityFissionReactorCasing>>> FISSION_REACTOR_CASING = registerTooltipBlock("fission_reactor_casing", () -> new BlockFissionCasing<>(BfrBlockTypes.FISSION_REACTOR_CASING));
    public static final BlockRegistryObject<BlockFissionCasing<TileEntityFissionReactorPort>, ItemBlockTooltip<BlockFissionCasing<TileEntityFissionReactorPort>>> FISSION_REACTOR_PORT = registerTooltipBlock("fission_reactor_port", () -> new BlockFissionCasing<>(BfrBlockTypes.FISSION_REACTOR_PORT));
    public static final BlockRegistryObject<BlockFissionCasing<TileEntityFissionReactorLogicAdapter>, ItemBlockTooltip<BlockFissionCasing<TileEntityFissionReactorLogicAdapter>>> FISSION_REACTOR_LOGIC_ADAPTER = registerTooltipBlock("fission_reactor_logic_adapter", () -> new BlockFissionCasing<>(BfrBlockTypes.FISSION_REACTOR_LOGIC_ADAPTER));
    public static final BlockRegistryObject<BlockTileModel<TileEntityFissionFuelAssembly, BlockTypeTile<TileEntityFissionFuelAssembly>>, ItemBlockTooltip<BlockTileModel<TileEntityFissionFuelAssembly, BlockTypeTile<TileEntityFissionFuelAssembly>>>> FISSION_FUEL_ASSEMBLY = registerTooltipBlock("fission_fuel_assembly", () -> new BlockTileModel<>(BfrBlockTypes.FISSION_FUEL_ASSEMBLY));
    public static final BlockRegistryObject<BlockTileModel<TileEntityControlRodAssembly, BlockTypeTile<TileEntityControlRodAssembly>>, ItemBlockTooltip<BlockTileModel<TileEntityControlRodAssembly, BlockTypeTile<TileEntityControlRodAssembly>>>> CONTROL_ROD_ASSEMBLY = registerTooltipBlock("control_rod_assembly", () -> new BlockTileModel<>(BfrBlockTypes.CONTROL_ROD_ASSEMBLY));

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorController>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorController>>> FUSION_REACTOR_CONTROLLER = registerTooltipBlock("fusion_reactor_controller", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_CONTROLLER));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorBlock>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorBlock>>> FUSION_REACTOR_FRAME = registerTooltipBlock("fusion_reactor_frame", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_FRAME));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorPort>>> FUSION_REACTOR_PORT = registerTooltipBlock("fusion_reactor_port", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_PORT));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>>> FUSION_REACTOR_LOGIC_ADAPTER = registerTooltipBlock("fusion_reactor_logic_adapter", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_LOGIC_ADAPTER));
    public static final BlockRegistryObject<BlockLaserFocusMatrix, ItemBlockTooltip<BlockLaserFocusMatrix>> LASER_FOCUS_MATRIX = registerTooltipBlock("laser_focus_matrix", BlockLaserFocusMatrix::new);

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}