package igentuman.bfr.common;

import igentuman.bfr.common.fixers.GeneratorTEFixer;
import io.netty.buffer.ByteBuf;
import mekanism.common.Mekanism;
import mekanism.common.Version;
import mekanism.common.base.IModule;
import mekanism.common.config.MekanismConfig;
import mekanism.common.fixers.MekanismDataFixers.MekFixers;
import mekanism.common.network.PacketSimpleGui;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = BFR.MODID, useMetadata = true, guiFactory = "igentuman.bfr.client.gui.BFRGuiFactory")
@Mod.EventBusSubscriber()
public class BFR implements IModule {

    public static final String MODID = "bfr";

    @SidedProxy(clientSide = "igentuman.bfr.client.BFRClientProxy", serverSide = "igentuman.bfr.common.BFRCommonProxy")
    public static BFRCommonProxy proxy;

    @Instance(BFR.MODID)
    public static BFR instance;

    /**
     * BFR version number
     */
    public static Version versionNumber = new Version(999, 999, 999);
    public static final int DATA_VERSION = 1;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        // Register blocks and tile entities
        BFRBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        // Register items and itemBlocks
        BFRBlocks.registerItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register models
        proxy.registerBlockRenders();
        proxy.registerItemRenders();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Mekanism.modulesLoaded.add(this);
        PacketSimpleGui.handlers.add(1, proxy);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new BFRGuiHandler());
        MinecraftForge.EVENT_BUS.register(this);
        proxy.registerTileEntities();
        proxy.registerTESRs();
        CompoundDataFixer fixer = FMLCommonHandler.instance().getDataFixer();
        ModFixs fixes = fixer.init(MODID, DATA_VERSION);
        fixes.registerFix(FixTypes.BLOCK_ENTITY, new GeneratorTEFixer(MekFixers.TILE_ENTITIES));
        Mekanism.logger.info("Loaded BFR module.");
    }

    @Override
    public Version getVersion() {
        return versionNumber;
    }

    @Override
    public String getName() {
        return "BFR";
    }

    @Override
    public void writeConfig(ByteBuf byteBuf, MekanismConfig mekanismConfig) {
    }

    @Override
    public void readConfig(ByteBuf byteBuf, MekanismConfig mekanismConfig) {
    }

    @Override
    public void resetClient() {
    }
}