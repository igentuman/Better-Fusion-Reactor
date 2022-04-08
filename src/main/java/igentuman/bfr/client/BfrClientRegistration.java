package igentuman.bfr.client;

import igentuman.bfr.client.gui.*;
import mekanism.client.ClientRegistrationUtil;
import igentuman.bfr.client.render.RenderFusionReactor;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.ContainerType;
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
         ClientRegistrationUtil.bindTileEntityRenderer(BfrTileEntityTypes.FUSION_REACTOR_CONTROLLER, RenderFusionReactor::new);

        //Block render layers
        ClientRegistrationUtil.setRenderLayer(RenderType.translucent(), BfrBlocks.LASER_FOCUS_MATRIX, BfrBlocks.REACTOR_GLASS);
    }

    @SubscribeEvent
    @SuppressWarnings("Convert2MethodRef")
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_CONTROLLER, GuiFusionReactorController::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_FUEL, GuiFusionReactorFuel::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_HEAT, GuiFusionReactorHeat::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_ADAPTER, GuiFusionReactorLogicAdapterGeneral::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_STATS, GuiFusionReactorStats::new);
        ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_EFFICIENCY, GuiFusionReactorEfficiency::new);
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
            return;
        }
        BfrSpecialColors.GUI_OBJECTS.parse(BetterFusionReactor.rl("textures/colormap/gui_objects.png"));
    }
}