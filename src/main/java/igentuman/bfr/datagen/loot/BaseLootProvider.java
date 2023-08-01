package igentuman.bfr.datagen.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseLootProvider extends LootTableProvider {

    protected BaseLootProvider(PackOutput output, List<LootTableProvider.SubProviderEntry> subProviders) {
        this(output, Collections.emptySet(), subProviders);
    }

    protected BaseLootProvider(PackOutput output, Set<ResourceLocation> requiredTables, List<LootTableProvider.SubProviderEntry> subProviders) {
        super(output, requiredTables, subProviders);
    }
}