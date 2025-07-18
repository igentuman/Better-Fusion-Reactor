package igentuman.bfr.common.recipe;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class ItemStackToItemStackWithTimeModRecipe extends BasicItemStackToItemStackRecipe {
    public int ticks;
    /**
     * @param input  Input.
     * @param output Output.
     * @param recipeType Recipe type.
     */
    public ItemStackToItemStackWithTimeModRecipe(ItemStackIngredient input, ItemStack output, RecipeType<ItemStackToItemStackRecipe> recipeType) {
        super(input, output, recipeType);
        ticks = 200;
    }

    public ItemStackToItemStackWithTimeModRecipe(ItemStackIngredient input, ItemStack output, RecipeType<ItemStackToItemStackRecipe> recipeType, int ticks) {
        super(input, output, recipeType);
        this.ticks = ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
