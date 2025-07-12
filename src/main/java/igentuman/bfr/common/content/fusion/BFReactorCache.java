package igentuman.bfr.common.content.fusion;

import mekanism.api.SerializationConstants;
import mekanism.common.lib.multiblock.MultiblockCache;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class BFReactorCache extends MultiblockCache<BFReactorMultiblockData> {

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
    public void merge(MultiblockCache<BFReactorMultiblockData> mergeCache, RejectContents rejectContents) {
        super.merge(mergeCache, rejectContents);
        plasmaTemperature = Math.max(plasmaTemperature, ((BFReactorCache) mergeCache).plasmaTemperature);
        currentReactivity = Math.max(currentReactivity, ((BFReactorCache) mergeCache).currentReactivity);
        targetReactivity = Math.max(targetReactivity, ((BFReactorCache) mergeCache).targetReactivity);
        adjustment = Math.max(adjustment, ((BFReactorCache) mergeCache).adjustment);
        errorLevel = Math.max(errorLevel, ((BFReactorCache) mergeCache).errorLevel);
        injectionRate = Math.max(injectionRate, ((BFReactorCache) mergeCache).injectionRate);
        laserCountdown = Math.min(laserCountdown, ((BFReactorCache) mergeCache).laserCountdown);
        burning |= ((BFReactorCache) mergeCache).burning;
    }

    @Override
    public void apply(HolderLookup.Provider provider, BFReactorMultiblockData data) {
        super.apply(provider, data);
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
    public void sync(BFReactorMultiblockData data) {
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
    public void load(HolderLookup.Provider provider, CompoundTag nbtTags) {
        super.load(provider, nbtTags);
        plasmaTemperature = nbtTags.getDouble(SerializationConstants.PLASMA_TEMP);
        injectionRate = nbtTags.getInt(SerializationConstants.INJECTION_RATE);
        burning = nbtTags.getBoolean(SerializationConstants.BURNING);
        adjustment = nbtTags.getFloat(ReactorConstants.NBT_ADJUSTMENT);
        currentReactivity = nbtTags.getFloat(ReactorConstants.NBT_CURRENT_REACTIVITY);
        targetReactivity = nbtTags.getFloat(ReactorConstants.NBT_TARGET_REACTIVITY);
        errorLevel = nbtTags.getFloat(ReactorConstants.NBT_ERROR_LEVEL);
        laserCountdown = nbtTags.getInt(ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN);
    }

    @Override
    public void save(HolderLookup.Provider provider, CompoundTag nbtTags) {
        super.save(provider, nbtTags);
        nbtTags.putDouble(SerializationConstants.PLASMA_TEMP, plasmaTemperature);
        nbtTags.putInt(SerializationConstants.INJECTION_RATE, getInjectionRate());
        nbtTags.putBoolean(SerializationConstants.BURNING, burning);
        nbtTags.putInt(ReactorConstants.NBT_LASER_SHOOT_COUNTDOWN, laserCountdown);
        nbtTags.putFloat(ReactorConstants.NBT_CURRENT_REACTIVITY, currentReactivity);
        nbtTags.putFloat(ReactorConstants.NBT_TARGET_REACTIVITY, targetReactivity);
        nbtTags.putFloat(ReactorConstants.NBT_ERROR_LEVEL, errorLevel);
        nbtTags.putFloat(ReactorConstants.NBT_ADJUSTMENT, adjustment);
    }
}
