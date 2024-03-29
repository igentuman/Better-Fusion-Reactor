package igentuman.bfr.datagen.recipe.builder;

import com.google.gson.JsonObject;
import igentuman.bfr.datagen.DataGenJsonConstants;
import mekanism.api.JsonConstants;
import mekanism.common.util.RegistryUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtendedCookingRecipeBuilder extends BaseRecipeBuilder<ExtendedCookingRecipeBuilder> {

    private final Ingredient ingredient;
    private final int cookingTime;
    private float experience;

    private ExtendedCookingRecipeBuilder(SimpleCookingSerializer<?> serializer, ItemLike result, int count, Ingredient ingredient, int cookingTime) {
        super(serializer, result, count);
        this.ingredient = ingredient;
        this.cookingTime = cookingTime;
    }

    public ExtendedCookingRecipeBuilder experience(float experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative.");
        }
        this.experience = experience;
        return this;
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
            json.addProperty(DataGenJsonConstants.COOKING_TIME, cookingTime);
            if (experience > 0) {
                json.addProperty(DataGenJsonConstants.EXPERIENCE, experience);
            }
        }

        @Override
        protected void serializeResult(JsonObject json) {
            if (count == 1) {
                json.addProperty(DataGenJsonConstants.RESULT, RegistryUtils.getName(result).toString());
            } else {
                super.serializeResult(json);
            }
        }
    }
}