package igentuman.bfr.common.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public interface BfrConfigCategory {

    public default GuiScreen buildChildScreen(String categoryName, GuiConfig owningScreen, IConfigElement configElement) {
        Configuration config = BfrConfig.getConfig();
        ConfigElement newElement = new ConfigElement(config.getCategory(categoryName));
        List<IConfigElement> propertiesOnScreen = newElement.getChildElements();
        String windowTitle = I18n.translateToLocalFormatted("gui.bfr.config.category." + categoryName);
        return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, configElement.requiresWorldRestart() || owningScreen.allRequireWorldRestart, configElement.requiresMcRestart() || owningScreen.allRequireMcRestart, windowTitle);
    }
}
