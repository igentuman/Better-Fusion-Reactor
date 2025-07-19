package igentuman.bfr.common.events;

import igentuman.bfr.common.BetterFusionReactor;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class GameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS =
            DeferredRegister.create(Registries.GAME_EVENT, BetterFusionReactor.MODID);

    public static final RegistryObject<GameEvent> REACTOR_LOW_CR_VIBRATION =
            GAME_EVENTS.register("reactor_low_cr_vibration", () -> new GameEvent("reactor_low_cr_vibration", 16));
    public static final RegistryObject<GameEvent> REACTOR_HI_CR_VIBRATION =
            GAME_EVENTS.register("reactor_high_cr_vibration", () -> new GameEvent("reactor_high_cr_vibration", 16));
    public static void init(FMLJavaModLoadingContext context)
    {
        GAME_EVENTS.register(context.getModEventBus());
    }

    public static void commonSetup() {
        if (VibrationSystem.VIBRATION_FREQUENCY_FOR_EVENT instanceof Object2IntOpenHashMap<GameEvent> frequencyForEvent) {
            frequencyForEvent.put(REACTOR_LOW_CR_VIBRATION.get(), 6);
        }
        if (VibrationSystem.VIBRATION_FREQUENCY_FOR_EVENT instanceof Object2IntOpenHashMap<GameEvent> frequencyForEvent) {
            frequencyForEvent.put(REACTOR_HI_CR_VIBRATION.get(), 15);
        }
    }
}
