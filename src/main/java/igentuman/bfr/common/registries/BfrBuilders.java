package igentuman.bfr.common.registries;

import mekanism.common.command.builders.StructureBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BfrBuilders {

    private BfrBuilders() {
    }

    public static class FusionReactorBuilder extends StructureBuilder {

        public FusionReactorBuilder() {
            super(5, 5, 5);
        }

        @Override
        protected void build(Level world, BlockPos start, boolean empty) {
            buildPartialFrame(world, start, 1);
            buildWalls(world, start);
            buildInteriorLayers(world, start, 1, 3, Blocks.AIR.defaultBlockState());
            world.setBlockAndUpdate(start.offset(2, 4, 2), BfrBlocks.FUSION_REACTOR_CONTROLLER.defaultState());
        }

        @Override
        protected BlockState getWallBlock(BlockPos pos) {
            return BfrBlocks.FUSION_REACTOR_FRAME.defaultState();
        }

        @Override
        protected BlockState getCasing() {
            return BfrBlocks.FUSION_REACTOR_FRAME.defaultState();
        }
    }
}
