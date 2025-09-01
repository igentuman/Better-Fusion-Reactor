package igentuman.bfr.common.content.fusion;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.HeatAPI.HeatTransfer;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.energy.VariableCapacityEnergyContainer;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.heat.ITileHeatHandler;
import mekanism.common.capabilities.heat.VariableHeatCapacitor;
import mekanism.common.integration.computer.ComputerException;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerChemicalTankWrapper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerFluidTankWrapper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.integration.computer.annotation.SyntheticComputerMethod;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.multiblock.IValveHandler.ValveData;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.prefab.TileEntityStructuralMultiblock;
import mekanism.common.util.CableUtils;
import mekanism.common.util.ChemicalUtil;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.BfrTags;
import igentuman.bfr.common.config.BetterFusionReactorConfig;
import igentuman.bfr.common.slot.ReactorInventorySlot;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.item.ItemHohlraum;
import mekanism.generators.common.registries.GeneratorsChemicals;
import mekanism.generators.common.registries.GeneratorsDamageTypes;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import static igentuman.bfr.common.events.GameEvents.REACTOR_HI_CR_VIBRATION;
import static igentuman.bfr.common.events.GameEvents.REACTOR_LOW_CR_VIBRATION;
public class BFReactorMultiblockData extends MultiblockData {

    public static final String HEAT_TAB = "heat";
    public static final String FUEL_TAB = "fuel";
    public static final String STATS_TAB = "stats";

    public static final int MAX_INJECTION = 98;//this is the effective cap in the GUI, as text field is limited to 2 chars
    //Reaction characteristics
    private static final double burnTemperature = 100_000_000;
    private static final double burnRatio = 1;
    //Thermal characteristics
    private static final long plasmaHeatCapacity = 100;
    private static final double caseHeatCapacity = 1;
    private static final double inverseInsulation = 100_000;
    //Heat transfer metrics
    private static final double plasmaCaseConductivity = 0.2;

    private final List<EnergyOutputTarget> energyOutputTargets = new ArrayList<>();
    private final List<CapabilityOutputTarget<IChemicalHandler>> chemicalOutputTargets = new ArrayList<>();
    private final Set<ITileHeatHandler> heatHandlers = new ObjectOpenHashSet<>();

    @ContainerSync
    private boolean burning = false;

    @ContainerSync
    public IEnergyContainer energyContainer;
    public IHeatCapacitor heatCapacitor;

    @ContainerSync(tags = HEAT_TAB)
    @WrappingComputerMethod(wrapper = ComputerFluidTankWrapper.class, methodNames = {"getLiquidCoolant", "getLiquidCoolantCapacity", "getLiquidCoolantNeeded", "getLiquidCoolantFilledPercentage"}, docPlaceholder = "cold coolant tank")
    public IExtendedFluidTank liquidCoolantTank;

    @ContainerSync(tags = HEAT_TAB)
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getSteam", "getSteamCapacity", "getSteamNeeded", "getSteamFilledPercentage"}, docPlaceholder = "steam tank")
    public IChemicalTank steamTank;

    @ContainerSync(tags = HEAT_TAB)
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getCoolant", "getCoolantCapacity", "getCoolantNeeded", "getCoolantFilledPercentage"}, docPlaceholder = "coolant tank")
    public IChemicalTank gasCoolantTank;

    private double biomeAmbientTemp;
    @ContainerSync(tags = HEAT_TAB)
    private double lastPlasmaTemperature;
    @ContainerSync
    private double lastCaseTemperature;
    @ContainerSync
    @SyntheticComputerMethod(getter = "getEnvironmentalLoss")
    public double lastEnvironmentLoss;
    @ContainerSync
    @SyntheticComputerMethod(getter = "getTransferLoss")
    public double lastTransferLoss;

