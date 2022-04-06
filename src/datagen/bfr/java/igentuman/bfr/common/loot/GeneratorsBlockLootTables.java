package igentuman.bfr.common.loot;

import igentuman.common.loot.table.BaseBlockLootTables;
import igentuman.bfr.common.registries.GeneratorsBlocks;

public class GeneratorsBlockLootTables extends BaseBlockLootTables {

    @Override
    protected void addTables() {
        dropSelfWithContents(GeneratorsBlocks.BLOCKS.getAllBlocks());
    }
}