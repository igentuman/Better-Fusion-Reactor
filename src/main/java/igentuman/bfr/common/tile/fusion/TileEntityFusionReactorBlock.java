package igentuman.bfr.common.tile.fusion;


import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.dynamic.SyncMapper;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.tile.prefab.TileEntityMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityFusionReactorBlock extends TileEntityMultiblock<FusionReactorMultiblockData> {

    public TileEntityFusionReactorBlock(BlockPos pos, BlockState state) {
        this(BfrBlocks.FUSION_REACTOR_FRAME, pos, state);
    }

    public TileEntityFusionReactorBlock(IBlockProvider blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
    }

    @Override
    public FusionReactorMultiblockData createMultiblock() {
        return new FusionReactorMultiblockData(this);
    }

    @Override
    public MultiblockManager<FusionReactorMultiblockData> getManager() {
        return BetterFusionReactor.fusionReactorManager;
    }

    @Override
    public boolean canBeMaster() {
        return false;
    }

    public void setInjectionRateFromPacket(int rate) {
        FusionReactorMultiblockData multiblock = getMultiblock();
        if (multiblock.isFormed()) {
            multiblock.setInjectionRate(Math.min(FusionReactorMultiblockData.MAX_INJECTION, Math.max(0, rate - (rate % 2))));
            markForSave();
        }
    }

    public void adjustReactivityFromPacket(float val) {
        FusionReactorMultiblockData multiblock = getMultiblock();
        if (multiblock.isFormed() && multiblock.isBurning()) {
            multiblock.adjustReactivity(val);
            markForSave();
        }
    }

    public void addFuelTabContainerTrackers(MekanismContainer container) {
        SyncMapper.INSTANCE.setup(container, FusionReactorMultiblockData.class, this::getMultiblock, "fuel");
    }

    public void addHeatTabContainerTrackers(MekanismContainer container) {
        SyncMapper.INSTANCE.setup(container, FusionReactorMultiblockData.class, this::getMultiblock, "heat");
    }
}