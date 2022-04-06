package igentuman.bfr.client;

import igentuman.bfr.common.BetterFusionReactor;
import mekanism.client.state.BaseBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrBlockStateProvider extends BaseBlockStateProvider<igentuman.bfr.client.BfrBlockModelProvider> {

    public BfrBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BetterFusionReactor.MODID, existingFileHelper, igentuman.bfr.client.BfrBlockModelProvider::new);
    }
}