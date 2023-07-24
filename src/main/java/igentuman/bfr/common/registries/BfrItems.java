package igentuman.bfr.common.registries;

import igentuman.bfr.common.BetterFusionReactor;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;

public class BfrItems {

    private BfrItems() {
    }

    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(BetterFusionReactor.MODID);
    public static final ItemRegistryObject<Item> SOLIDIFIED_WASTE = ITEMS.register("solidified_waste");

}
