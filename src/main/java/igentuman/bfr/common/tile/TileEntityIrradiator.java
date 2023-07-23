package igentuman.bfr.common.tile;

import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.recipe.impl.IrradiatingIRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrRecipes;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import mekanism.api.NBTConstants;
import mekanism.api.RelativeSide;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.interfaces.ISustainedData;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import mekanism.generators.common.content.fission.FissionReactorMultiblockData;
import mekanism.generators.common.tile.fission.TileEntityFissionReactorPort;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityIrradiator extends TileEntityMachine {
    private final boolean supportsUpgrades = false;
    protected double radiativeFlux = 0;
    public boolean hasRadiationSource = false;

    //update source each 20 ticks
    protected int updateSourceTimer = 20;
    public BlockEntity radiationSource;

    public TileEntityIrradiator(BlockPos pos, BlockState state) {
        super(BfrBlocks.IRRADIATOR, pos, state, 200);
        configComponent.addDisabledSides(RelativeSide.BACK);
    }

    @Override
    protected void onUpdateServer() {
        updateRadiativeFlux();
        if(radiativeFlux > 0) {
            recipeCacheLookupMonitor.updateAndProcess();
        }

        if (ejectorComponent != null) {
            ejectorComponent.tickServer();
        }
    }

    @Override
    protected void addGeneralPersistentData(CompoundTag data) {
        super.addGeneralPersistentData(data);
        data.putDouble("radiativeFlux", radiativeFlux);
        data.putBoolean("hasRadiationSource", hasRadiationSource);
    }

    @Override
    protected void loadGeneralPersistentData(CompoundTag data) {
        super.loadGeneralPersistentData(data);
        radiativeFlux = data.getDouble("radiativeFlux");
        hasRadiationSource = data.getBoolean("hasRadiationSource");
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> getRecipeType() {
        return BfrRecipes.IRRADIATING;
    }

    private double getRadiationFromFissionReactor()
    {
        TileEntityFissionReactorPort port = (TileEntityFissionReactorPort)radiationSource;
        FissionReactorMultiblockData reactor = port.getMultiblock();
        if(reactor == null) return 0;
        if(!reactor.isBurning()) return  0;
        double burnRateRelation = reactor.getMaxBurnRate()/reactor.lastBurnRate*10;

        return burnRateRelation;
    }

    private double getRadiationFromFusionReactor()
    {
        TileEntityFusionReactorPort port = (TileEntityFusionReactorPort)radiationSource;
        FusionReactorMultiblockData reactor = port.getMultiblock();
        if(reactor == null) return 0;
        if(!reactor.isBurning()) return  0;
        double temperatureRelation = reactor.getTotalTemperature()/reactor.plasmaTemperature*10;

        return temperatureRelation;
    }


    public void updateRadiativeFlux()
    {
        updateSourceTimer--;
        if(updateSourceTimer <= 0) {
            updateSourceTimer = 20;
            BlockEntity be = level.getBlockEntity(worldPosition.relative(getDirection().getOpposite(), 1));
            if(be instanceof TileEntityFusionReactorPort || be instanceof TileEntityFissionReactorPort) {
                radiationSource = be;
            } else {
                radiationSource = null;
            }
        }
        double fluxBefore = radiativeFlux;
        if(radiationSource != null) {
            if(radiationSource instanceof TileEntityFissionReactorPort) {
                setRadiativeFlux((double) Math.round((radiativeFlux + getRadiationFromFissionReactor()) / 0.02) /100);
                hasRadiationSource = true;
            } else if(radiationSource instanceof TileEntityFusionReactorPort) {
                setRadiativeFlux((double) Math.round((radiativeFlux + getRadiationFromFusionReactor()) / 0.02) /100);
                hasRadiationSource = true;
            } else {
                setRadiativeFlux(0);
            }
        } else {
            setRadiativeFlux(0);
            hasRadiationSource = false;
        }
        if(fluxBefore != getRadiativeFlux()) {
            markForSave();
        }
    }


    public double getRadiativeFlux()
    {
        return radiativeFlux;
    }

    public void setRadiativeFlux(double value)
    {
        radiativeFlux = value;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag) {
        super.handleUpdateTag(tag);
        radiativeFlux = tag.getDouble("radiativeFlux");
        hasRadiationSource = tag.getBoolean("hasRadiationSource");
    }

    @NotNull
    @Override
    public CompoundTag getReducedUpdateTag() {
        CompoundTag updateTag = super.getReducedUpdateTag();

        updateTag.putDouble("radiativeFlux", radiativeFlux);
        updateTag.putBoolean("hasRadiationSource", hasRadiationSource);
        return updateTag;
    }

    @NotNull
    @Override
    public CachedRecipe<ItemStackToItemStackRecipe> createNewCachedRecipe(@NotNull ItemStackToItemStackRecipe recipe, int cacheIndex) {
        ticksRequired = ((IrradiatingIRecipe)recipe).getTicks();
        return OneInputCachedRecipe.itemToItem(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                .setActive(this::setActive)
                .setEnergyRequirements(() -> FloatingLong.create(0), getEnergyContainer())
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }
}