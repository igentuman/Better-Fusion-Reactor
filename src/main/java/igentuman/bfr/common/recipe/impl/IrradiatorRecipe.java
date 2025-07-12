package igentuman.bfr.common.recipe.impl;

import igentuman.bfr.common.recipe.ItemStackToItemStackWithTimeModRecipe;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;

import static igentuman.bfr.common.BetterFusionReactor.rl;

@NothingNullByDefault
public class IrradiatorRecipe extends ItemStackToItemStackWithTimeModRecipe {

    private static final Holder<Item> IRRADIATOR = DeferredHolder.create(Registries.ITEM, rl("irradiator"));

    public IrradiatorRecipe(ItemStackIngredient input, ItemStack output) {
        super(input, output, BfrRecipes.IRRADIATING.value());
    }

    @Override
    public RecipeSerializer<ItemStackToItemStackWithTimeModRecipe> getSerializer() {
        return BfrRecipes.IRRADIATING_SERIALIZER.value();
    }

    @Override
    public String getGroup() {
        return "irradiator";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(IRRADIATOR);
    }

    public int getTicks() {
        return 200; // Default processing time for the recipe
    }
}