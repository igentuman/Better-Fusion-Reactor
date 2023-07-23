package igentuman.bfr.common.recipe;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class ItemStackToItemStackWithTimeModRecipe extends ItemStackToItemStackRecipe {
    public int ticks;
    /**
     * @param id     Recipe name.
     * @param input  Input.
     * @param output Output.
     */
    public ItemStackToItemStackWithTimeModRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output) {
        super(id, input, output);
        ticks = 0;
    }

    public ItemStackToItemStackWithTimeModRecipe(ResourceLocation id, ItemStackIngredient input, ItemStack output, int ticks) {
        super(id, input, output);
        this.ticks = ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
