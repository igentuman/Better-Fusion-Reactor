package igentuman.bfr.common.loot;

import igentuman.common.loot.BaseLootProvider;
import igentuman.bfr.common.MekanismGenerators;
import net.minecraft.data.DataGenerator;

public class GeneratorsLootProvider extends BaseLootProvider {

    public GeneratorsLootProvider(DataGenerator gen) {
        super(gen, MekanismGenerators.MODID);
    }

    @Override
    protected GeneratorsBlockLootTables getBlockLootTable() {
        return new GeneratorsBlockLootTables();
    }
}