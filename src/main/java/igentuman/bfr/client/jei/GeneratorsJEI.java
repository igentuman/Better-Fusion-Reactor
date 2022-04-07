package igentuman.bfr.client.jei;

import javax.annotation.Nonnull;
import mekanism.client.jei.MekanismJEI;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class GeneratorsJEI implements IModPlugin {

    private static final ResourceLocation FISSION = BetterFusionReactor.rl("fission");

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
        //registry.addRecipeCategories(new FissionReactorRecipeCategory(guiHelper, FISSION));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registry) {
      //  CatalystRegistryHelper.register(registry, FISSION, BfrBlocks.FISSION_REACTOR_CASING, BfrBlocks.FISSION_REACTOR_PORT,
        //      BfrBlocks.FISSION_REACTOR_LOGIC_ADAPTER, BfrBlocks.FISSION_FUEL_ASSEMBLY, BfrBlocks.CONTROL_ROD_ASSEMBLY);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
     //   registry.addRecipes(FissionReactorRecipeCategory.getFissionRecipes(), FISSION);
    }
}