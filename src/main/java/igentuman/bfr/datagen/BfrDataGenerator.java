package igentuman.bfr.datagen;

import com.electronwill.nightconfig.core.CommentedConfig;
import igentuman.bfr.datagen.lang.BfrLangProvider;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.datagen.loot.BfrLootProvider;
import mekanism.common.Mekanism;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = BetterFusionReactor.MODID, bus = Bus.MOD)
public class BfrDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        bootstrapConfigs(MekanismGenerators.MODID);
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new BfrLangProvider(gen));
        gen.addProvider(event.includeServer(), new BfrTagProvider(gen, existingFileHelper));
        gen.addProvider(event.includeServer(), new BfrRecipeProvider(gen, existingFileHelper));
        gen.addProvider(event.includeServer(), new BfrLootProvider(gen));

    }

    public static void bootstrapConfigs(String modid) {
        ConfigTracker.INSTANCE.configSets().forEach((type, configs) -> {
            for (ModConfig config : configs) {
                if (config.getModId().equals(modid)) {
                    CommentedConfig commentedConfig = CommentedConfig.inMemory();
                    config.getSpec().correct(commentedConfig);
                    config.getSpec().acceptConfig(commentedConfig);
                }
            }
        });
    }
}