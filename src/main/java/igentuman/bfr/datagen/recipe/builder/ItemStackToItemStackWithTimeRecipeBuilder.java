package igentuman.bfr.datagen.recipe.builder;

import com.google.gson.JsonObject;
import mekanism.api.JsonConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@NothingNullByDefault
public class ItemStackToItemStackWithTimeRecipeBuilder extends MekanismRecipeBuilder<ItemStackToItemStackWithTimeRecipeBuilder> {

    private final ItemStackIngredient input;
    private final ItemStack output;
    private final int  ticks;

    protected ItemStackToItemStackWithTimeRecipeBuilder(ItemStackIngredient input, ItemStack output, ResourceLocation serializerName, int ticks) {
        super(serializerName);
        this.input = input;
        this.output = output;
        this.ticks = ticks;
    }

    /**
     * Creates a Crushing recipe builder.
     *
     * @param input  Input.
     * @param output Output.
     */
    public static ItemStackToItemStackWithTimeRecipeBuilder irradiating(ItemStackIngredient input, ItemStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This irradiating recipe requires a non empty item output.");
        }
        return new ItemStackToItemStackWithTimeRecipeBuilder(input, output, mekSerializer("irradiating"), 0);
    }

    public static ItemStackToItemStackWithTimeRecipeBuilder irradiating(ItemStackIngredient input, ItemStack output, int ticks) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This irradiating recipe requires a non empty item output.");
        }
        return new ItemStackToItemStackWithTimeRecipeBuilder(input, output, mekSerializer("irradiating"), ticks);
    }

    @Override
    protected ItemStackToItemStackRecipeResult getResult(ResourceLocation id) {
        return new ItemStackToItemStackRecipeResult(id);
    }

    /**
     * Builds this recipe using the output item's name as the recipe name.
     *
     * @param consumer Finished Recipe Consumer.
     */
    public void build(Consumer<FinishedRecipe> consumer) {
        build(consumer, output.getItem());
    }

    public class ItemStackToItemStackRecipeResult extends RecipeResult {

        protected ItemStackToItemStackRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            json.add(JsonConstants.INPUT, input.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeItemStack(output));
            json.addProperty("ticks", ticks);
        }
    }
}