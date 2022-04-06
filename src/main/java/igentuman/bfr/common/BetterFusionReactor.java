package igentuman.bfr.common;

import mekanism.api.MekanismIMC;
import mekanism.common.Mekanism;
import mekanism.common.base.IModModule;
import mekanism.common.command.builders.BuildCommand;
import mekanism.common.config.MekanismConfig;
import mekanism.common.config.MekanismModConfig;
import mekanism.common.lib.Version;
import mekanism.common.lib.multiblock.MultiblockManager;
import mekanism.common.registries.MekanismGases;
import igentuman.bfr.common.config.BetterFusionReactorConfig;
import igentuman.bfr.common.content.fusion.FusionReactorCache;
import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.content.fusion.FusionReactorValidator;
import igentuman.bfr.common.network.BfrPacketHandler;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrBuilders.FusionReactorBuilder;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.registries.BfrFluids;
import igentuman.bfr.common.registries.BfrGases;
import igentuman.bfr.common.registries.BfrModules;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterFusionReactor.MODID)
public class BetterFusionReactor implements IModModule {

    public static final String MODID = "bfr";

    public static BetterFusionReactor instance;

    /**
     * BetterFusionReactor version number
     */
    public final Version versionNumber;
    /**
     * Mekanism Generators Packet Pipeline
     */
    public static final BfrPacketHandler packetHandler = new BfrPacketHandler();

    public static final MultiblockManager<FusionReactorMultiblockData> fusionReactorManager = new MultiblockManager<>("fusionReactor", FusionReactorCache::new, FusionReactorValidator::new);

    public BetterFusionReactor() {
        Mekanism.modulesLoaded.add(instance = this);
        BetterFusionReactorConfig.registerConfigs(ModLoadingContext.get());
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onConfigLoad);
        modEventBus.addListener(this::imcQueue);

        BfrBlocks.BLOCKS.register(modEventBus);
        BfrFluids.FLUIDS.register(modEventBus);
        BfrContainerTypes.CONTAINER_TYPES.register(modEventBus);
        BfrTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        BfrGases.GASES.register(modEventBus);
        BfrModules.MODULES.register(modEventBus);
        //Set our version number to match the mods.toml file, which matches the one in our build.gradle
        versionNumber = new Version(ModLoadingContext.get().getActiveContainer());
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(BetterFusionReactor.MODID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        BuildCommand.register("fusion", BfrLang.FUSION_REACTOR, new FusionReactorBuilder());

        packetHandler.initialize();

        //Finalization
        Mekanism.logger.info("Loaded 'Mekanism Generators' module.");
    }

    private void imcQueue(InterModEnqueueEvent event) {
        MekanismIMC.addMekaSuitPantsModules(BfrModules.GEOTHERMAL_GENERATOR_UNIT);
    }

    @Override
    public Version getVersion() {
        return versionNumber;
    }

    @Override
    public String getName() {
        return "Better Fusion Reactor";
    }

    @Override
    public void resetClient() {

    }

    private void onConfigLoad(ModConfig.ModConfigEvent configEvent) {
        //Note: We listen to both the initial load and the reload, to make sure that we fix any accidentally
        // cached values from calls before the initial loading
        ModConfig config = configEvent.getConfig();
        //Make sure it is for the same modid as us
        if (config.getModId().equals(MODID) && config instanceof MekanismModConfig) {
            ((MekanismModConfig) config).clearCache();
        }
    }
}