package igentuman.bfr.datagen;

import java.util.function.Consumer;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.datagen.recipe.BaseRecipeProvider;
import igentuman.bfr.datagen.recipe.builder.ExtendedShapedRecipeBuilder;
import igentuman.bfr.datagen.recipe.pattern.Pattern;
import igentuman.bfr.datagen.recipe.pattern.RecipePattern;
import igentuman.bfr.datagen.recipe.pattern.RecipePattern.TripleLine;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.tags.MekanismTags;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mekanism.generators.common.registries.GeneratorsItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;


@NothingNullByDefault
public class BfrRecipeProvider extends BaseRecipeProvider {

    private static final char GLASS_CHAR = 'G';
    private static final char FRAME_CHAR = 'F';

    public BfrRecipeProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, existingFileHelper, BetterFusionReactor.MODID);
    }

    @Override
    protected void addRecipes(Consumer<FinishedRecipe> consumer) {
        addFusionReactorRecipes(consumer);
    }

    private void addFusionReactorRecipes(Consumer<FinishedRecipe> consumer) {
        //Laser Focus Matrix
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.LASER_FOCUS_MATRIX, 2)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.EMPTY, GLASS_CHAR, Pattern.EMPTY),
                        TripleLine.of(GLASS_CHAR, Pattern.REDSTONE, GLASS_CHAR),
                        TripleLine.of(Pattern.EMPTY, GLASS_CHAR, Pattern.EMPTY))
                ).key(GLASS_CHAR, BfrBlocks.REACTOR_GLASS)
                .key(Pattern.REDSTONE, Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .build(consumer);
        //Frame
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.FUSION_REACTOR_FRAME, 4)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.ALLOY, Pattern.CONSTANT, Pattern.ALLOY),
                        TripleLine.of(Pattern.CONSTANT, Pattern.STEEL_CASING, Pattern.CONSTANT),
                        TripleLine.of(Pattern.ALLOY, Pattern.CONSTANT, Pattern.ALLOY))
                ).key(Pattern.STEEL_CASING, MekanismBlocks.STEEL_CASING)
                .key(Pattern.ALLOY, MekanismTags.Items.ALLOYS_ULTIMATE)
                .key(Pattern.CONSTANT, MekanismTags.Items.PELLETS_POLONIUM)
                .build(consumer, BetterFusionReactor.rl("reactor/frame"));
        //Glass
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.REACTOR_GLASS, 4)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.STEEL, Pattern.INGOT, Pattern.STEEL),
                        TripleLine.of(Pattern.INGOT, GLASS_CHAR, Pattern.INGOT),
                        TripleLine.of(Pattern.STEEL, Pattern.INGOT, Pattern.STEEL))
                ).key(GLASS_CHAR, Tags.Items.GLASS)
                .key(Pattern.INGOT, MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.LEAD))
                .key(Pattern.STEEL, MekanismItems.ENRICHED_IRON)
                .build(consumer, BetterFusionReactor.rl("reactor/glass"));
        //Port
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.FUSION_REACTOR_PORT, 2)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.EMPTY, FRAME_CHAR, Pattern.EMPTY),
                        TripleLine.of(FRAME_CHAR, Pattern.CIRCUIT, FRAME_CHAR),
                        TripleLine.of(Pattern.EMPTY, FRAME_CHAR, Pattern.EMPTY))
                ).key(Pattern.CIRCUIT, MekanismTags.Items.CIRCUITS_ULTIMATE)
                .key(FRAME_CHAR, BfrBlocks.FUSION_REACTOR_FRAME)
                .build(consumer, BetterFusionReactor.rl("reactor/port"));
        //Logic Adapter
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.EMPTY, Pattern.REDSTONE, Pattern.EMPTY),
                        TripleLine.of(Pattern.REDSTONE, FRAME_CHAR, Pattern.REDSTONE),
                        TripleLine.of(Pattern.EMPTY, Pattern.REDSTONE, Pattern.EMPTY))
                ).key(FRAME_CHAR, BfrBlocks.FUSION_REACTOR_FRAME)
                .key(Pattern.REDSTONE, Tags.Items.DUSTS_REDSTONE)
                .build(consumer, BetterFusionReactor.rl("reactor/logic_adapter"));
        //Controller
        ExtendedShapedRecipeBuilder.shapedRecipe(BfrBlocks.FUSION_REACTOR_CONTROLLER)
                .pattern(RecipePattern.createPattern(
                        TripleLine.of(Pattern.CIRCUIT, GLASS_CHAR, Pattern.CIRCUIT),
                        TripleLine.of(FRAME_CHAR, Pattern.TANK, FRAME_CHAR),
                        TripleLine.of(FRAME_CHAR, FRAME_CHAR, FRAME_CHAR))
                ).key(Pattern.CIRCUIT, MekanismTags.Items.CIRCUITS_ULTIMATE)
                .key(GLASS_CHAR, Tags.Items.GLASS_PANES)
                .key(FRAME_CHAR, BfrBlocks.FUSION_REACTOR_FRAME)
                .key(Pattern.TANK, MekanismBlocks.BASIC_CHEMICAL_TANK)
                .build(consumer, BetterFusionReactor.rl("reactor/controller"));
    }
}