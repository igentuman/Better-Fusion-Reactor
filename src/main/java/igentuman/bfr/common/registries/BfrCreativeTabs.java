package igentuman.bfr.common.registries;

import igentuman.bfr.common.BetterFusionReactor;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.Attributes.AttributeComparator;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class BfrCreativeTabs {

    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(BetterFusionReactor.MODID, BfrCreativeTabs::addToExistingTabs);


    private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {

        } else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            for (Holder<Item> item : BfrBlocks.BLOCKS.getSecondaryEntries()) {
                if (item.value() instanceof BlockItem blockItem && Attribute.has(blockItem.getBlock(), AttributeComparator.class)) {
                    CreativeTabDeferredRegister.addToDisplay(event, item);
                }
            }
        }
    }
}