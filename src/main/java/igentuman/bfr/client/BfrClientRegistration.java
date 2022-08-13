package igentuman.bfr.client;

import igentuman.bfr.client.gui.*;
import igentuman.bfr.client.render.RenderFusionReactor;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import mekanism.client.ClientRegistrationUtil;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrContainerTypes;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = BetterFusionReactor.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerContainers(RegisterEvent event) {
        event.register(Registry.MENU_REGISTRY, helper -> {
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_CONTROLLER, GuiFusionReactorController::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_FUEL, GuiFusionReactorFuel::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_HEAT, GuiFusionReactorHeat::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_ADAPTER, GuiFusionReactorLogicAdapterGeneral::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_STATS, GuiFusionReactorStats::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_GENERAL, GuiFusionReactorLogicAdapterGeneral::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_IN, GuiFusionReactorLogicAdapterInput::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_LOGIC_OUT, GuiFusionReactorLogicAdapterOutput::new);
            ClientRegistrationUtil.registerScreen(BfrContainerTypes.FUSION_REACTOR_EFFICIENCY, GuiFusionReactorEfficiency::new);
        });
    }
}