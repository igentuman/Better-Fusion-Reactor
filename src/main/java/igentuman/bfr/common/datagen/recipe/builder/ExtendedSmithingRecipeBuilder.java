package igentuman.bfr.common.datagen.recipe.builder;

import com.google.gson.JsonObject;
import igentuman.bfr.common.datagen.DataGenJsonConstants;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtendedSmithingRecipeBuilder extends BaseRecipeBuilder<ExtendedSmithingRecipeBuilder> {

    private final Ingredient ingredient;
    private final Ingredient upgradeIngredient;

    public ExtendedSmithingRecipeBuilder(Ingredient ingredient, Ingredient upgradeIngredient, IItemProvider result) {
        super(IRecipeSerializer.SMITHING, result, 1);
        this.ingredient = ingredient;
        this.upgradeIngredient = upgradeIngredient;
    }

    public static ExtendedSmithingRecipeBuilder smithing(IItemProvider ingredient, IItemProvider upgradeIngredient, IItemProvider result) {
        return smithing(Ingredient.of(ingredient), Ingredient.of(upgradeIngredient), result);
    }

    public static ExtendedSmithingRecipeBuilder smithing(Ingredient ingredient, Ingredient upgradeIngredient, IItemProvider result) {
        return new ExtendedSmithingRecipeBuilder(ingredient, upgradeIngredient, result);
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
            json.add(DataGenJsonConstants.BASE, ingredient.toJson());
            json.add(DataGenJsonConstants.ADDITION, upgradeIngredient.toJson());
        }
    }
}