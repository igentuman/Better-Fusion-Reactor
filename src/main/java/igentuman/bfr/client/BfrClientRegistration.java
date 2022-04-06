package igentuman.bfr.client;

import mekanism.client.ClientRegistration;
import mekanism.client.ClientRegistrationUtil;
import mekanism.client.model.baked.ExtensionBakedModel.TransformedBakedModel;
import mekanism.client.render.lib.QuadTransformation;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.FluidRegistryObject;
import igentuman.bfr.client.gui.GuiBioGenerator;
import igentuman.bfr.client.gui.GuiFissionReactor;
import igentuman.bfr.client.gui.GuiFissionReactorLogicAdapter;
import igentuman.bfr.client.gui.GuiFissionReactorStats;
import igentuman.bfr.client.gui.GuiFusionReactorController;
import igentuman.bfr.client.gui.GuiFusionReactorFuel;
import igentuman.bfr.client.gui.GuiFusionReactorHeat;
import igentuman.bfr.client.gui.GuiFusionReactorLogicAdapter;
import igentuman.bfr.client.gui.GuiFusionReactorStats;
import igentuman.bfr.client.gui.GuiGasGenerator;
import igentuman.bfr.client.gui.GuiHeatGenerator;
import igentuman.bfr.client.gui.GuiIndustrialTurbine;
import igentuman.bfr.client.gui.GuiSolarGenerator;
import igentuman.bfr.client.gui.GuiTurbineStats;
import igentuman.bfr.client.gui.GuiWindGenerator;
import igentuman.bfr.client.render.RenderFusionReactor;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.registries.BfrFluids;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import igentuman.bfr.common.tile.TileEntityAdvancedSolarGenerator;
import igentuman.bfr.common.tile.TileEntitySolarGenerator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BetterFusionReactor.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BfrClientRegistration {

    private BfrClientRegistration() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.BIO_GENERATOR, RenderBioGenerator::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.FUSION_REACTOR_CONTROLLER, RenderFusionReactor::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.FISSION_REACTOR_CASING, RenderFissionReactor::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.FISSION_REACTOR_PORT, RenderFissionReactor::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.FISSION_REACTOR_LOGIC_ADAPTER, RenderFissionReactor::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.TURBINE_CASING, RenderIndustrialTurbine::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.TURBINE_ROTOR, RenderTurbineRotor::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.TURBINE_VALVE, RenderIndustrialTurbine::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.TURBINE_VENT, RenderIndustrialTurbine::new);
        ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.WIND_GENERATOR, RenderWindGenerator::new);

        //Block render layers
        ClientRegistrationUtil.setRenderLayer(RenderType.translucent(), BfrBlocks.LASER_FOCUS_MATRIX, BfrBlocks.REACTOR_GLASS);
        ClientRegistrationUtil.setRenderLayer(renderType -> renderType == RenderType.solid() || renderType == RenderType.translucent(),
              BfrBlocks.BIO_GENERATOR, BfrBlocks.HEAT_GENERATOR);
        //Fluids (translucent)
        for (FluidRegistryObject<?, ?, ?, ?> fluidRO : BfrFluids.FLUIDS.getAllFluids()) {
            ClientRegistrationUtil.setRenderLayer(RenderType.translucent(), fluidRO);
        }

        // adv solar gen requires to be translated up 1 block, so handle the model separately
        ClientRegistration.addCustomModel(BfrBlocks.ADVANCED_SOLAR_GENERATOR, (orig, evt) -> new TransformedBakedModel<Void>(orig,
              QuadTransformation.translate(new Vector3d(0, 1, 0))));
    }

    @SubscribeEvent
    @SuppressWarnings("Convert2MethodRef")
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.BIO_GENERATOR, GuiBioGenerator::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.GAS_BURNING_GENERATOR, GuiGasGenerator::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.HEAT_GENERATOR, GuiHeatGenerator::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.INDUSTRIAL_TURBINE, GuiIndustrialTurbine::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FISSION_REACTOR, GuiFissionReactor::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FISSION_REACTOR_STATS, GuiFissionReactorStats::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FISSION_REACTOR_LOGIC_ADAPTER, GuiFissionReactorLogicAdapter::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_CONTROLLER, GuiFusionReactorController::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_FUEL, GuiFusionReactorFuel::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_HEAT, GuiFusionReactorHeat::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_ADAPTER, GuiFusionReactorLogicAdapter::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_STATS, GuiFusionReactorStats::new);
        // for some reason java is unable to infer the types with this generics structure, so we include constructor signature ourselves
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.SOLAR_GENERATOR, (MekanismTileContainer<TileEntitySolarGenerator> container, PlayerInventory inv, ITextComponent title) -> new GuiSolarGenerator<>(container, inv, title));
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.ADVANCED_SOLAR_GENERATOR, (MekanismTileContainer<TileEntityAdvancedSolarGenerator> container, PlayerInventory inv, ITextComponent title) -> new GuiSolarGenerator<>(container, inv, title));
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.TURBINE_STATS, GuiTurbineStats::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.WIND_GENERATOR, GuiWindGenerator::new);
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
            return;
        }
        RenderBioGenerator.resetCachedModels();
        RenderFissionReactor.resetCachedModels();
        BfrSpecialColors.GUI_OBJECTS.parse(BetterFusionReactor.rl("textures/colormap/gui_objects.png"));
    }
}