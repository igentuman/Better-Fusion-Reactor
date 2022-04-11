package igentuman.bfr.common.tile;

import mekanism.api.providers.IBlockProvider;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityStructuralMultiblock;
import igentuman.bfr.common.registries.BfrBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityReactorGlass extends TileEntityStructuralMultiblock {

    public TileEntityReactorGlass(BlockPos pos, BlockState state) {
        super(BfrBlocks.REACTOR_GLASS, pos, state);
    }

    public TileEntityReactorGlass(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }
    @Override
    public boolean canInterface(MultiblockManager<?> manager) {
        return true;
    }

}
