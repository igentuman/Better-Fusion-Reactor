package igentuman.bfr.common.container;

import mekanism.common.inventory.container.tile.EmptyTileContainer;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import net.minecraft.entity.player.PlayerInventory;

public class FusionReactorHeatTabContainer extends EmptyTileContainer<TileEntityFusionReactorController> {

    public FusionReactorHeatTabContainer(int id, PlayerInventory inv, TileEntityFusionReactorController tile) {
        super(BfrContainerTypes.FUSION_REACTOR_HEAT, id, inv, tile);
    }

    @Override
    protected void addContainerTrackers() {
        super.addContainerTrackers();
        tile.addHeatTabContainerTrackers(this);
    }
}