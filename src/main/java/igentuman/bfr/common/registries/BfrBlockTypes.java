package igentuman.bfr.common.registries;

import mekanism.api.math.FloatingLong;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.block.attribute.Attributes.AttributeMobSpawn;
import mekanism.common.block.attribute.Attributes.AttributeRedstoneEmitter;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.BlockTypeTile.BlockTileBuilder;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.tile.TileEntityReactorGlass;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import igentuman.bfr.common.tile.fusion.TileEntityLaserFocusMatrix;
import mekanism.generators.common.registries.GeneratorsSounds;

public class BfrBlockTypes {

    private BfrBlockTypes() {
    }

    //TODO: Do this in a cleaner way
    private static final FloatingLong STORAGE = FloatingLong.createConst(160_000);
    private static final FloatingLong STORAGE2 = FloatingLong.createConst(200_000);
    private static final FloatingLong SOLAR_STORAGE = FloatingLong.createConst(96_000);

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
          .with(new AttributeRedstoneEmitter<>(tile -> tile.getRedstoneLevel()))
           .with(Attributes.REDSTONE)
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
