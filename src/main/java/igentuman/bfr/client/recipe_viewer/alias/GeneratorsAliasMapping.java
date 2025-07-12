package igentuman.bfr.client.recipe_viewer.alias;

import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import mekanism.client.recipe_viewer.alias.IAliasMapping;
import mekanism.client.recipe_viewer.alias.RVAliasHelper;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GeneratorsAliasMapping implements IAliasMapping {

    @Override
    public <ITEM, FLUID, CHEMICAL> void addAliases(RVAliasHelper<ITEM, FLUID, CHEMICAL> rv) {
        addMultiblockAliases(rv);
        rv.addModuleAliases(BfrItems.ITEMS);
    }


    private <ITEM, FLUID, CHEMICAL> void addMultiblockAliases(RVAliasHelper<ITEM, FLUID, CHEMICAL> rv) {

        rv.addItemAliases(List.of(
              new ItemStack(BfrBlocks.FUSION_REACTOR_CONTROLLER),
              new ItemStack(BfrBlocks.FUSION_REACTOR_FRAME),
              new ItemStack(BfrBlocks.FUSION_REACTOR_PORT),
              new ItemStack(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER),
              new ItemStack(BfrBlocks.LASER_FOCUS_MATRIX)
        ), GeneratorsAliases.FUSION_COMPONENT);
    }
}