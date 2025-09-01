package igentuman.bfr.common.registries;

import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.Attributes.AttributeComparator;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static igentuman.bfr.common.registries.BfrBlocks.IRRADIATOR;
import static igentuman.bfr.common.registries.BfrItems.SOLIDIFIED_WASTE;
import static mekanism.generators.common.registries.GeneratorsCreativeTabs.GENERATORS;

public class BfrCreativeTabs {

    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismGenerators.MODID, BfrCreativeTabs::addToExistingTabs);


    private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (tabKey == CreativeModeTabs.BUILDING_BLOCKS) {
            CreativeTabDeferredRegister.addToDisplay(event, BfrBlocks.ORE_BLOCKS.get("tin"), BfrBlocks.ORE_BLOCKS.get("osmium"), BfrBlocks.ORE_BLOCKS.get("uranium"), BfrBlocks.ORE_BLOCKS.get("iron"), BfrBlocks.ORE_BLOCKS.get("lead"), BfrBlocks.ORE_BLOCKS.get("gold"), BfrBlocks.ORE_BLOCKS.get("copper"));
        }
        if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            CreativeTabDeferredRegister.addToDisplay(event, BfrBlocks.FUSION_REACTOR_CONTROLLER,  BfrBlocks.FUSION_REACTOR_PORT, BfrBlocks.FUSION_REACTOR_FRAME, BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.LASER_FOCUS_MATRIX, IRRADIATOR);
        } else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            for (Holder<Item> item : BfrBlocks.BLOCKS.getSecondaryEntries()) {
                if (item.value() instanceof BlockItem blockItem && Attribute.has(blockItem.getBlock(), AttributeComparator.class)) {
                    CreativeTabDeferredRegister.addToDisplay(event, item);
                }
            }
        } else if(tabKey == GENERATORS.getKey()) {
            CreativeTabDeferredRegister.addToDisplay(event, BfrBlocks.FUSION_REACTOR_CONTROLLER,  BfrBlocks.FUSION_REACTOR_PORT, BfrBlocks.FUSION_REACTOR_FRAME, BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.LASER_FOCUS_MATRIX, IRRADIATOR);
        } else if(tabKey == CreativeModeTabs.INGREDIENTS) {
            CreativeTabDeferredRegister.addToDisplay(event, SOLIDIFIED_WASTE);
        }
    }
}