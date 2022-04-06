package igentuman.bfr.common.registries;

import java.util.EnumSet;
import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.AttributeCustomSelectionBox;
import mekanism.common.block.attribute.AttributeParticleFX;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.block.attribute.Attributes.AttributeMobSpawn;
import mekanism.common.block.attribute.Attributes.AttributeRedstoneEmitter;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.BlockTypeTile.BlockTileBuilder;
import mekanism.common.lib.math.Pos3D;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.block.attribute.AttributeStateFissionPortMode;
import igentuman.bfr.common.content.blocktype.BlockShapes;
import igentuman.bfr.common.content.blocktype.Generator;
import igentuman.bfr.common.content.blocktype.Generator.GeneratorBuilder;
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
import igentuman.bfr.common.tile.fission.TileEntityFissionReactorLogicAdapter.RedstoneStatus;
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
import net.minecraft.particles.ParticleTypes;

public class BfrBlockTypes {

    private BfrBlockTypes() {
    }

    //TODO: Do this in a cleaner way
    private static final FloatingLong STORAGE = FloatingLong.createConst(160_000);
    private static final FloatingLong STORAGE2 = FloatingLong.createConst(200_000);
    private static final FloatingLong SOLAR_STORAGE = FloatingLong.createConst(96_000);

    // Heat Generator
    public static final Generator<TileEntityHeatGenerator> HEAT_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.HEAT_GENERATOR, BfrLang.DESCRIPTION_HEAT_GENERATOR)
          .withGui(() -> BfrContainerTypes.HEAT_GENERATOR)
          .withEnergyConfig(() -> STORAGE)
          .withCustomShape(BlockShapes.HEAT_GENERATOR)
          .withSound(GeneratorsSounds.HEAT_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("heatGenerator")
          .with(new AttributeParticleFX()
                .add(ParticleTypes.SMOKE, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, -0.52))
                .add(ParticleTypes.FLAME, rand -> new Pos3D(rand.nextFloat() * 0.6F - 0.3F, rand.nextFloat() * 6.0F / 16.0F, -0.52)))
          .build();
    // Bio Generator
    public static final Generator<TileEntityBioGenerator> BIO_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.BIO_GENERATOR, BfrLang.DESCRIPTION_BIO_GENERATOR)
          .withGui(() -> BfrContainerTypes.BIO_GENERATOR)
          .withEnergyConfig(() -> STORAGE)
          .withCustomShape(BlockShapes.BIO_GENERATOR)
          .withSound(GeneratorsSounds.BIO_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("bioGenerator")
          .with(new AttributeParticleFX()
                .add(ParticleTypes.SMOKE, rand -> new Pos3D(0, 0.3, -0.25)))
          .build();
    // Solar Generator
    public static final Generator<TileEntitySolarGenerator> SOLAR_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.SOLAR_GENERATOR, BfrLang.DESCRIPTION_SOLAR_GENERATOR)
          .withGui(() -> BfrContainerTypes.SOLAR_GENERATOR)
          .withEnergyConfig(() -> SOLAR_STORAGE)
          .withCustomShape(BlockShapes.SOLAR_GENERATOR)
          .withSound(GeneratorsSounds.SOLAR_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("solarGenerator")
          .replace(Attributes.ACTIVE)
          .build();
    // Wind Generator
    public static final Generator<TileEntityWindGenerator> WIND_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.WIND_GENERATOR, BfrLang.DESCRIPTION_WIND_GENERATOR)
          .withGui(() -> BfrContainerTypes.WIND_GENERATOR)
          .withEnergyConfig(() -> STORAGE2)
          .withCustomShape(BlockShapes.WIND_GENERATOR)
          .with(AttributeCustomSelectionBox.JAVA)
          .withSound(GeneratorsSounds.WIND_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("windGenerator")
          .build();
    // Gas Burning Generator
    public static final Generator<TileEntityGasGenerator> GAS_BURNING_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.GAS_BURNING_GENERATOR, BfrLang.DESCRIPTION_GAS_BURNING_GENERATOR)
          .withGui(() -> BfrContainerTypes.GAS_BURNING_GENERATOR)
          .withEnergyConfig(() -> MekanismConfig.general.FROM_H2.get().multiply(1_000))
          .withCustomShape(BlockShapes.GAS_BURNING_GENERATOR)
          .with(AttributeCustomSelectionBox.JSON)
          .withSound(GeneratorsSounds.GAS_BURNING_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("gasBurningGenerator")
          .build();
    // Advanced Solar Generator
    public static final Generator<TileEntityAdvancedSolarGenerator> ADVANCED_SOLAR_GENERATOR = GeneratorBuilder
          .createGenerator(() -> BfrTileEntityTypes.ADVANCED_SOLAR_GENERATOR, BfrLang.DESCRIPTION_ADVANCED_SOLAR_GENERATOR)
          .withGui(() -> BfrContainerTypes.ADVANCED_SOLAR_GENERATOR)
          .withEnergyConfig(() -> STORAGE2)
          .withCustomShape(BlockShapes.ADVANCED_SOLAR_GENERATOR)
          .withSound(GeneratorsSounds.SOLAR_GENERATOR)
          .withSupportedUpgrades(EnumSet.of(Upgrade.MUFFLING))
          .withComputerSupport("advancedSolarGenerator")
          .replace(Attributes.ACTIVE)
          .build();

    // Turbine Casing
    public static final BlockTypeTile<TileEntityTurbineCasing> TURBINE_CASING = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.TURBINE_CASING, BfrLang.DESCRIPTION_TURBINE_CASING)
          .withGui(() -> BfrContainerTypes.INDUSTRIAL_TURBINE)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .build();
    // Turbine Valve
    public static final BlockTypeTile<TileEntityTurbineValve> TURBINE_VALVE = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.TURBINE_VALVE, BfrLang.DESCRIPTION_TURBINE_VALVE)
          .withGui(() -> BfrContainerTypes.INDUSTRIAL_TURBINE)
          .with(Attributes.COMPARATOR, Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .withComputerSupport("turbineValve")
          .build();
    // Turbine Vent
    public static final BlockTypeTile<TileEntityTurbineVent> TURBINE_VENT = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.TURBINE_VENT, BfrLang.DESCRIPTION_TURBINE_VENT)
          .withGui(() -> BfrContainerTypes.INDUSTRIAL_TURBINE)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .build();
    // Electromagnetic Coil
    public static final BlockTypeTile<TileEntityElectromagneticCoil> ELECTROMAGNETIC_COIL = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.ELECTROMAGNETIC_COIL, BfrLang.DESCRIPTION_ELECTROMAGNETIC_COIL)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();
    // Rotational Complex
    public static final BlockTypeTile<TileEntityRotationalComplex> ROTATIONAL_COMPLEX = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.ROTATIONAL_COMPLEX, BfrLang.DESCRIPTION_ROTATIONAL_COMPLEX)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();
    // Saturating Condenser
    public static final BlockTypeTile<TileEntitySaturatingCondenser> SATURATING_CONDENSER = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.SATURATING_CONDENSER, BfrLang.DESCRIPTION_SATURATING_CONDENSER)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();
    // Turbine Rotor
    public static final BlockTypeTile<TileEntityTurbineRotor> TURBINE_ROTOR = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.TURBINE_ROTOR, BfrLang.DESCRIPTION_TURBINE_ROTOR)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();

    // Fission Reactor Casing
    public static final BlockTypeTile<TileEntityFissionReactorCasing> FISSION_REACTOR_CASING = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FISSION_REACTOR_CASING, BfrLang.DESCRIPTION_FISSION_REACTOR_CASING)
          .withGui(() -> BfrContainerTypes.FISSION_REACTOR)
          .withSound(GeneratorsSounds.FISSION_REACTOR)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .build();
    // Fission Reactor Port
    public static final BlockTypeTile<TileEntityFissionReactorPort> FISSION_REACTOR_PORT = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FISSION_REACTOR_PORT, BfrLang.DESCRIPTION_FISSION_REACTOR_PORT)
          .with(new AttributeStateFissionPortMode(), Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .withGui(() -> BfrContainerTypes.FISSION_REACTOR)
          .withSound(GeneratorsSounds.FISSION_REACTOR)
          .withComputerSupport("fissionReactorPort")
          .build();
    // Fission Reactor Logic Adapter
    public static final BlockTypeTile<TileEntityFissionReactorLogicAdapter> FISSION_REACTOR_LOGIC_ADAPTER = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FISSION_REACTOR_LOGIC_ADAPTER, BfrLang.DESCRIPTION_FISSION_REACTOR_LOGIC_ADAPTER)
          .with(new AttributeRedstoneEmitter<>(tile -> tile.getStatus() == RedstoneStatus.OUTPUTTING ? 15 : 0))
          .with(Attributes.REDSTONE, Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .withGui(() -> BfrContainerTypes.FISSION_REACTOR_LOGIC_ADAPTER)
          .withSound(GeneratorsSounds.FISSION_REACTOR)
          .withComputerSupport("fissionReactorLogicAdapter")
          .build();
    // Fission Fuel Assembly
    public static final BlockTypeTile<TileEntityFissionFuelAssembly> FISSION_FUEL_ASSEMBLY = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FISSION_FUEL_ASSEMBLY, BfrLang.DESCRIPTION_FISSION_FUEL_ASSEMBLY)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .withCustomShape(BlockShapes.FUEL_ASSEMBLY)
          .build();
    // Control Rod Assembly
    public static final BlockTypeTile<TileEntityControlRodAssembly> CONTROL_ROD_ASSEMBLY = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.CONTROL_ROD_ASSEMBLY, BfrLang.DESCRIPTION_CONTROL_ROD_ASSEMBLY)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .withCustomShape(BlockShapes.CONTROL_ROD_ASSEMBLY)
          .build();

    // Fusion Reactor Controller
    public static final BlockTypeTile<TileEntityFusionReactorController> FUSION_REACTOR_CONTROLLER = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FUSION_REACTOR_CONTROLLER, BfrLang.DESCRIPTION_FUSION_REACTOR_CONTROLLER)
          .withGui(() -> BfrContainerTypes.FUSION_REACTOR_CONTROLLER)
          .withSound(GeneratorsSounds.FUSION_REACTOR)
          .with(Attributes.ACTIVE, Attributes.INVENTORY, Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .build();
    // Fusion Reactor Port
    public static final BlockTypeTile<TileEntityFusionReactorPort> FUSION_REACTOR_PORT = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FUSION_REACTOR_PORT, BfrLang.DESCRIPTION_FUSION_REACTOR_PORT)
          .with(Attributes.ACTIVE, Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .withComputerSupport("fusionReactorPort")
          .build();
    // Fusion Reactor Frame
    public static final BlockTypeTile<TileEntityFusionReactorBlock> FUSION_REACTOR_FRAME = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FUSION_REACTOR_FRAME, BfrLang.DESCRIPTION_FUSION_REACTOR_FRAME)
          .withEnergyConfig(null, null)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .build();
    // Fusion Reactor Logic Adapter
    public static final BlockTypeTile<TileEntityFusionReactorLogicAdapter> FUSION_REACTOR_LOGIC_ADAPTER = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.FUSION_REACTOR_LOGIC_ADAPTER, BfrLang.DESCRIPTION_FUSION_REACTOR_LOGIC_ADAPTER)
          .withGui(() -> BfrContainerTypes.FUSION_REACTOR_LOGIC_ADAPTER)
          .with(new AttributeRedstoneEmitter<>(tile -> tile.checkMode() ? 15 : 0))
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.WHEN_NOT_FORMED)
          .withComputerSupport("fusionReactorLogicAdapter")
          .build();
    // Laser Focus Matrix
    public static final BlockTypeTile<TileEntityLaserFocusMatrix> LASER_FOCUS_MATRIX = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.LASER_FOCUS_MATRIX, BfrLang.DESCRIPTION_LASER_FOCUS_MATRIX)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();
    // Reactor Glass
    public static final BlockTypeTile<TileEntityReactorGlass> REACTOR_GLASS = BlockTileBuilder
          .createBlock(() -> BfrTileEntityTypes.REACTOR_GLASS, BfrLang.DESCRIPTION_REACTOR_GLASS)
          .with(Attributes.MULTIBLOCK, AttributeMobSpawn.NEVER)
          .build();
}
