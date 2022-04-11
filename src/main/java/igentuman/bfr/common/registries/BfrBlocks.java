package igentuman.bfr.common.registries;

import java.util.function.Supplier;
import mekanism.common.block.basic.BlockStructuralGlass;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.block.fusion.BlockLaserFocusMatrix;
import igentuman.bfr.common.tile.TileEntityReactorGlass;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import net.minecraft.world.level.block.Block;


public class BfrBlocks {

    private BfrBlocks() {
    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(BetterFusionReactor.MODID);
    public static final BlockRegistryObject<BlockStructuralGlass<TileEntityReactorGlass>, ItemBlockTooltip<BlockStructuralGlass<TileEntityReactorGlass>>> REACTOR_GLASS = registerTooltipBlock("reactor_glass", () -> new BlockStructuralGlass<>(BfrBlockTypes.REACTOR_GLASS));

    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorController>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorController>>> FUSION_REACTOR_CONTROLLER = registerTooltipBlock("fusion_reactor_controller", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_CONTROLLER));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorBlock>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorBlock>>> FUSION_REACTOR_FRAME = registerTooltipBlock("fusion_reactor_frame", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_FRAME));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorPort>>> FUSION_REACTOR_PORT = registerTooltipBlock("fusion_reactor_port", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_PORT));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>>> FUSION_REACTOR_LOGIC_ADAPTER = registerTooltipBlock("fusion_reactor_logic_adapter", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_LOGIC_ADAPTER));
    public static final BlockRegistryObject<BlockLaserFocusMatrix, ItemBlockTooltip<BlockLaserFocusMatrix>> LASER_FOCUS_MATRIX = registerTooltipBlock("laser_focus_matrix", BlockLaserFocusMatrix::new);

    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.registerDefaultProperties(name, blockCreator, ItemBlockTooltip::new);
    }
}