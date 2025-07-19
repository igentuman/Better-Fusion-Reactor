package igentuman.bfr.common.block;

import igentuman.bfr.common.tile.TileRedstoneCapacitor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import static igentuman.bfr.common.registries.BfrTileEntityTypes.REDSTONE_CAPACITOR_TILE;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class RedstoneCapacitorBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public RedstoneCapacitorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return REDSTONE_CAPACITOR_TILE.get().create(blockPos, blockState);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return direction == state.getValue(HORIZONTAL_FACING) || direction == state.getValue(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        BlockEntity be = world.getExistingBlockEntity(pos);
        if (direction == state.getValue(HORIZONTAL_FACING).getOpposite() && be instanceof TileRedstoneCapacitor) {
            return ((TileRedstoneCapacitor) be).redstoneLevel;
        }
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return getSignal(state, world, pos, direction);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (level.isClientSide()) {
            return (lvl, pos, blockState, t) -> {
                if (t instanceof TileRedstoneCapacitor tile) {
                    tile.tickClient();
                }
            };
        }
        return (lvl, pos, blockState, t)-> {
            if (t instanceof TileRedstoneCapacitor tile) {
                tile.tickServer();
            }
        };
    }
}
