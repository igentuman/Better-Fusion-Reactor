package igentuman.bfr.common;

import igentuman.bfr.common.config.BetterFusionReactorConfig;
import igentuman.bfr.common.content.fusion.BFReactorCache;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.content.fusion.BFReactorValidator;
import igentuman.bfr.common.events.GameEvents;
import igentuman.bfr.common.network.BfrPacketHandler;
import igentuman.bfr.common.registries.*;
import igentuman.bfr.common.registries.BfrBuilders.FusionReactorBuilder;
import mekanism.common.Mekanism;
import mekanism.common.base.IModModule;
import mekanism.common.command.builders.BuildCommand;
import mekanism.common.lib.Version;
import mekanism.common.lib.multiblock.MultiblockManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(BetterFusionReactor.MODID)
public class BetterFusionReactor implements IModModule {

    public static final String MODID = "bfr";

    public static BetterFusionReactor instance;

    /**
     * MekanismGenerators version number
     */
    public final Version versionNumber;
    /**
     * Mekanism Generators Packet Pipeline
     */
    private final BfrPacketHandler packetHandler;

    public static final MultiblockManager<BFReactorMultiblockData> fusionReactorManager = new MultiblockManager<>("fusionReactor", BFReactorCache::new, BFReactorValidator::new);

    public BetterFusionReactor(ModContainer modContainer, IEventBus modEventBus) {
        Mekanism.addModule(instance = this);
        versionNumber = new Version(modContainer);
        BetterFusionReactorConfig.registerConfigs(modContainer);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(BetterFusionReactorConfig::onConfigLoad);

        BfrDataComponents.DATA_COMPONENTS.register(modEventBus);
        BfrItems.ITEMS.register(modEventBus);
        BfrBlocks.BLOCKS.register(modEventBus);
        BfrCreativeTabs.CREATIVE_TABS.register(modEventBus);
        BfrContainerTypes.CONTAINER_TYPES.register(modEventBus);
        BfrTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        BfrChemicals.CHEMICALS.register(modEventBus);
        packetHandler = new BfrPacketHandler(modEventBus, versionNumber);
        GameEvents.GAME_EVENTS.register(modEventBus);

        BfrRecipes.init();
    }

    public static BfrPacketHandler packetHandler() {
        return instance.packetHandler;
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BuildCommand.register("better_fusion", BfrLang.FUSION_REACTOR, new FusionReactorBuilder());
        });

        //Finalization
        Mekanism.logger.info("Loaded 'Mekanism: BFR' module.");
    }

    @Override
    public Version getVersion() {
        return versionNumber;
    }

    @Override
    public String getName() {
        return "BFR";
    }
}
