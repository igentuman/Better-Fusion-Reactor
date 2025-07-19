package igentuman.bfr.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static igentuman.bfr.common.registries.BfrTileEntityTypes.REDSTONE_CAPACITOR_TILE;

public class TileRedstoneCapacitor extends BlockEntity {

    public int redstoneLevel = 0;

    public TileRedstoneCapacitor(BlockPos pPos, BlockState pBlockState) {
        super(REDSTONE_CAPACITOR_TILE.get(), pPos, pBlockState);
    }

    public void tickServer() {
        Direction facing = getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos backPos = getBlockPos().relative(facing.getOpposite());
        int level = getLevel().getDirectSignal(backPos, facing);
        if(level > 0) {
            redstoneLevel = level;
            setChanged();
            getLevel().setBlockAndUpdate(getBlockPos(), getBlockState());
            getLevel().updateNeighbourForOutputSignal(getBlockPos().relative(getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)), getBlockState().getBlock());
            getLevel().updateNeighbourForOutputSignal(getBlockPos().relative(getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite()), getBlockState().getBlock());
            getLevel().updateNeighbourForOutputSignal(getBlockPos(), getBlockState().getBlock());
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public void tickClient() {

    }
}
