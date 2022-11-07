package igentuman.bfr.common.datagen;

import igentuman.bfr.client.datagen.BfrLangProvider;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.datagen.loot.BfrLootProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(modid = BetterFusionReactor.MODID, bus = Bus.MOD)
public class BfrDataGenerator {

    private BfrDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
            //Client side data generators
            gen.addProvider(new BfrLangProvider(gen));
        }
        if (event.includeServer()) {
            //Server side data generators
            gen.addProvider(new BfrTagProvider(gen, existingFileHelper));
            gen.addProvider(new BfrLootProvider(gen));
            gen.addProvider(new BfrRecipeProvider(gen, existingFileHelper));
        }
    }
}