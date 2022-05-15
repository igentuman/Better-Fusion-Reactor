package igentuman.bfr.common.config;

import igentuman.bfr.common.BFR;
import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BfrConfig {

	private static Configuration config = null;

	public static final String BFR_CATEGORY = "better_fusion_reactor";

	public static Configuration getConfig()
	{
		return config;
	}
	public static int reactionDifficulty;
	public static boolean reactorMeltdown;
	public static float reactorExplosionPower;

	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), "better_fusion_reactor.cfg"));
		syncConfig(true, true);
	}
	
	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}


	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		Property reaction_difficulty = config.get(BFR_CATEGORY, "reaction_difficulty", 10, Lang.localise("gui.bfr.config.better_fusion_reactor.reaction_difficulty.comment"),1,20);
		reaction_difficulty.setLanguageKey("gui.bfr.config.better_fusion_reactor.reaction_difficulty");
		reactionDifficulty = reaction_difficulty.getInt();

		Property reactor_meltdown = config.get(BFR_CATEGORY, "reactor_meltdown", true, Lang.localise("gui.bfr.config.better_fusion_reactor.reactor_meltdown.comment"));
		reactor_meltdown.setLanguageKey("gui.bfr.config.better_fusion_reactor.reactor_meltdown");
		reactorMeltdown = reactor_meltdown.getBoolean();

		Property reactor_explosion_power = config.get(BFR_CATEGORY, "reactor_explosion_power", 160.0f, Lang.localise("gui.bfr.config.better_fusion_reactor.reactor_explosion.comment"));
		reactor_explosion_power.setLanguageKey("gui.bfr.config.better_fusion_reactor.reactor_explosion");
		reactorExplosionPower= reactor_explosion_power.getFloat();
		if (config.hasChanged()) config.save();
	}

	private static class ClientConfigEventHandler
	{

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(BFR.MODID))
			{
				syncConfig(false, true);
			}
		}
	}
	

}
