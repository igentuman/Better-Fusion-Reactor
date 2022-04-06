package igentuman.bfr.client;

import igentuman.client.state.BaseBlockStateProvider;
import igentuman.bfr.common.MekanismGenerators;
import igentuman.bfr.common.registries.GeneratorsFluids;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeneratorsBlockStateProvider extends BaseBlockStateProvider<GeneratorsBlockModelProvider> {

    public GeneratorsBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MekanismGenerators.MODID, existingFileHelper, GeneratorsBlockModelProvider::new);
    }

    @Override
    protected void registerStatesAndModels() {
        registerFluidBlockStates(GeneratorsFluids.FLUIDS.getAllFluids());
    }
}