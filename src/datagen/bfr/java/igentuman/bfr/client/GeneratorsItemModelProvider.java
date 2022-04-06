package igentuman.bfr.client;

import igentuman.client.model.BaseItemModelProvider;
import igentuman.bfr.common.MekanismGenerators;
import igentuman.bfr.common.registries.GeneratorsFluids;
import igentuman.bfr.common.registries.GeneratorsItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeneratorsItemModelProvider extends BaseItemModelProvider {

    public GeneratorsItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MekanismGenerators.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBuckets(GeneratorsFluids.FLUIDS);
        registerModules(GeneratorsItems.ITEMS);
        registerGenerated(GeneratorsItems.HOHLRAUM, GeneratorsItems.SOLAR_PANEL, GeneratorsItems.TURBINE_BLADE);
    }
}