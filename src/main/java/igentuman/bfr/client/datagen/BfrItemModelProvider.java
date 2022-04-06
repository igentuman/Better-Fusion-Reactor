package igentuman.bfr.client;

import mekanism.client.model.BaseItemModelProvider;
import igentuman.bfr.common.MekanismGenerators;
import igentuman.bfr.common.registries.BfrFluids;
import igentuman.bfr.common.registries.BfrItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrItemModelProvider extends BaseItemModelProvider {

    public BfrItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MekanismGenerators.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBuckets(BfrFluids.FLUIDS);
        registerModules(BfrItems.ITEMS);
        registerGenerated(BfrItems.HOHLRAUM, BfrItems.SOLAR_PANEL, BfrItems.TURBINE_BLADE);
    }
}