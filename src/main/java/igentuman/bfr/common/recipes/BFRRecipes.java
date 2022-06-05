package igentuman.bfr.common.recipes;

import com.google.common.collect.Lists;
import igentuman.bfr.common.BFR;
import igentuman.bfr.common.config.ReactorCoolantRecipesConfig;
import mekanism.generators.common.GeneratorsBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.ForgeRegistry;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BFRRecipes
{
	private static boolean initialized = false;
	public static final RecipeManager<ReactorCoolantRecipe> REACTOR_COOLANT = new RecipeManager<>();

	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		if (initialized)
			return;
/*		addShapedOreRecipe(
				new ItemStack(BFRBlocks.Reactor,1),
				new Object[]{"   ", " SS", " SS", 'S', "ingotCopper"}
		);*/
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.Reactor),0);
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.Reactor),1);
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.Reactor),2);
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.Reactor),3);
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.ReactorGlass),0);
		removeRecipeFor(Item.getItemFromBlock(GeneratorsBlocks.ReactorGlass),1);

		for(ReactorCoolantRecipe recipe: ReactorCoolantRecipesConfig.getReactorCoolantRecipes()) {
			REACTOR_COOLANT.add(recipe);
		}
		initialized = true;
	}

	public static void removeRecipeFor(Item item, int meta)
	{
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
		ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());

		for (IRecipe r : recipes)
		{
			ItemStack output = r.getRecipeOutput();
			if (output.getItem() == item && output.getItem().getMetadata(output) == meta)
			{
				recipeRegistry.remove(r.getRegistryName());
			}
		}
	}

	public static void addShapedOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedOreRecipe.class, out, inputs);
	}

	private static final Map<String, Integer> RECIPE_COUNT_MAP = new HashMap<String, Integer>();

	public static <T> T newInstance(Class<T> clazz, Object... args) throws Exception {
		Constructor<T> constructor = clazz.getConstructor(getClasses(args));
		return constructor.newInstance(args);
	}

	public static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class[objects.length];

		for(int i = 0; i < objects.length; ++i) {
			classes[i] = objects[i].getClass();
		}

		return classes;
	}

	public static void registerRecipe(Class<? extends IRecipe> clazz, Object out, Object... inputs)
	{
		if (out == null || Lists.newArrayList(inputs).contains(null))
			return;
		ItemStack outStack = StackHelper.fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null)
		{
			String outName = outStack.getTranslationKey();
			if (RECIPE_COUNT_MAP.containsKey(outName))
			{
				int count = RECIPE_COUNT_MAP.get(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;
			}
			else
				RECIPE_COUNT_MAP.put(outName, 1);
			ResourceLocation location = new ResourceLocation(BFR.MODID, outName);
			try
			{
				IRecipe recipe = newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			}
			catch (Exception e)
			{

			}
		}
	}
}
