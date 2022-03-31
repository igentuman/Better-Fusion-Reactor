package igentuman.bfr.common.integration.jei;

import mekanism.generators.common.GeneratorsBlocks;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@JEIPlugin
public class BFRPluginJEI implements IModPlugin {
    public IJeiHelpers jeiHelpers;

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.Reactor),1, 0));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.Reactor),1, 1));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.Reactor),1, 2));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.Reactor),1, 3));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.ReactorGlass),1, 0));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Item.getItemFromBlock(GeneratorsBlocks.ReactorGlass),1, 1));
    }
}
