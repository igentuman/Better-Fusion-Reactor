package igentuman.bfr.common.datagen.recipe.builder;

import com.google.gson.JsonObject;
import igentuman.bfr.common.datagen.DataGenJsonConstants;
import mekanism.api.JsonConstants;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtendedSingleItemRecipeBuilder extends BaseRecipeBuilder<ExtendedSingleItemRecipeBuilder> {

    private final Ingredient ingredient;

    public ExtendedSingleItemRecipeBuilder(RecipeSerializer<?> serializer, Ingredient ingredient, ItemLike result, int count) {
        super(serializer, result, count);
        this.ingredient = ingredient;
    }

    public static ExtendedSingleItemRecipeBuilder stonecutting(Ingredient ingredient, ItemLike result) {
        return stonecutting(ingredient, result, 1);
    }

    public static ExtendedSingleItemRecipeBuilder stonecutting(Ingredient ingredient, ItemLike result, int count) {
        return new ExtendedSingleItemRecipeBuilder(RecipeSerializer.STONECUTTER, ingredient, result, count);
    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return new Result(id);
    }

    public class Result extends BaseRecipeResult {

        public Result(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.add(JsonConstants.INGREDIENT, ingredient.toJson());
        }

        @Override
        protected void serializeResult(JsonObject json) {
            json.addProperty(DataGenJsonConstants.RESULT, result.getRegistryName().toString());
            json.addProperty(JsonConstants.COUNT, count);
        }
    }
}