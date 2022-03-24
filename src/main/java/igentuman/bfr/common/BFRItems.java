package igentuman.bfr.common;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BFR.MODID)
public class BFRItems {

    public static void registerItems(IForgeRegistry<Item> registry) {

    }

    public static Item init(Item item, String name) {
        return item.setTranslationKey(name).setRegistryName(new ResourceLocation(BFR.MODID, name));
    }
}