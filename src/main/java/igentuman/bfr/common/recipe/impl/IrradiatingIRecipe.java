package igentuman.bfr.common.recipe.impl;

import igentuman.bfr.common.config.BetterFusionReactorConfig;
import igentuman.bfr.common.recipe.ItemStackToItemStackWithTimeModRecipe;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrRecipes;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

@NothingNullByDefault
public class IrradiatingIRecipe extends ItemStackToItemStackWithTimeModRecipe {

    public IrradiatingIRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output) {
        super(id, input, output, 0);
    }

    public IrradiatingIRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output, int ticks) {
        super(id, input, output, ticks);
    }

    @Override
    public RecipeType<ItemStackToItemStackRecipe> getType() {
        return BfrRecipes.IRRADIATING.get();
    }

    public int getTicks()
    {
        if(ticks == 0)
            return BetterFusionReactorConfig.bfr.irradiatorBaseProcessTicks.get();
        else
            return ticks;
    }
    @Override
    public RecipeSerializer<ItemStackToItemStackWithTimeModRecipe> getSerializer() {
        return BfrRecipes.IRRADIATING_SERIALIZER.get();
    }

    @Override
    public String getGroup() {
        return BfrBlocks.IRRADIATOR.getName();
    }

    @Override
    public ItemStack getToastSymbol() {
        return BfrBlocks.IRRADIATOR.getItemStack();
    }
}