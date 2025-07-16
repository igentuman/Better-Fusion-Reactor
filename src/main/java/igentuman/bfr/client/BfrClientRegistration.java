package igentuman.bfr.client;

import igentuman.bfr.client.gui.*;
import igentuman.bfr.client.render.RenderFusionReactor;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import mekanism.client.ClientRegistrationUtil;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = BetterFusionReactor.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class BfrClientRegistration {

    private BfrClientRegistration() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BfrTileEntityTypes.FUSION_REACTOR_CONTROLLER.get(), RenderFusionReactor::new);
    }


    @SuppressWarnings("Convert2MethodRef")
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_CONTROLLER, GuiFusionReactorController::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_FUEL, GuiFusionReactorFuel::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_HEAT, GuiFusionReactorHeat::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_LOGIC_ADAPTER, GuiFusionReactorLogicAdapter::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_STATS, GuiFusionReactorStats::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_LOGIC_IN, GuiFusionReactorLogicAdapterInput::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_LOGIC_OUT, GuiFusionReactorLogicAdapterOutput::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.FUSION_REACTOR_EFFICIENCY, GuiFusionReactorEfficiency::new);
        ClientRegistrationUtil.registerScreen(event, BfrContainerTypes.IRRADIATOR, GuiIrradiator::new);
    }

    @SubscribeEvent
    public static void onStitch(TextureAtlasStitchedEvent event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
        //Reset any cached models now that the atlases are built
        BfrSpecialColors.GUI_OBJECTS.parse(BetterFusionReactor.rl("textures/colormap/gui_objects.png"));
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        ClientRegistrationUtil.registerBlockExtensions(event, BfrBlocks.BLOCKS);
    }
}