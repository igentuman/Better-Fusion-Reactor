package igentuman.bfr.common.tile;

import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityStructuralMultiblock;
import igentuman.bfr.common.registries.BfrBlocks;

public class TileEntityReactorGlass extends TileEntityStructuralMultiblock {

    public TileEntityReactorGlass() {
        super(BfrBlocks.REACTOR_GLASS);
    }

    @Override
    public boolean canInterface(MultiblockManager<?> manager) {
        return true;
    }

}
