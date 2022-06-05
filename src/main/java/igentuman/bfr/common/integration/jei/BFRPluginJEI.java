package igentuman.bfr.common.integration.jei;

import igentuman.bfr.common.recipes.BFRRecipes;
import igentuman.bfr.common.recipes.ReactorCoolantRecipe;
import igentuman.bfr.common.recipes.StackHelper;
import mekanism.generators.common.GeneratorsBlocks;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static igentuman.bfr.common.BFR.MODID;

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

        registry.handleRecipes(ReactorCoolantRecipe.class, ReactorCoolantRecipeCategory.Wrapper::new, MODID+"_reactor_coolant");
        registry.addRecipeCatalyst(StackHelper.getStackFromString(MODID + ":reactor",0),MODID+"_reactor_coolant");
        registry.addRecipeCatalyst(StackHelper.getStackFromString(MODID + ":reactor",2),MODID+"_reactor_coolant");
        registry.addRecipes(BFRRecipes.REACTOR_COOLANT.getAll(), MODID+"_reactor_coolant");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        final IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new ReactorCoolantRecipeCategory(guiHelper));
    }
}
