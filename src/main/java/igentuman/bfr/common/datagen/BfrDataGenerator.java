package igentuman.bfr.common.datagen;

import igentuman.bfr.client.datagen.BfrLangProvider;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = MekanismGenerators.MODID, bus = Bus.MOD)
public class BfrDataGenerator {

    private BfrDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        //BfrDataGenerator.bootstrapConfigs(MekanismGenerators.MODID);
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new BfrLangProvider(gen));
        gen.addProvider(event.includeServer(), new BfrTagProvider(gen, existingFileHelper));
        gen.addProvider(event.includeServer(), new BfrRecipeProvider(gen, existingFileHelper));
    }
}