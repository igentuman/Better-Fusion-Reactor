package igentuman.bfr.datagen;

import com.electronwill.nightconfig.core.CommentedConfig;
import igentuman.bfr.datagen.lang.BfrLangProvider;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.datagen.loot.BfrLootProvider;
import mekanism.common.Mekanism;
import mekanism.generators.common.MekanismGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BetterFusionReactor.MODID, bus = Bus.MOD)
public class BfrDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        bootstrapConfigs(MekanismGenerators.MODID);
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new BfrLangProvider(output));
        gen.addProvider(event.includeServer(), new BfrTagProvider(output, lookupProvider, existingFileHelper));
        gen.addProvider(event.includeServer(), new BfrRecipeProvider(output, existingFileHelper));
        gen.addProvider(event.includeServer(), new BfrLootProvider(output));

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