package igentuman.bfr.common.content.fusion;

import java.util.List;
import mekanism.api.NBTConstants;
import mekanism.common.lib.multiblock.MultiblockCache;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class FusionReactorCache extends MultiblockCache<FusionReactorMultiblockData> {

    private double plasmaTemperature = -1;
    private int injectionRate = -1;
    private boolean burning;
    private float currentReactivity;
    private float targetReactivity;
    private float adjustment;
    private float errorLevel;
    private int laserCountdown;


    private int getInjectionRate() {
        if (injectionRate == -1) {
            //If it never got set default to 2
            return 2;
        }
        //Otherwise, return the actual so that it can be manually set down to zero
        return injectionRate;
    }

    @Override
    public void merge(MultiblockCache<FusionReactorMultiblockData> mergeCache, List<ItemStack> rejectedItems) {
        super.merge(mergeCache, rejectedItems);
        plasmaTemperature = Math.max(plasmaTemperature, ((FusionReactorCache) mergeCache).plasmaTemperature);
        currentReactivity = Math.max(currentReactivity, ((FusionReactorCache) mergeCache).currentReactivity);
        targetReactivity = Math.max(targetReactivity, ((FusionReactorCache) mergeCache).targetReactivity);
        adjustment = Math.max(adjustment, ((FusionReactorCache) mergeCache).adjustment);
        errorLevel = Math.max(errorLevel, ((FusionReactorCache) mergeCache).errorLevel);
        injectionRate = Math.max(injectionRate, ((FusionReactorCache) mergeCache).injectionRate);
        laserCountdown = Math.min(laserCountdown, ((FusionReactorCache) mergeCache).laserCountdown);
        burning |= ((FusionReactorCache) mergeCache).burning;
    }

    @Override
    public void apply(FusionReactorMultiblockData data) {
        super.apply(data);
        if (plasmaTemperature >= 0) {
            data.plasmaTemperature = plasmaTemperature;
        }
        data.setInjectionRate(getInjectionRate());
        data.setBurning(burning);
        data.setAdjustment(adjustment);
        data.setCurrentReactivity(currentReactivity);
        data.setTargetReactivity(targetReactivity);
        data.setErrorLevel(errorLevel);
        data.setLaserShootCountdown(laserCountdown);
        data.updateTemperatures();
    }

    @Override
    public void sync(FusionReactorMultiblockData data) {
        super.sync(data);
        plasmaTemperature = data.plasmaTemperature;
        injectionRate = data.getInjectionRate();
        currentReactivity = data.getCurrentReactivity();
        targetReactivity = data.getTargetReactivity();
        errorLevel = data.getErrorLevel();
        adjustment = data.getAdjustment();
        laserCountdown = data.getLaserShootCountdown();
        burning = data.isBurning();
    }

    @Override
    public void load(CompoundNBT nbtTags) {
        super.load(nbtTags);
        plasmaTemperature = nbtTags.getDouble(NBTConstants.PLASMA_TEMP);
        injectionRate = nbtTags.getInt(NBTConstants.INJECTION_RATE);
        burning = nbtTags.getBoolean(NBTConstants.BURNING);
        adjustment = nbtTags.getFloat(ReactorConstants.NBT_ADJUSTMENT);
        currentReactivity = nbtTags.getFloat(ReactorConstants.NBT_CURRENT_REACTIVITY);
        targetReactivity = nbtTags.getFloat(ReactorConstants.NBT_TARGET_REACTIVITY);
        errorLevel = nbtTags.getFloat(ReactorConstants.NBT_ERROR_LEVEL);
        laserCountdown = nbtTags.getInt(ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN);
    }

    @Override
    public void save(CompoundNBT nbtTags) {
        super.save(nbtTags);
        nbtTags.putDouble(NBTConstants.PLASMA_TEMP, plasmaTemperature);
        nbtTags.putInt(NBTConstants.INJECTION_RATE, getInjectionRate());
        nbtTags.putInt(ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN, laserCountdown);
        nbtTags.putFloat(ReactorConstants.NBT_CURRENT_REACTIVITY, currentReactivity);
        nbtTags.putFloat(ReactorConstants.NBT_TARGET_REACTIVITY, targetReactivity);
        nbtTags.putFloat(ReactorConstants.NBT_ERROR_LEVEL, errorLevel);
        nbtTags.putFloat(ReactorConstants.NBT_ADJUSTMENT, adjustment);
        nbtTags.putBoolean(NBTConstants.BURNING, burning);
    }
}
