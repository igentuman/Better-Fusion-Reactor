package igentuman.bfr.common.events;

import igentuman.bfr.common.BetterFusionReactor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class GameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS =
            DeferredRegister.create(Registries.GAME_EVENT, BetterFusionReactor.MODID);

    public static final DeferredHolder<GameEvent, GameEvent> REACTOR_LOW_CR_VIBRATION =
            GAME_EVENTS.register("reactor_low_cr_vibration", () -> new GameEvent(16));
    public static final DeferredHolder<GameEvent, GameEvent> REACTOR_HI_CR_VIBRATION =
            GAME_EVENTS.register("reactor_high_cr_vibration", () -> new GameEvent(16));

    public static void init(IEventBus bus)
    {
        GAME_EVENTS.register(bus);
    }

    public static void commonSetup() {
        // Vibration frequencies are now configured via data maps in NeoForge 1.20.1+
        // Create data/bfr/neoforge/data_maps/game_event/vibration_frequencies.json to set frequencies
        // This method is kept for potential future use or other setup tasks
    }
}
