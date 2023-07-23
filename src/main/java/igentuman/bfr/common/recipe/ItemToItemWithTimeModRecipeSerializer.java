package igentuman.bfr.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.ItemStackToItemStackRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemToItemWithTimeModRecipeSerializer<RECIPE extends ItemStackToItemStackWithTimeModRecipe> extends ItemStackToItemStackRecipeSerializer<RECIPE> {
    private final IFactory<RECIPE> factory;

        public ItemToItemWithTimeModRecipeSerializer(IFactory<RECIPE> factory) {
            super(factory);
            this.factory = factory;
        }

    @NotNull
    @Override
    public RECIPE fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
        JsonElement input = GsonHelper.isArrayNode(json, JsonConstants.INPUT) ? GsonHelper.getAsJsonArray(json, JsonConstants.INPUT) :
                GsonHelper.getAsJsonObject(json, JsonConstants.INPUT);
        ItemStackIngredient inputIngredient = IngredientCreatorAccess.item().deserialize(input);
        ItemStack output = SerializerHelper.getItemStack(json, JsonConstants.OUTPUT);
        if (output.isEmpty()) {
            throw new JsonSyntaxException("Recipe output must not be empty.");
        }
        int ticks = GsonHelper.getAsInt(json, "ticks");
        ItemStackToItemStackRecipe recipe = this.factory.create(recipeId, inputIngredient, output);
        ((ItemStackToItemStackWithTimeModRecipe) recipe).setTicks(ticks);
        return (RECIPE) recipe;
    }

}
