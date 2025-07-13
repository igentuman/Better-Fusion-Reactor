package igentuman.bfr.common.tile.fusion;

import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.dynamic.SyncMapper;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrContainerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityFusionReactorBlock extends TileEntityMultiblock<BFReactorMultiblockData> {

    public TileEntityFusionReactorBlock(BlockPos pos, BlockState state) {
        this(BfrBlocks.FUSION_REACTOR_FRAME, pos, state);
    }

    public TileEntityFusionReactorBlock(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public BFReactorMultiblockData createMultiblock() {
        return new BFReactorMultiblockData(this);
    }

    @Override
    public MultiblockManager<BFReactorMultiblockData> getManager() {
        return BetterFusionReactor.fusionReactorManager;
    }

    @Override
    public boolean canBeMaster() {
        return false;
    }

    public void setInjectionRateFromPacket(int rate) {
        BFReactorMultiblockData multiblock = getMultiblock();
        if (multiblock.isFormed()) {
            multiblock.setInjectionRate(Mth.clamp(rate - (rate % 2), 0, BFReactorMultiblockData.MAX_INJECTION));
            markForSave();
        }
    }

    public void adjustReactivity(int rate) {
        BFReactorMultiblockData multiblock = getMultiblock();
        if (multiblock.isFormed()) {
            multiblock.adjustReactivity(rate);
            markForSave();
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        if (container.getType() == BfrContainerTypes.FUSION_REACTOR_FUEL.get()) {
            addTabContainerTracker(container, BFReactorMultiblockData.FUEL_TAB);
        } else if (container.getType() == BfrContainerTypes.FUSION_REACTOR_HEAT.get()) {
            addTabContainerTracker(container, BFReactorMultiblockData.HEAT_TAB);
        } else if (container.getType() == BfrContainerTypes.FUSION_REACTOR_STATS.get()) {
            addTabContainerTracker(container, BFReactorMultiblockData.STATS_TAB);
        }
    }

    private void addTabContainerTracker(MekanismContainer container, String tab) {
        SyncMapper.INSTANCE.setup(container, BFReactorMultiblockData.class, this::getMultiblock, tab);
    }
}