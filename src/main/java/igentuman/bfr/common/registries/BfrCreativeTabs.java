package igentuman.bfr.common.registries;

import mekanism.api.providers.IBlockProvider;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.Attributes.AttributeComparator;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mekanism.generators.common.registries.GeneratorsFluids;
import mekanism.generators.common.registries.GeneratorsItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import static mekanism.generators.common.registries.GeneratorsCreativeTabs.GENERATORS;


public class BfrCreativeTabs {

    public static final CreativeTabDeferredRegister BFR_TAB = new CreativeTabDeferredRegister(MekanismGenerators.MODID, BfrCreativeTabs::addToExistingTabs);

    private static void addToExistingTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
         if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
             CreativeTabDeferredRegister.addToDisplay(event, BfrBlocks.FUSION_REACTOR_CONTROLLER,  BfrBlocks.FUSION_REACTOR_PORT, BfrBlocks.FUSION_REACTOR_FRAME, BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.LASER_FOCUS_MATRIX, BfrBlocks.IRRADIATOR);
        } else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            for (IBlockProvider block : BfrBlocks.BLOCKS.getAllBlocks()) {
                if (Attribute.has(block.getBlock(), AttributeComparator.class)) {
                    CreativeTabDeferredRegister.addToDisplay(event, block);
                }
            }
        }  else if (tabKey == CreativeModeTabs.INGREDIENTS) {
             CreativeTabDeferredRegister.addToDisplay(event, BfrItems.SOLIDIFIED_WASTE);
             for(IBlockProvider block : BfrBlocks.ORE_BLOCKS.values()) {
                 CreativeTabDeferredRegister.addToDisplay(event, block);
             }
         } else if( tabKey == GENERATORS.key()) {
             CreativeTabDeferredRegister.addToDisplay(event, BfrBlocks.FUSION_REACTOR_CONTROLLER,  BfrBlocks.FUSION_REACTOR_PORT, BfrBlocks.FUSION_REACTOR_FRAME, BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.LASER_FOCUS_MATRIX, BfrBlocks.IRRADIATOR);
        }
    }
}