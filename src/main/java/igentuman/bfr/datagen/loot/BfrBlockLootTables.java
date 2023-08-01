package igentuman.bfr.datagen.loot;

import igentuman.bfr.common.registries.BfrBlocks;

public class BfrBlockLootTables extends BaseBlockLootTables {

    @Override
    protected void generate() {
        dropSelfWithContents(BfrBlocks.BLOCKS.getAllBlocks());
    }
}