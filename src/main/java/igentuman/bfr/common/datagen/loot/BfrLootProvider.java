package igentuman.bfr.common.datagen.loot;

import igentuman.bfr.common.BetterFusionReactor;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.data.DataGenerator;

public class BfrLootProvider extends BaseLootProvider {

    public BfrLootProvider(DataGenerator gen) {
        super(gen, BetterFusionReactor.MODID);
    }

    @Override
    protected BfrBlockLootTables getBlockLootTable() {
        return new BfrBlockLootTables();
    }
}