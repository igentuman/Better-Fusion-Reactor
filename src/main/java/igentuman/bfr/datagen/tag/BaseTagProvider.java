package igentuman.bfr.datagen.tag;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IChemicalProvider;
import mekanism.api.providers.IEntityTypeProvider;
import mekanism.api.providers.IGasProvider;
import mekanism.api.providers.IInfuseTypeProvider;
import mekanism.api.providers.IPigmentProvider;
import mekanism.api.providers.ISlurryProvider;
import mekanism.common.registration.impl.FluidRegistryObject;
import mekanism.common.registration.impl.GameEventRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.util.RegistryUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseTagProvider implements DataProvider {

    private final Map<ResourceKey<? extends Registry<?>>, Map<TagKey<?>, TagBuilder>> supportedTagTypes = new Object2ObjectLinkedOpenHashMap<>();
    private final Set<Block> knownHarvestRequirements = new ReferenceOpenHashSet<>();
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final ExistingFileHelper existingFileHelper;
    private final PackOutput output;
    private final String modid;

    protected BaseTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        this.modid = modid;
        this.output = output;
        this.lookupProvider = lookupProvider;
        this.existingFileHelper = existingFileHelper;
    }

    @NotNull
    @Override
    public String getName() {
        return "Tags: " + modid;
    }

    protected abstract void registerTags(HolderLookup.Provider registries);

    protected List<IBlockProvider> getAllBlocks() {
        return Collections.emptyList();
    }

    protected void hasHarvestData(Block block) {
        knownHarvestRequirements.add(block);
    }

    @NotNull
    @Override
    public CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return this.lookupProvider.thenApply(registries -> {
            supportedTagTypes.values().forEach(Map::clear);
            registerTags(registries);
            return registries;
        }).thenCompose(registries -> {
            for (IBlockProvider blockProvider : getAllBlocks()) {
                Block block = blockProvider.getBlock();
                if (block.defaultBlockState().requiresCorrectToolForDrops() && !knownHarvestRequirements.contains(block)) {
                    throw new IllegalStateException("Missing harvest tool type for block '" + RegistryUtils.getName(block) + "' that requires the correct tool for drops.");
                }
            }
            List<CompletableFuture<?>> futures = new ArrayList<>();
            supportedTagTypes.forEach((registry, tagTypeMap) -> {
                if (!tagTypeMap.isEmpty()) {
                    //Create a dummy provider and pass all our collected data through to it
                    futures.add(new TagsProvider(output, registry, lookupProvider, modid, existingFileHelper) {
                        @Override
                        protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
                            //Add each tag builder to the wrapped provider's builder
                            tagTypeMap.forEach((tag, tagBuilder) -> builders.put(tag.location(), tagBuilder));
                        }
                    }.run(cache));
                }
            });
            return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
        });
    }

    //Protected to allow for extensions to add retrieve their own supported types if they have any
    protected <TYPE> ForgeRegistryTagBuilder<TYPE> getBuilder(TagType<TYPE> tagType, TagKey<TYPE> tag) {
        return new ForgeRegistryTagBuilder<>(tagType.getRegistry(), supportedTagTypes.get(tagType).computeIfAbsent(tag, ignored -> TagBuilder.create()), modid);
    }

    protected ForgeRegistryTagBuilder<Item> getItemBuilder(TagKey<Item> tag) {
        return getBuilder(TagType.ITEM, tag);
    }

    protected ForgeRegistryTagBuilder<Block> getBlockBuilder(TagKey<Block> tag) {
        return getBuilder(TagType.BLOCK, tag);
    }

    protected ForgeRegistryTagBuilder<EntityType<?>> getEntityTypeBuilder(TagKey<EntityType<?>> tag) {
        return getBuilder(TagType.ENTITY_TYPE, tag);
    }

    protected ForgeRegistryTagBuilder<Fluid> getFluidBuilder(TagKey<Fluid> tag) {
        return getBuilder(TagType.FLUID, tag);
    }

    protected ForgeRegistryTagBuilder<BlockEntityType<?>> getTileEntityTypeBuilder(TagKey<BlockEntityType<?>> tag) {
        return getBuilder(TagType.BLOCK_ENTITY_TYPE, tag);
    }

    protected ForgeRegistryTagBuilder<Gas> getGasBuilder(TagKey<Gas> tag) {
        return getBuilder(TagType.GAS, tag);
    }

    protected ForgeRegistryTagBuilder<InfuseType> getInfuseTypeBuilder(TagKey<InfuseType> tag) {
        return getBuilder(TagType.INFUSE_TYPE, tag);
    }

    protected ForgeRegistryTagBuilder<Pigment> getPigmentBuilder(TagKey<Pigment> tag) {
        return getBuilder(TagType.PIGMENT, tag);
    }

    protected ForgeRegistryTagBuilder<Slurry> getSlurryBuilder(TagKey<Slurry> tag) {
        return getBuilder(TagType.SLURRY, tag);
    }

    protected void addToTag(TagKey<Item> tag, ItemLike... itemProviders) {
        getItemBuilder(tag).addTyped(ItemLike::asItem, itemProviders);
    }

    protected void addToTag(TagKey<Block> tag, IBlockProvider... blockProviders) {
        getBlockBuilder(tag).addTyped(IBlockProvider::getBlock, blockProviders);
    }

    @SafeVarargs
    protected final void addToTag(TagKey<Block> blockTag, Map<?, ? extends IBlockProvider>... blockProviders) {
        ForgeRegistryTagBuilder<Block> tagBuilder = getBlockBuilder(blockTag);
        for (Map<?, ? extends IBlockProvider> blockProvider : blockProviders) {
            for (IBlockProvider value : blockProvider.values()) {
                tagBuilder.add(value.getBlock());
            }
        }
    }

    protected void addToHarvestTag(TagKey<Block> tag, IBlockProvider... blockProviders) {
        ForgeRegistryTagBuilder<Block> tagBuilder = getBlockBuilder(tag);
        for (IBlockProvider blockProvider : blockProviders) {
            Block block = blockProvider.getBlock();
            tagBuilder.add(block);
            hasHarvestData(block);
        }
    }

    @SafeVarargs
    protected final void addToHarvestTag(TagKey<Block> blockTag, Map<?, ? extends IBlockProvider>... blockProviders) {
        ForgeRegistryTagBuilder<Block> tagBuilder = getBlockBuilder(blockTag);
        for (Map<?, ? extends IBlockProvider> blockProvider : blockProviders) {
            for (IBlockProvider value : blockProvider.values()) {
                Block block = value.getBlock();
                tagBuilder.add(block);
                hasHarvestData(block);
            }
        }
    }

    protected void addToTags(TagKey<Item> itemTag, TagKey<Block> blockTag, IBlockProvider... blockProviders) {
        ForgeRegistryTagBuilder<Item> itemTagBuilder = getItemBuilder(itemTag);
        ForgeRegistryTagBuilder<Block> blockTagBuilder = getBlockBuilder(blockTag);
        for (IBlockProvider blockProvider : blockProviders) {
            itemTagBuilder.add(blockProvider.asItem());
            blockTagBuilder.add(blockProvider.getBlock());
        }
    }

    protected void addToTag(TagKey<EntityType<?>> tag, IEntityTypeProvider... entityTypeProviders) {
        getEntityTypeBuilder(tag).addTyped(IEntityTypeProvider::getEntityType, entityTypeProviders);
    }

    protected void addToTag(TagKey<Fluid> tag, FluidRegistryObject<?, ?, ?, ?, ?>... fluidRegistryObjects) {
        ForgeRegistryTagBuilder<Fluid> tagBuilder = getFluidBuilder(tag);
        for (FluidRegistryObject<?, ?, ?, ?, ?> fluidRO : fluidRegistryObjects) {
            tagBuilder.add(fluidRO.getStillFluid(), fluidRO.getFlowingFluid());
        }
    }

    protected void addToTag(TagKey<BlockEntityType<?>> tag, TileEntityTypeRegistryObject<?>... tileEntityTypeRegistryObjects) {
        getTileEntityTypeBuilder(tag).add(tileEntityTypeRegistryObjects);
    }

    protected void addToTag(TagKey<Gas> tag, IGasProvider... gasProviders) {
        addToTag(getGasBuilder(tag), gasProviders);
    }

    protected void addToTag(TagKey<InfuseType> tag, IInfuseTypeProvider... infuseTypeProviders) {
        addToTag(getInfuseTypeBuilder(tag), infuseTypeProviders);
    }

    protected void addToTag(TagKey<Pigment> tag, IPigmentProvider... pigmentProviders) {
        addToTag(getPigmentBuilder(tag), pigmentProviders);
    }

    protected void addToTag(TagKey<Slurry> tag, ISlurryProvider... slurryProviders) {
        addToTag(getSlurryBuilder(tag), slurryProviders);
    }

    @SafeVarargs
    protected final <CHEMICAL extends Chemical<CHEMICAL>> void addToTag(ForgeRegistryTagBuilder<CHEMICAL> tagBuilder, IChemicalProvider<CHEMICAL>... providers) {
        tagBuilder.addTyped(IChemicalProvider::getChemical, providers);
    }
}