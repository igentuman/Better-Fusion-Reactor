package igentuman.bfr.common;

import net.minecraftforge.fml.ModList;

public class ModUtil {
    protected static boolean initialized = false;
    protected static boolean tinkersAdvancedLoaded;
    protected static void initialize()
    {
        if(initialized)
            return;
        initialized = true;
        tinkersAdvancedLoaded = ModList.get().isLoaded("tinkers_advanced");
    }

    public static boolean isTinkersAdvancedLoaded() {
        initialize();
        return tinkersAdvancedLoaded;
    }

}
