package igentuman.bfr.common.datagen.loot;

import igentuman.bfr.common.registries.BfrBlocks;

public class BfrBlockLootTables extends BaseBlockLootTables {

    @Override
    protected void addTables() {
        dropSelfWithContents(BfrBlocks.BLOCKS.getAllBlocks());
    }
}