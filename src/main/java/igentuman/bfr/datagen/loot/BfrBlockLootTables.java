package igentuman.bfr.datagen.loot;

import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import static net.minecraft.world.item.Items.*;

public class BfrBlockLootTables extends BaseBlockLootTables {

    protected void generate() {
        add(block -> droppingWithFortuneOrRandomly(block, MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.TIN), UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("tin"));
        add(block -> droppingWithFortuneOrRandomly(block, MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.OSMIUM), UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("osmium"));
        add(block -> droppingWithFortuneOrRandomly(block, MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.URANIUM), UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("uranium"));
        add(block -> droppingWithFortuneOrRandomly(block, MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.LEAD), UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("lead"));
        add(block -> droppingWithFortuneOrRandomly(block, RAW_GOLD, UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("gold"));
        add(block -> droppingWithFortuneOrRandomly(block, RAW_IRON, UniformGenerator.between(1, 4)), BfrBlocks.ORE_BLOCKS.get("iron"));
        add(block -> droppingWithFortuneOrRandomly(block, RAW_COPPER, UniformGenerator.between(1, 8)), BfrBlocks.ORE_BLOCKS.get("copper"));
        dropSelfWithContents(BfrBlocks.BLOCKS.getAllBlocks());
    }
}