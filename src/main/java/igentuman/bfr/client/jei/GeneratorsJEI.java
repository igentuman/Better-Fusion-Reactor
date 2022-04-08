package igentuman.bfr.client.jei;

import javax.annotation.Nonnull;

import mekanism.client.jei.CatalystRegistryHelper;
import mekanism.client.jei.MekanismJEI;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.generators.common.registries.GeneratorsBlockTypes;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mekanism.generators.common.registries.GeneratorsItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@JeiPlugin
public class GeneratorsJEI implements IModPlugin {

    private static final ResourceLocation FUSION = BetterFusionReactor.rl("fusion");

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return BetterFusionReactor.rl("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registry) {
        MekanismJEI.registerItemSubtypes(registry, BfrBlocks.BLOCKS.getAllBlocks());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new FusionReactorRecipeCategory(guiHelper, FUSION));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, FUSION,
        BfrBlocks.FUSION_REACTOR_CONTROLLER, BfrBlocks.FUSION_REACTOR_PORT);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
      registry.addRecipes(FusionReactorRecipeCategory.getFusionRecipes(), FUSION);

        Collection<ItemStack> collection = Arrays.asList(
                GeneratorsBlocks.LASER_FOCUS_MATRIX,
                GeneratorsBlocks.FUSION_REACTOR_CONTROLLER,
                GeneratorsBlocks.FUSION_REACTOR_FRAME,
                GeneratorsBlocks.FUSION_REACTOR_PORT,
                GeneratorsBlocks.FUSION_REACTOR_LOGIC_ADAPTER,
                GeneratorsBlocks.REACTOR_GLASS
        ).stream().map(ItemStack::new).collect(Collectors.toList());

        registry.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, collection);
    }
}