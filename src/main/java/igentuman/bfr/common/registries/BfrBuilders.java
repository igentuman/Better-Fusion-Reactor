package igentuman.bfr.common.registries;

import mekanism.common.command.builders.StructureBuilder;
import mekanism.common.registries.MekanismBlocks;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineRotor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BfrBuilders {

    private BfrBuilders() {
    }

    public static class TurbineBuilder extends StructureBuilder {

        public TurbineBuilder() {
            super(17, 18, 17);
        }

        @Override
        protected void build(World world, BlockPos start) {
            buildFrame(world, start);
            buildWalls(world, start);
            //Clear out the inside
            buildInteriorLayers(world, start, 1, 14, Blocks.AIR);
            //Add two blades to each rotor, they will be properly scanned when the multiblock forms at the end
            buildColumn(world, start, new BlockPos(sizeX / 2, 1, sizeZ / 2), 14, BfrBlocks.TURBINE_ROTOR.getBlock(), TileEntityTurbineRotor.class,
                  rotor -> rotor.blades = 2);
            buildInteriorLayer(world, start, 15, MekanismBlocks.PRESSURE_DISPERSER.getBlock());
            world.setBlockAndUpdate(start.offset(sizeX / 2, 15, sizeZ / 2), BfrBlocks.ROTATIONAL_COMPLEX.getBlock().defaultBlockState());
            buildInteriorLayer(world, start, 16, BfrBlocks.SATURATING_CONDENSER.getBlock());
            buildPlane(world, start, 5, 5, 13, 13, 16, BfrBlocks.ELECTROMAGNETIC_COIL.getBlock());
        }

        @Override
        protected Block getWallBlock(BlockPos pos) {
            return pos.getY() >= 15 ? BfrBlocks.TURBINE_VENT.getBlock() : super.getWallBlock(pos);
        }

        @Override
        protected Block getRoofBlock(BlockPos pos) {
            return BfrBlocks.TURBINE_VENT.getBlock();
        }

        @Override
        protected Block getCasing() {
            return BfrBlocks.TURBINE_CASING.getBlock();
        }
    }

    public static class FissionReactorBuilder extends StructureBuilder {

        public FissionReactorBuilder() {
            super(18, 18, 18);
        }

        @Override
        protected void build(World world, BlockPos start) {
            buildFrame(world, start);
            buildWalls(world, start);
            for (int x = 1; x < sizeX - 1; x++) {
                for (int z = 1; z < sizeZ - 1; z++) {
                    if (x % 2 == z % 2) {
                        buildColumn(world, start, new BlockPos(x, 1, z), 15, BfrBlocks.FISSION_FUEL_ASSEMBLY.getBlock());
                        world.setBlockAndUpdate(start.offset(x, sizeY - 2, z), BfrBlocks.CONTROL_ROD_ASSEMBLY.getBlock().defaultBlockState());
                    } else {
                        buildColumn(world, start, new BlockPos(x, 1, z), 16, Blocks.AIR);
                    }
                }
            }
        }

        @Override
        protected Block getWallBlock(BlockPos pos) {
            return BfrBlocks.REACTOR_GLASS.getBlock();
        }

        @Override
        protected Block getCasing() {
            return BfrBlocks.FISSION_REACTOR_CASING.getBlock();
        }
    }

    public static class FusionReactorBuilder extends StructureBuilder {

        public FusionReactorBuilder() {
            super(5, 5, 5);
        }

        @Override
        protected void build(World world, BlockPos start) {
            buildPartialFrame(world, start, 1);
            buildWalls(world, start);
            buildInteriorLayers(world, start, 1, 3, Blocks.AIR);
            world.setBlockAndUpdate(start.offset(2, 4, 2), BfrBlocks.FUSION_REACTOR_CONTROLLER.getBlock().defaultBlockState());
        }

        @Override
        protected Block getWallBlock(BlockPos pos) {
            return BfrBlocks.FUSION_REACTOR_FRAME.getBlock();
        }

        @Override
        protected Block getCasing() {
            return BfrBlocks.FUSION_REACTOR_FRAME.getBlock();
        }
    }
}
