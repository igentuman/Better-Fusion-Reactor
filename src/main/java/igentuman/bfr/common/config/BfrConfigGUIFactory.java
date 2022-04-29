package igentuman.bfr.common.config;

import igentuman.bfr.common.BFR;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.Language;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.GuiConfigEntries.IConfigEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BfrConfigGUIFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
	}

	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return BfrConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	public static class BfrConfigGui extends GuiConfig
	{

		public BfrConfigGui(GuiScreen parentScreen)
		{
			super(parentScreen, getConfigElements(), BFR.MODID, false, false,
					"Better Fusion Reactor");
		}

		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<>();
			list.add(categoryElement(BfrConfig.BFR_CATEGORY, CategoryEntryProcessors.class));
			return list;
		}

		private static DummyCategoryElement categoryElement(String categoryName,
				Class<? extends IConfigEntry> categoryClass)
		{
			return new DummyCategoryElement(I18n.translateToLocalFormatted("gui.bfr.config.category." + categoryName),
					"gui.bfr.config.category." + categoryName, categoryClass);
		}

		public static class CategoryEntryProcessors extends CategoryEntry implements BfrConfigCategory
		{

			public CategoryEntryProcessors(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
					IConfigElement configElement)
			{
				super(owningScreen, owningEntryList, configElement);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				return buildChildScreen(BfrConfig.BFR_CATEGORY, owningScreen, configElement);
			}
		}

	}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new BfrConfigGui(parentScreen);
	}
}
