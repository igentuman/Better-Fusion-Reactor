package igentuman.bfr.datagen.loot;

import igentuman.bfr.common.BetterFusionReactor;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class BfrLootProvider extends BaseLootProvider {

    public BfrLootProvider(PackOutput gen) {
        super(gen, List.of(
                new SubProviderEntry(BfrBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}