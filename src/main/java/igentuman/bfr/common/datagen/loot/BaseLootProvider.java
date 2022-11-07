package igentuman.bfr.common.datagen.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseLootProvider extends LootTableProvider {

    private final String modid;

    protected BaseLootProvider(DataGenerator gen, String modid) {
        super(gen);
        this.modid = modid;
    }

    @Nonnull
    @Override
    public String getName() {
        return super.getName() + ": " + modid;
    }

    @Nullable
    protected BaseBlockLootTables getBlockLootTable() {
        return null;
    }


    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
        ImmutableList.Builder<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> builder = new ImmutableList.Builder<>();
        BaseBlockLootTables blockLootTable = getBlockLootTable();
        if (blockLootTable != null) {
            builder.add(Pair.of(() -> blockLootTable, LootContextParamSets.BLOCK));
        }
        return builder.build();
    }

    @Override
    protected void validate(@Nonnull Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
        //NO-OP, as we don't
    }
}