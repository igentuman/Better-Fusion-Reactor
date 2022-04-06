package igentuman.bfr.client;

import mekanism.client.state.BaseBlockStateProvider;
import igentuman.bfr.common.MekanismGenerators;
import igentuman.bfr.common.registries.BfrFluids;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrBlockStateProvider extends BaseBlockStateProvider<BfrBlockModelProvider> {

    public BfrBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MekanismGenerators.MODID, existingFileHelper, BfrBlockModelProvider::new);
    }

    @Override
    protected void registerStatesAndModels() {
        registerFluidBlockStates(BfrFluids.FLUIDS.getAllFluids());
    }
}