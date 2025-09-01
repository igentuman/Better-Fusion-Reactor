package igentuman.bfr.common.recipe;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mekanism.api.SerializationConstants;
import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public record ItemToItemWithTimeModRecipeSerializer<RECIPE extends ItemStackToItemStackWithTimeModRecipe>(
        MapCodec<RECIPE> codec, StreamCodec<RegistryFriendlyByteBuf, RECIPE> streamCodec) implements RecipeSerializer<RECIPE> {

    public static <RECIPE extends ItemStackToItemStackWithTimeModRecipe> MekanismRecipeSerializer<RECIPE> itemToItemWithTicks(Function3<ItemStackIngredient, ItemStack, Integer, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.<RECIPE>mapCodec(instance -> instance.group(
                ItemStackIngredient.CODEC.fieldOf(SerializationConstants.INPUT).forGetter(recipe -> recipe.getInput()),
                ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT).forGetter(recipe -> recipe.getOutputRaw()),
                Codec.INT.fieldOf("ticks").forGetter(recipe -> recipe.ticks)
        ).apply(instance, factory)), StreamCodec.composite(
                ItemStackIngredient.STREAM_CODEC, recipe -> recipe.getInput(),
                ItemStack.STREAM_CODEC, recipe -> recipe.getOutputRaw(),
                ByteBufCodecs.INT, recipe -> recipe.ticks,
                factory
        ));
    }
    /*@NotNull
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
    }*/

}
