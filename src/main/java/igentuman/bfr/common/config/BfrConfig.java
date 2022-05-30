package igentuman.bfr.common.config;

import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.CachedBooleanValue;
import mekanism.common.config.value.CachedFloatValue;
import mekanism.common.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public class BfrConfig extends BaseMekanismConfig {

    private static final String FUSION_CATEGORY = "better_fusion_reactor";

    private final ForgeConfigSpec configSpec;

    public final CachedIntValue reactionDifficulty;
    public final CachedBooleanValue reactorMeltdown;
    public final CachedFloatValue reactorExplosionRadius;

    BfrConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Better Fusion Reactor. This config is synced between server and client.").push("bfr");

        builder.comment("Better Fusion Reactor").push(FUSION_CATEGORY);
        reactionDifficulty = CachedIntValue.wrap(this, builder.comment("How often Reactivity changes and error level change speed. default 10")
                .defineInRange("reaction_difficulty", 10, 1, 20));
        reactorMeltdown = CachedBooleanValue.wrap(this, builder.comment("Explosion when reactor reaches 100% error level")
                .define("reactor_meltdown", false));
        builder.pop();
        reactorExplosionRadius = CachedFloatValue.wrap(this, builder.comment("Radius of Explosion (default 4 - TNT size)")
                .define("reactor_explosion_radius", 4.0));
        builder.pop();

        configSpec = builder.build();
    }

    @Override
    public String getFileName() {
        return "bfr";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public Type getConfigType() {
        return Type.SERVER;
    }
}
