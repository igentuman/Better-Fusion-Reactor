package igentuman.bfr.client;

import mekanism.client.model.BaseBlockModelProvider;
import igentuman.bfr.common.MekanismGenerators;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrBlockModelProvider extends BaseBlockModelProvider {

    public BfrBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MekanismGenerators.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}