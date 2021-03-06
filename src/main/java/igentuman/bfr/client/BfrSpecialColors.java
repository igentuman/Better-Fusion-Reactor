package igentuman.bfr.client;

import mekanism.client.render.lib.ColorAtlas;
import mekanism.client.render.lib.ColorAtlas.ColorRegistryObject;

public class BfrSpecialColors {

    private BfrSpecialColors() {
    }

    public static final ColorAtlas GUI_OBJECTS = new ColorAtlas("generators_gui_objects");

    public static final ColorRegistryObject TAB_MULTIBLOCK_HEAT = GUI_OBJECTS.register();
    public static final ColorRegistryObject TAB_MULTIBLOCK_FUEL = GUI_OBJECTS.register();
}