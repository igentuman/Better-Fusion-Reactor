package igentuman.bfr.datagen.recipe.impl;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrItems;
import igentuman.bfr.datagen.recipe.ISubRecipeProvider;
import igentuman.bfr.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import igentuman.bfr.datagen.recipe.builder.ItemStackToItemStackWithTimeRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.Mekanism;
import mekanism.common.registration.impl.SlurryRegistryObject;
import mekanism.common.registries.MekanismGases;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismSlurries;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ore.OreType;
import mekanism.common.tags.MekanismTags;
import mekanism.common.util.EnumUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class IrradiatorRecipeProvider implements ISubRecipeProvider {

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {
        String basePath = "irradiating/";

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
              IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.OSMIUM)),
                BfrBlocks.ORE_BLOCKS.get("osmium").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "osmium"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.LEAD)),
                BfrBlocks.ORE_BLOCKS.get("lead").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "lead"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.TIN)),
                BfrBlocks.ORE_BLOCKS.get("tin").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "tin"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.URANIUM)),
                BfrBlocks.ORE_BLOCKS.get("uranium").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "uranium"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(Tags.Items.ORES_IRON),
                BfrBlocks.ORE_BLOCKS.get("iron").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "iron"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(Tags.Items.ORES_GOLD),
                BfrBlocks.ORE_BLOCKS.get("gold").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "gold"));

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(Tags.Items.ORES_COPPER),
                BfrBlocks.ORE_BLOCKS.get("copper").getItemStack(),
                200
        ).build(consumer, BetterFusionReactor.rl(basePath + "copper"));

        ItemStack waste = BfrItems.SOLIDIFIED_WASTE.getItemStack();
        waste.setCount(7);

        ItemStackToItemStackWithTimeRecipeBuilder.irradiating(
                IngredientCreatorAccess.item().from(waste),
                MekanismItems.POLONIUM_PELLET.getItemStack(),
                2000
        ).build(consumer, BetterFusionReactor.rl(basePath + "polonium_from_waste"));
    }
}