    @ContainerSync(tags = FUEL_TAB)
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getDeuterium", "getDeuteriumCapacity", "getDeuteriumNeeded", "getDeuteriumFilledPercentage"}, docPlaceholder = "deuterium tank")
    public IChemicalTank deuteriumTank;
    @ContainerSync(tags = FUEL_TAB)
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getTritium", "getTritiumCapacity", "getTritiumNeeded", "getTritiumFilledPercentage"}, docPlaceholder = "tritium tank")
    public IChemicalTank tritiumTank;
    @ContainerSync(tags = FUEL_TAB)
    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = {"getDTFuel", "getDTFuelCapacity", "getDTFuelNeeded", "getDTFuelFilledPercentage"}, docPlaceholder = "fuel tank")
    public IChemicalTank fuelTank;
    @ContainerSync(tags = {FUEL_TAB, HEAT_TAB, STATS_TAB}, getter = "getInjectionRate", setter = "setInjectionRate")
    private int injectionRate = 2;
    @ContainerSync(tags = {FUEL_TAB, HEAT_TAB, STATS_TAB})
    private long lastBurned;

    public double plasmaTemperature;

    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getHohlraum", docPlaceholder = "Hohlraum slot")
    final ReactorInventorySlot reactorSlot;

    private boolean clientBurning;
    private double clientTemp;

    private int maxWater;
    private long maxSteam;

    private AABB deathZone;

    protected int laserShootCountdown = 0;
    protected int laserShootEnergyDuration = 12000;
    public double laserShootMinEnergy = 500000000;
    protected float currentReactivity = 0;
    protected float targetReactivity = 0;
    protected float errorLevel = 0;
    protected float adjustment = 0;
    protected int reactivityUpdateTicks = 10000;
    protected int currentReactivityTick = 0;
    protected int adjustmentTicks = 80;
    protected float difficulty = 10;
    public boolean explodeFlag = false;

    public BFReactorMultiblockData(TileEntityFusionReactorBlock tile) {
        super(tile);
        if(BetterFusionReactorConfig.bfr.isLoaded()) {
            BetterFusionReactorConfig.bfr.initFusionCoolants();
        }
        //Default biome temp to the ambient temperature at the block we are at
        biomeAmbientTemp = HeatAPI.getAmbientTemp(tile.getLevel(), tile.getBlockPos());
        lastPlasmaTemperature = biomeAmbientTemp;
        lastCaseTemperature = biomeAmbientTemp;
        plasmaTemperature = biomeAmbientTemp;
        chemicalTanks.add(deuteriumTank = VariableCapacityChemicalTank.input(this, MekanismGeneratorsConfig.generators.fusionFuelCapacity,
              chemical -> chemical.is(BfrTags.Chemicals.DEUTERIUM), this));
        chemicalTanks.add(tritiumTank = VariableCapacityChemicalTank.input(this, MekanismGeneratorsConfig.generators.fusionFuelCapacity,
              chemical -> chemical.is(BfrTags.Chemicals.TRITIUM), this));
        chemicalTanks.add(fuelTank = VariableCapacityChemicalTank.input(this, MekanismGeneratorsConfig.generators.fusionFuelCapacity,
              chemical -> chemical.is(BfrTags.Chemicals.FUSION_FUEL), createSaveAndComparator()));
        chemicalTanks.add(steamTank = VariableCapacityChemicalTank.output(this, this::getMaxSteam, chemical -> BetterFusionReactorConfig.bfr.allowedCoolantHotGases.contains(chemical.getChemical()), this));
        chemicalTanks.add(gasCoolantTank = VariableCapacityChemicalTank.input(this, this::getMaxWater, chemical -> BetterFusionReactorConfig.bfr.allowedCoolantGases.contains(chemical.getChemical()), this));
        fluidTanks.add(liquidCoolantTank = VariableCapacityFluidTank.input(this, this::getMaxWater, fluid -> BetterFusionReactorConfig.bfr.allowedCoolantFluids.contains(fluid.getFluid()), this));
        energyContainers.add(energyContainer = VariableCapacityEnergyContainer.output(MekanismGeneratorsConfig.generators.fusionEnergyCapacity, this));
        heatCapacitors.add(heatCapacitor = VariableHeatCapacitor.create(caseHeatCapacity, BFReactorMultiblockData::getInverseConductionCoefficient,
              () -> inverseInsulation, () -> biomeAmbientTemp, this));
        inventorySlots.add(reactorSlot = ReactorInventorySlot.at(stack -> stack.getItem() instanceof ItemHohlraum, this, 85, 39));
    }

    //Target reactivity update rate depends on temperature
    public float getTargetReactivity()
    {
        return targetReactivity;
    }

    public float getKt()
    {
        //so laser gives you 1 minute of independence from Kt
        if(laserShootEnergyDuration - getLaserShootCountdown() < 1200) {
            return  0;
        }
        float tDevide = 20;
        if(isActiveCooled()) {
            tDevide = 30;
        }
        return (float) Math.pow((float)(Math.abs(Math.sqrt((float)getLastPlasmaTemp()/2000000) - 40))/tDevide, 2);
    }

    protected boolean isActiveCooled()
    {
        return !fluidTanks.get(0).isEmpty();
    }

    /** value in range 0..100 **/
    @ComputerMethod
    public float getEfficiency()
    {
        if(!isBurning()) return 0;
        return (float) Math.min(100, Math.max(0, 1/(Math.abs(targetReactivity - currentReactivity)/100 + 0.2)*22)-10);
    }

    public void setAdjustment(float val)
    {
        adjustment = val;
    }

    public float getAdjustment() {
        return adjustment;
    }

    public float getCurrentReactivity()
    {
        return currentReactivity;
    }

    public void setTargetReactivity(float val)
    {
        targetReactivity = Math.abs(val);
    }

    public void setCurrentReactivity(float val)
    {
        currentReactivity = Math.abs(val);
    }

    @ComputerMethod
    public float getErrorLevel()
    {
        return errorLevel;
    }

    protected void setErrorLevel(float val)
    {
        errorLevel = val;
    }

    public int getLaserShootCountdown()
    {
        return laserShootCountdown;
    }

    protected void setLaserShootCountdown(int val)
    {
        laserShootCountdown = val;
    }
    
    public void processLaserShoot(long laserEnergy)
    {
        if(laserEnergy >= laserShootMinEnergy && laserShootCountdown == 0) {
            laserShootCountdown = laserShootEnergyDuration;
        }
    }

    protected void laserShootCount()
    {
        if(laserShootCountdown > 0) {
            laserShootCountdown--;
        }
    }

    // if efficiency bigger than 80% we reducing chances
    protected void updateErrorLevel()
    {
        if(isBurning()) {
            float shift = ((80 - getEfficiency()) * ((getKt() + 1) / 2)) * 0.0005f;
            if(shift > 0) {
                shift = shift*(difficulty/10);
            }
            errorLevel += shift;
        } else {
            errorLevel -= 0.1;
        }

        errorLevel = Math.min(100, Math.max(0, errorLevel));
        if(errorLevel > 0) {
            markDirty();
        }
        if(errorLevel >= 100) {
            currentReactivity = 0;
            targetReactivity = 0;
            adjustment = 0;
            setBurning(false);
            if(BetterFusionReactorConfig.bfr.reactorMeltdown.get()) {
                explodeFlag = true;
            }
        }
    }

    public boolean adjustReactivity(float rate)
    {
        if(adjustment != 0) return false;
        adjustment = rate/(float)adjustmentTicks;
        return true;
    }

    protected void updateAdjustment()
    {
        if(adjustment == 0) return;
        currentReactivity += adjustment;
        currentReactivity = Math.min(100, Math.max(0, currentReactivity));
        adjustmentTicks--;
        if(adjustmentTicks < 1) {
            adjustmentTicks = 80;
            adjustment = 0;
        }
        markDirty();
    }

    public int reactivityUpdateTicksScaled()
    {
        return (int) (( reactivityUpdateTicks / (getKt() + 0.25)) * (difficulty/10));
    }

    public void updateReactivity()
    {
        float low = 0f;
        float high = 100f;
        currentReactivityTick++;
        if(reactivityUpdateTicksScaled() < currentReactivityTick) {
            currentReactivityTick = 0;
            float currentTarget = getTargetReactivity();
            // Limit maximum change to 75% relative to the current target reactivity
            float maxDelta = 75f;
            float newLow = Math.max(low, currentTarget - maxDelta);
            float newHigh = Math.min(high, currentTarget + maxDelta);
            setTargetReactivity(newLow + new Random().nextFloat() * (newHigh - newLow));
            setCurrentReactivity((low + new Random().nextFloat() * (high - low) + currentReactivity*3)/4);
        }
    }

    @ComputerMethod(nameOverride = "adjustReactivity")
    public void computerAdjustReactivity(float val) throws ComputerException {
        if(val > 100 || val < -100) {
            throw new ComputerException("Adjustment must be float value in range [-100 .. 100]");
        }
        adjustReactivity(val);
    }

    @Override
    public void onCreated(Level world) {
        super.onCreated(world);
        for (ValveData data : valves) {
            BlockEntity tile = WorldUtils.getTileEntity(world, data.location);
            if (tile instanceof TileEntityFusionReactorPort port) {
                heatHandlers.add(port);
            }
        }
        biomeAmbientTemp = calculateAverageAmbientTemperature(world);
        deathZone = AABB.encapsulatingFullBlocks(getMinPos().offset(1, 1, 1), getMaxPos().offset(-1, -1, -1));
    }

    @Override
    public boolean allowsStructuralGuiAccess(TileEntityStructuralMultiblock multiblock) {
        return false;
    }

    @Override
    public void readUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.readUpdateTag(tag, provider);
        NBTUtils.setDoubleIfPresent(tag, SerializationConstants.PLASMA_TEMP, this::setLastPlasmaTemp);
        NBTUtils.setBooleanIfPresent(tag, SerializationConstants.BURNING, this::setBurning);
        NBTUtils.setFloatIfPresent(tag, ReactorConstants.NBT_CURRENT_REACTIVITY, this::setCurrentReactivity);
        NBTUtils.setFloatIfPresent(tag, ReactorConstants.NBT_TARGET_REACTIVITY, this::setTargetReactivity);
        NBTUtils.setFloatIfPresent(tag, ReactorConstants.NBT_ERROR_LEVEL, this::setErrorLevel);
        NBTUtils.setFloatIfPresent(tag, ReactorConstants.NBT_ADJUSTMENT, this::setAdjustment);
        NBTUtils.setIntIfPresent(tag, ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN, this::setLaserShootCountdown);
    }

    @Override
    public void writeUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.writeUpdateTag(tag, provider);
        tag.putDouble(SerializationConstants.PLASMA_TEMP, getLastPlasmaTemp());
        tag.putBoolean(SerializationConstants.BURNING, isBurning());
        tag.putFloat(ReactorConstants.NBT_CURRENT_REACTIVITY, getCurrentReactivity());
        tag.putFloat(ReactorConstants.NBT_TARGET_REACTIVITY, getTargetReactivity());
        tag.putFloat(ReactorConstants.NBT_ERROR_LEVEL, getErrorLevel());
        tag.putFloat(ReactorConstants.NBT_ADJUSTMENT, getAdjustment());
        tag.putInt(ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN, getLaserShootCountdown());
    }

    public void addTemperatureFromEnergyInput(long energyAdded) {
        if (isBurning()) {
            setPlasmaTemp(getPlasmaTemp() + ((double) energyAdded / plasmaHeatCapacity));
        } else {
            setPlasmaTemp(getPlasmaTemp() + ((double) energyAdded / plasmaHeatCapacity) * 10);
        }
    }

    private boolean hasHohlraum() {
        if (!reactorSlot.isEmpty()) {
            ItemStack hohlraum = reactorSlot.getStack();
            if (hohlraum.getItem() instanceof ItemHohlraum) {
                IChemicalHandler gasHandlerItem = Capabilities.CHEMICAL.getCapability(hohlraum);
                if (gasHandlerItem != null && gasHandlerItem.getChemicalTanks() > 0) {
                    //Validate something didn't go terribly wrong, and we actually do have the tank we expect to have
                    return gasHandlerItem.getChemicalInTank(0).getAmount() == gasHandlerItem.getChemicalTankCapacity(0);
                }
            }
        }
        return false;
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);
        long fuelBurned = 0;
        //Only thermal transfer happens unless we're hot enough to burn.
        if (getPlasmaTemp() >= burnTemperature) {
            //If we're not burning, yet we need a hohlraum to ignite
            if (!burning && hasHohlraum()) {
                vaporiseHohlraum();
                float low = 0F;
                float high = 100F;
                setCurrentReactivity(low + new Random().nextFloat() * (high - low));
                setTargetReactivity(low + new Random().nextFloat() * (high - low));
            }
            updateErrorLevel();
            //Only inject fuel if we're burning
            if (isBurning()) {
                laserShootCount();
                updateReactivity();
                updateAdjustment();
                injectFuel();
                fuelBurned = burnFuel();
                if (fuelBurned == 0) {
                    setBurning(false);
                }
            }
        } else {
            currentReactivity = 0;
            targetReactivity = 0;
            adjustment = 0;
            setBurning(false);
        }

        if (lastBurned != fuelBurned) {
            lastBurned = fuelBurned;
        }

        //Perform the heat transfer calculations
        transferHeat();
        updateHeatCapacitors(null);
        updateTemperatures();

        if (!energyOutputTargets.isEmpty() && !energyContainer.isEmpty()) {
            CableUtils.emit(getActiveOutputs(energyOutputTargets), energyContainer);
        }

        if (!chemicalOutputTargets.isEmpty() && !steamTank.isEmpty()) {
            ChemicalUtil.emit(getActiveOutputs(chemicalOutputTargets), steamTank);
        }

        if (isBurning()) {
            kill(world);
        }

        if (
            isBurning() != clientBurning ||
            Math.abs(getLastPlasmaTemp() - clientTemp) > 1_000_000 ||
            getAdjustment() > 0 ||
            getTargetReactivity() != targetReactivity ||
            getErrorLevel() > 0
        ) {
            clientBurning = isBurning();
            clientTemp = getLastPlasmaTemp();
            targetReactivity = getTargetReactivity();
            errorLevel = getErrorLevel();
            needsPacket = true;
        }
        if(getLevel().getGameTime() % 20 == 0) {
            int reactivityDelta = (int) (currentReactivity - targetReactivity);
            if (reactivityDelta > 4.9) {
                getLevel().gameEvent(null, REACTOR_HI_CR_VIBRATION.getDelegate(), getMaxPos().offset(-3, -3, -3));
            }
            if (reactivityDelta < -4.9) {
                getLevel().gameEvent(null, REACTOR_LOW_CR_VIBRATION.getDelegate(), getMaxPos().below());
            }
        }

        return needsPacket;
    }

    @Override
    protected void updateEjectors(Level world) {
        energyOutputTargets.clear();
        chemicalOutputTargets.clear();
        for (ValveData valve : valves) {
            TileEntityFusionReactorPort tile = WorldUtils.getTileEntity(TileEntityFusionReactorPort.class, world, valve.location);
            if (tile != null) {
                tile.addEnergyTargetCapability(energyOutputTargets, valve.side);
                tile.addGasTargetCapability(chemicalOutputTargets, valve.side);
            }
        }
    }

    public void updateTemperatures() {
        lastPlasmaTemperature = getPlasmaTemp();
        lastCaseTemperature = heatCapacitor.getTemperature();
    }

    private void kill(Level world) {
        if (world.getRandom().nextInt() % SharedConstants.TICKS_PER_SECOND != 0) {
            return;
        }
        List<Entity> entitiesToDie = world.getEntitiesOfClass(Entity.class, deathZone);
        if (!entitiesToDie.isEmpty()) {
            DamageSource damageSource = GeneratorsDamageTypes.FUSION.source(world, deathZone.getCenter());
            for (Entity entity : entitiesToDie) {
                entity.hurt(damageSource, 50_000F);
            }
        }
    }

    private void vaporiseHohlraum() {
        ItemStack hohlraum = reactorSlot.getStack();
        IChemicalHandler gasHandlerItem = Capabilities.CHEMICAL.getCapability(hohlraum);
        if (gasHandlerItem != null && gasHandlerItem.getChemicalTanks() > 0) {
            fuelTank.insert(gasHandlerItem.getChemicalInTank(0), Action.EXECUTE, AutomationType.INTERNAL);
            lastPlasmaTemperature = getPlasmaTemp();
            reactorSlot.setEmpty();
            setBurning(true);
        }
    }

    private void injectFuel() {
        long amountNeeded = fuelTank.getNeeded();
        long amountAvailable = 2 * Math.min(deuteriumTank.getStored(), tritiumTank.getStored());
        long amountToInject = Math.min(amountNeeded, Math.min(amountAvailable, injectionRate));
        amountToInject -= amountToInject % 2;
        long injectingAmount = amountToInject / 2;
        MekanismUtils.logMismatchedStackSize(deuteriumTank.shrinkStack(injectingAmount, Action.EXECUTE), injectingAmount);
        MekanismUtils.logMismatchedStackSize(tritiumTank.shrinkStack(injectingAmount, Action.EXECUTE), injectingAmount);
        fuelTank.insert(GeneratorsChemicals.FUSION_FUEL.asStack(amountToInject), Action.EXECUTE, AutomationType.INTERNAL);
    }

    private long burnFuel() {
        long fuelBurned = MathUtils.clampToLong(Mth.clamp((lastPlasmaTemperature - burnTemperature) * burnRatio, 0, fuelTank.getStored()));
        MekanismUtils.logMismatchedStackSize(fuelTank.shrinkStack(fuelBurned, Action.EXECUTE), fuelBurned);
        setPlasmaTemp(getPlasmaTemp() + (MathUtils.multiplyClamped(MekanismGeneratorsConfig.generators.energyPerFusionFuel.get(), fuelBurned) / (double) plasmaHeatCapacity));
        return fuelBurned;
    }

    private void transferHeat() {
        //Transfer from plasma to casing
        double plasmaCaseHeat = plasmaCaseConductivity * (lastPlasmaTemperature - lastCaseTemperature);
        if (Math.abs(plasmaCaseHeat) > HeatAPI.EPSILON) {
            setPlasmaTemp(getPlasmaTemp() - plasmaCaseHeat / plasmaHeatCapacity);
            heatCapacitor.handleHeat(plasmaCaseHeat);
        }

        //Transfer from casing to water if necessary
        double caseWaterHeat = MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() * (lastCaseTemperature - biomeAmbientTemp);
        if (Math.abs(caseWaterHeat) > HeatAPI.EPSILON) {
            int waterToVaporize = (int) (HeatUtils.getSteamEnergyEfficiency() * caseWaterHeat / HeatUtils.getWaterThermalEnthalpy());
            waterToVaporize = Math.min(waterToVaporize, Math.min(liquidCoolantTank.getFluidAmount(), MathUtils.clampToInt(steamTank.getNeeded())));
            if (waterToVaporize > 0) {
                MekanismUtils.logMismatchedStackSize(liquidCoolantTank.shrinkStack(waterToVaporize, Action.EXECUTE), waterToVaporize);
                steamTank.insert(MekanismChemicals.STEAM.asStack(waterToVaporize), Action.EXECUTE, AutomationType.INTERNAL);
                caseWaterHeat = waterToVaporize * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
                heatCapacitor.handleHeat(-caseWaterHeat);
            } else {
                long gasToVaporize = (long) (HeatUtils.getSteamEnergyEfficiency() * caseWaterHeat / HeatUtils.getWaterThermalEnthalpy());
                gasToVaporize = Math.min(gasToVaporize, Math.min(gasCoolantTank.getStored(), steamTank.getNeeded()));
                if(gasToVaporize > 0) {

                    for(Chemical out: BetterFusionReactorConfig.bfr.coolantMap.keySet()) {
                        if(BetterFusionReactorConfig.bfr.coolantMap.get(out).equals(gasCoolantTank.getType())) {
                            steamTank.insert(new ChemicalStack(out, gasToVaporize), Action.EXECUTE, AutomationType.INTERNAL);
                            MekanismUtils.logMismatchedStackSize(gasCoolantTank.shrinkStack(gasToVaporize, Action.EXECUTE), gasToVaporize);
                            break;
                        }
                    }
                    caseWaterHeat = gasToVaporize * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency();
                    heatCapacitor.handleHeat(-caseWaterHeat);
                }
            }
        }

        HeatTransfer heatTransfer = simulate();
        lastEnvironmentLoss = heatTransfer.environmentTransfer();
        lastTransferLoss = heatTransfer.adjacentTransfer();

        //Passive energy generation
        double caseAirHeat = MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get() * (lastCaseTemperature - biomeAmbientTemp);
        if (Math.abs(caseAirHeat) > HeatAPI.EPSILON) {
            heatCapacitor.handleHeat(-caseAirHeat);
            energyContainer.insert(MathUtils.clampToLong(caseAirHeat * MekanismGeneratorsConfig.generators.fusionThermocoupleEfficiency.get()), Action.EXECUTE, AutomationType.INTERNAL);
        }
    }

    @NotNull
    @Override
    public HeatTransfer simulate() {
        double environmentTransfer = 0;
        double adjacentTransfer = 0;
        for (ITileHeatHandler source : heatHandlers) {
            HeatTransfer heatTransfer = source.simulate();
            adjacentTransfer += heatTransfer.adjacentTransfer();
            environmentTransfer += heatTransfer.environmentTransfer();
        }
        return new HeatTransfer(adjacentTransfer, environmentTransfer);
    }

    public void setLastPlasmaTemp(double temp) {
        lastPlasmaTemperature = temp;
    }

    @ComputerMethod(nameOverride = "getPlasmaTemperature")
    public double getLastPlasmaTemp() {
        return lastPlasmaTemperature;
    }

    @ComputerMethod(nameOverride = "getCaseTemperature")
    public double getLastCaseTemp() {
        return lastCaseTemperature;
    }

    public double getPlasmaTemp() {
        return plasmaTemperature;
    }

    public void setPlasmaTemp(double temp) {
        if (plasmaTemperature != temp) {
            plasmaTemperature = temp;
            markDirty();
        }
    }

    @ComputerMethod
    public int getInjectionRate() {
        return injectionRate;
    }

    public void setInjectionRate(int rate) {
        if (injectionRate != rate) {
            injectionRate = rate;
            maxWater = injectionRate * MekanismGeneratorsConfig.generators.fusionWaterPerInjection.get();
            maxSteam = injectionRate * MekanismGeneratorsConfig.generators.fusionSteamPerInjection.get();
            if (getLevel() != null && !isRemote()) {
                if (!liquidCoolantTank.isEmpty()) {
                    liquidCoolantTank.setStackSize(Math.min(liquidCoolantTank.getFluidAmount(), liquidCoolantTank.getCapacity()), Action.EXECUTE);
                }
                if (!gasCoolantTank.isEmpty()) {
                    gasCoolantTank.setStackSize(Math.min(gasCoolantTank.getStored(), gasCoolantTank.getCapacity()), Action.EXECUTE);
                }
                if (!steamTank.isEmpty()) {
                    steamTank.setStackSize(Math.min(steamTank.getStored(), steamTank.getCapacity()), Action.EXECUTE);
                }
            }
            markDirty();
        }
    }

    public int getMaxWater() {
        return maxWater;
    }

    public long getMaxSteam() {
        return maxSteam;
    }

    @ComputerMethod(nameOverride = "isIgnited", methodDescription = "Checks if a reaction is occurring.")
    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burn) {
        if (burning != burn) {
            burning = burn;
            markDirty();
        }
    }

    public double getCaseTemp() {
        return heatCapacitor.getTemperature();
    }

    @Override
    protected int getMultiblockRedstoneLevel() {
        return MekanismUtils.redstoneLevelFromContents(fuelTank.getStored(), fuelTank.getCapacity());
    }

    @ComputerMethod(methodDescription = "true -> water cooled, false -> air cooled")
    public int getMinInjectionRate(boolean active) {
        double k = active ? MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() : 0;
        double caseAirConductivity = MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get();
        double aMin = burnTemperature * burnRatio * plasmaCaseConductivity * (k + caseAirConductivity) /
                      (MekanismGeneratorsConfig.generators.energyPerFusionFuel.get() * burnRatio * (plasmaCaseConductivity + k + caseAirConductivity) -
                       plasmaCaseConductivity * (k + caseAirConductivity));
        return 2 * Mth.ceil(aMin / 2D);
    }

    @ComputerMethod(methodDescription = "true -> water cooled, false -> air cooled")
    public double getMaxPlasmaTemperature(boolean active) {
        double k = active ? MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() : 0;
        double caseAirConductivity = MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get();
        long injectionRate = Math.max(this.injectionRate, lastBurned);
        return injectionRate * MekanismGeneratorsConfig.generators.energyPerFusionFuel.get() / plasmaCaseConductivity *
               (plasmaCaseConductivity + k + caseAirConductivity) / (k + caseAirConductivity);
    }

    @ComputerMethod(methodDescription = "true -> water cooled, false -> air cooled")
    public double getMaxCasingTemperature(boolean active) {
        double k = active ? MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() : 0;
        long injectionRate = Math.max(this.injectionRate, lastBurned);
        return MathUtils.multiplyClamped(MekanismGeneratorsConfig.generators.energyPerFusionFuel.get(), injectionRate)
               / (k + MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get());
    }

    @ComputerMethod(methodDescription = "true -> water cooled, false -> air cooled")
    public double getIgnitionTemperature(boolean active) {
        double k = active ? MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() : 0;
        double caseAirConductivity = MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get();
        double energyPerFusionFuel = MekanismGeneratorsConfig.generators.energyPerFusionFuel.get();
        return burnTemperature * energyPerFusionFuel * burnRatio * (plasmaCaseConductivity + k + caseAirConductivity) /
               (energyPerFusionFuel * burnRatio * (plasmaCaseConductivity + k + caseAirConductivity) - plasmaCaseConductivity * (k + caseAirConductivity));
    }

    public long getPassiveGeneration(boolean active, boolean current) {
        double temperature = current ? getLastCaseTemp() : getMaxCasingTemperature(active);
        return MathUtils.clampToLong(MekanismGeneratorsConfig.generators.fusionThermocoupleEfficiency.get() *
                MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get() * temperature *
                ((getEfficiency()/100+2)/3));
    }

    public long getSteamPerTick(boolean current) {
        double temperature = current ? getLastCaseTemp() : getMaxCasingTemperature(true);
        return MathUtils.clampToLong(HeatUtils.getSteamEnergyEfficiency() * 
                MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio.get() * 
                temperature / HeatUtils.getWaterThermalEnthalpy() *
                ((getEfficiency()/100+2)/3));
    }

    private static double getInverseConductionCoefficient() {
        return 1 / MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get();
    }

    //Computer related methods
    @ComputerMethod(nameOverride = "setInjectionRate")
    void computerSetInjectionRate(int rate) throws ComputerException {
        if (rate < 0 || rate > MAX_INJECTION) {
            //Validate bounds even though we can clamp
            throw new ComputerException("Injection Rate '%d' is out of range must be an even number between 0 and %d. (Inclusive)", rate, MAX_INJECTION);
        } else if (rate % 2 != 0) {
            //Validate it is even
            throw new ComputerException("Injection Rate '%d' must be an even number between 0 and %d. (Inclusive)", rate, MAX_INJECTION);
        }
        setInjectionRate(rate);
    }

    @ComputerMethod
    long getPassiveGeneration(boolean active) {
        return getPassiveGeneration(active, false);
    }

    @ComputerMethod
    long getProductionRate() {
        return getPassiveGeneration(false, true);
    }
    //End computer related methods
}
