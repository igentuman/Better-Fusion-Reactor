package igentuman.bfr.common.tile.fusion;

import javax.annotation.Nonnull;
import mekanism.api.lasers.ILaserReceptor;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.resolver.BasicCapabilityResolver;
import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.registries.GeneratorsBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;

public class TileEntityLaserFocusMatrix extends TileEntityFusionReactorBlock implements ILaserReceptor {

    public TileEntityLaserFocusMatrix() {
        super(GeneratorsBlocks.LASER_FOCUS_MATRIX);
        addCapabilityResolver(BasicCapabilityResolver.constant(Capabilities.LASER_RECEPTOR_CAPABILITY, this));
    }

    @Override
    public void receiveLaserEnergy(@Nonnull FloatingLong energy, Direction side) {
        FusionReactorMultiblockData multiblock = getMultiblock();
        if (multiblock.isFormed()) {
            multiblock.addTemperatureFromEnergyInput(energy);
        }
    }

    @Override
    public ActionResultType onRightClick(PlayerEntity player, Direction side) {
        if (!isRemote() && player.isCreative()) {
            FusionReactorMultiblockData multiblock = getMultiblock();
            if (multiblock.isFormed()) {
                multiblock.setPlasmaTemp(1_000_000_000);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onRightClick(player, side);
    }

    @Override
    public boolean canLasersDig() {
        return false;
    }
}