package igentuman.bfr.common.config;

import igentuman.bfr.common.recipes.ReactorCoolantRecipe;
import igentuman.bfr.common.recipes.StackHelper;
import mekanism.common.Mekanism;
import nc.util.Lang;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

import static igentuman.bfr.common.BFR.MODID;

public class ReactorCoolantRecipesConfig {

	private static Configuration config = null;
	public static final String RECIPES = "recipes";
	public static String[] coolantRecipes;
	public static ReactorCoolantRecipe[] ReactorCoolantRecipes;
	public static void preInit()
	{
		config = new Configuration(new File(Loader.instance().getConfigDir(), "bfr_coolant_recipes.cfg"));
		syncConfig(true, true);
	}

	public static FluidStack fluidStack(String fluidName, int stackSize) {
		return !StackHelper.fluidExists(fluidName) ? null : StackHelper.getFluidStackFromString(fluidName, stackSize);
	}

	public static void clientPreInit()
	{
		MinecraftForge.EVENT_BUS.register(new ClientConfigEventHandler());
	}


	private static void syncConfig(boolean loadFromFile, boolean setFromConfig)
	{
		if (loadFromFile) config.load();

		String[] defaultCoolantRecipes = new String[] {
				"water*3000=steam*3000",
				"preheated_water*1000=high_pressure_steam*1000",
		};

		coolantRecipes = config.get(RECIPES, "reactor_coolant", defaultCoolantRecipes, Lang.localise("gui.bfr.config.better_fusion_reactor.coolant.comment")).getStringList();

		if (config.hasChanged()) config.save();
	}

	public static ReactorCoolantRecipe[] getReactorCoolantRecipes()
	{
		if(ReactorCoolantRecipes == null) {
			ReactorCoolantRecipes = new ReactorCoolantRecipe[coolantRecipes.length];
			int i = 0;
			for(String recipe: coolantRecipes) {
				FluidStack[] parsedRecipe = parseCoolantRecipe(recipe);
				ReactorCoolantRecipes[i] = new ReactorCoolantRecipe(parsedRecipe[1],parsedRecipe[0]);
				i++;

			}
		}
		return ReactorCoolantRecipes;
	}

	public static FluidStack[] parseCoolantRecipe(String recipe)
	{
		String[] parts = recipe.split("=");
		if(parts.length != 2) return null;
		String[] input = parts[0].split(";");
		FluidStack[] recipeObj = new FluidStack[input.length+1];
		int qty = 1000;
		try {
			for(int i = 0; i < input.length; i++) {
				String[] ingredient = input[i].split("\\*");
				qty = 1000;
				if(ingredient.length > 1) {
					qty = Integer.parseInt(ingredient[1]);
				}
				recipeObj[i] = fluidStack(ingredient[0], qty);
			}
			String[] output = parts[1].split("\\*");
			qty = 1;
			if(output.length > 1) {
				qty = Integer.parseInt(output[1]);
			}
			recipeObj[recipeObj.length-1] = fluidStack(output[0], qty);
		} catch (Exception e) {
			Mekanism.logger.log(Level.ERROR,"Coolant Recipe format issue");
			return null;
		}
		return recipeObj;
	}

	private static class ClientConfigEventHandler
	{

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(OnConfigChangedEvent event)
		{
			if (event.getModID().equals(MODID))
			{
				syncConfig(false, true);
			}
		}
	}
	

}
