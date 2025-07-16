package igentuman.bfr.common.tile.fusion;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.function.IntFunction;
import mekanism.api.SerializationConstants;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTranslationKey.IHasEnumNameTranslationKey;
import mekanism.api.text.ILangEntry;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableBoolean;
import mekanism.common.inventory.container.sync.SyncableEnum;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.tile.interfaces.IHasMode;
import mekanism.common.util.NBTUtils;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.base.IReactorLogic;
import igentuman.bfr.common.base.IReactorLogicMode;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrDataComponents;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

public class TileEntityFusionReactorLogicAdapter extends TileEntityFusionReactorBlock implements IReactorLogic<FusionReactorLogic>, IHasMode {

    public FusionReactorLogic logicType = FusionReactorLogic.READY;
    private boolean activeCooled;
    private int prevOutputting;
    protected ArrayList<FusionReactorLogic> inLogicModes;
    protected ArrayList<FusionReactorLogic> outLogicModes;
    protected int prevRedstoneLevel;

    public TileEntityFusionReactorLogicAdapter(BlockPos pos, BlockState state) {
        super(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER, pos, state);
    }

    @Override
    protected boolean onUpdateServer(BFReactorMultiblockData multiblock) {
        boolean needsPacket = super.onUpdateServer(multiblock);
        int outputting = checkMode();
        if (outputting != prevOutputting) {
            Level world = getLevel();
            if (world != null) {
                Direction side = multiblock.getOutsideSide(worldPosition);
                BlockState state = getBlockState();
                if (side == null) {
                    //Not formed, just update all sides
                    world.updateNeighborsAt(getBlockPos(), state.getBlock());
                } else if (!EventHooks.onNeighborNotify(world, worldPosition, state, EnumSet.of(side), false).isCanceled()) {
                    world.neighborChanged(worldPosition.relative(side), state.getBlock(), worldPosition);
                }
            }
            prevOutputting = outputting;
        }
        return needsPacket;
    }

    public FusionReactorLogic[] getInputModes() {
        if(inLogicModes == null) {
            inLogicModes = new ArrayList<>();
            for(int i = 0; i < FusionReactorLogic.values().length; i++) {
                if(Objects.equals(FusionReactorLogic.values()[i].direction, "in")) {
                    inLogicModes.add(FusionReactorLogic.values()[i]);
                }
            }
        }
        return inLogicModes.toArray(new FusionReactorLogic[0]);
    }

    public FusionReactorLogic[] getOutputModes() {
        if(outLogicModes == null) {
            outLogicModes = new ArrayList<>();
            for(int i = 0; i < FusionReactorLogic.values().length; i++) {
                if(Objects.equals(FusionReactorLogic.values()[i].direction, "out")) {
                    outLogicModes.add(FusionReactorLogic.values()[i]);
                }
            }
        }
        return outLogicModes.toArray(new FusionReactorLogic[0]);
    }

    public void onPowerChange()
    {
        if(isPowered() && !wasPowered()) {
            BFReactorMultiblockData multiblock = getMultiblock();
            if(multiblock == null || !getMultiblock().isFormed()) {
                return;
            }
            int power = getLevel().getBestNeighborSignal(getBlockPos());
            switch (logicType) {
                case INJECTION_DOWN:
                    multiblock.setInjectionRate(multiblock.getInjectionRate()-2);
                    break;
                case INJECTION_UP:
                    multiblock.setInjectionRate(multiblock.getInjectionRate()+2);
                    break;
                case REACTIVITY_UP:
                    multiblock.adjustReactivity(power);
                    break;
                case REACTIVITY_DOWN:
                    multiblock.adjustReactivity(-power);
                    break;
                default:
                    return;
            }
            markForSave();
        }
    }

    public int getRedstoneLevel(Direction side)
    {
        return !isRemote() && getMultiblock().isPositionOutsideBounds(worldPosition.relative(side)) ? checkMode() : 0;
    }

    public int checkMode() {
        if (isRemote()) {
            return prevRedstoneLevel;
        }
        BFReactorMultiblockData multiblock = getMultiblock();
        if (multiblock == null || !getMultiblock().isFormed()) {
            return 0;
        }
        switch (logicType) {
            case READY:
                return multiblock.getLastPlasmaTemp() >= multiblock.getIgnitionTemperature(activeCooled)  ? 15 : 0;
            case CAPACITY:
                return multiblock.getLastPlasmaTemp() >= multiblock.getMaxPlasmaTemperature(activeCooled) ? 15 : 0;
            case ERROR_LEVEL:
                return (int)(multiblock.getErrorLevel() / (100 / 15));
            case EFFICIENCY:
                return (int)(multiblock.getEfficiency() / (100 / 15));
            case DEPLETED:
                return (multiblock.deuteriumTank.getStored() < multiblock.getInjectionRate() / 2) ||
                        (multiblock.tritiumTank.getStored() < multiblock.getInjectionRate() / 2) ? 15 : 0;
            default:
                return 0;
        }
    }

    @Override
    public void readSustainedData(HolderLookup.Provider provider, @NotNull CompoundTag nbt) {
        super.readSustainedData(provider, nbt);
        NBTUtils.setEnumIfPresent(nbt, SerializationConstants.LOGIC_TYPE, FusionReactorLogic.BY_ID, logicType -> this.logicType = logicType);
        activeCooled = nbt.getBoolean(SerializationConstants.ACTIVE_COOLED);
    }

    @Override
    public void writeSustainedData(HolderLookup.Provider provider, CompoundTag nbtTags) {
        super.writeSustainedData(provider, nbtTags);
        NBTUtils.writeEnum(nbtTags, SerializationConstants.LOGIC_TYPE, logicType);
        nbtTags.putBoolean(SerializationConstants.ACTIVE_COOLED, activeCooled);
    }

    @Override
    protected void collectImplicitComponents(@NotNull DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(BfrDataComponents.FUSION_LOGIC_TYPE, logicType);
        builder.set(BfrDataComponents.ACTIVE_COOLED, activeCooled);
    }

    @Override
    protected void applyImplicitComponents(@NotNull BlockEntity.DataComponentInput input) {
        super.applyImplicitComponents(input);
        logicType = input.getOrDefault(BfrDataComponents.FUSION_LOGIC_TYPE, logicType);
        activeCooled = input.getOrDefault(BfrDataComponents.ACTIVE_COOLED, activeCooled);
    }

    @Override
    public void nextMode() {
        activeCooled = !activeCooled;
        markForSave();
    }

    @Override
    public void previousMode() {
        //We only have two modes just flip it
        nextMode();
    }

    @ComputerMethod(nameOverride = "isActiveCooledLogic")
    public boolean isActiveCooled() {
        return activeCooled;
    }

    @Override
    @ComputerMethod(nameOverride = "getLogicMode")
    public FusionReactorLogic getMode() {
        return logicType;
    }

    @Override
    public FusionReactorLogic[] getModes() {
        return FusionReactorLogic.values();
    }

    @ComputerMethod(nameOverride = "setLogicMode")
    public void setLogicTypeFromPacket(FusionReactorLogic logicType) {
        if (this.logicType != logicType) {
            this.logicType = logicType;
            markForSave();
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableEnum.create(FusionReactorLogic.BY_ID, FusionReactorLogic.READY, this::getMode, value -> logicType = value));
        container.track(SyncableBoolean.create(this::isActiveCooled, value -> activeCooled = value));
        container.track(SyncableInt.create(() -> prevOutputting, value -> prevOutputting = value));
    }

    //Methods relating to IComputerTile
    @ComputerMethod
    void setActiveCooledLogic(boolean active) {
        if (activeCooled != active) {
            nextMode();
        }
    }
    //End methods IComputerTile

    @NothingNullByDefault
    public enum FusionReactorLogic implements IReactorLogicMode<FusionReactorLogic>, IHasEnumNameTranslationKey, StringRepresentable {
        READY(GeneratorsLang.REACTOR_LOGIC_READY, GeneratorsLang.DESCRIPTION_REACTOR_READY, new ItemStack(Items.REDSTONE), "out"),
        CAPACITY(GeneratorsLang.REACTOR_LOGIC_CAPACITY, GeneratorsLang.DESCRIPTION_REACTOR_CAPACITY, new ItemStack(Items.REDSTONE), "out"),
        DEPLETED(GeneratorsLang.REACTOR_LOGIC_DEPLETED, GeneratorsLang.DESCRIPTION_REACTOR_DEPLETED, new ItemStack(Items.REDSTONE), "out"),
        EFFICIENCY(BfrLang.REACTOR_LOGIC_EFFICIENCY, BfrLang.DESCRIPTION_REACTOR_EFFICIENCY, new ItemStack(Items.REDSTONE), "out"),
        ERROR_LEVEL(BfrLang.REACTOR_LOGIC_ERROR_LEVEL, BfrLang.DESCRIPTION_REACTOR_ERROR_LEVEL, new ItemStack(Items.REDSTONE), "out"),
        INJECTION_UP(BfrLang.REACTOR_LOGIC_INJECTION_UP, BfrLang.DESCRIPTION_REACTOR_INJECTION_UP, new ItemStack(Items.REDSTONE), "in"),
        INJECTION_DOWN(BfrLang.REACTOR_LOGIC_INJECTION_DOWN, BfrLang.DESCRIPTION_REACTOR_INJECTION_DOWN, new ItemStack(Items.REDSTONE), "in"),
        REACTIVITY_UP(BfrLang.REACTOR_LOGIC_REACTIVITY_UP, BfrLang.DESCRIPTION_REACTOR_REACTIVITY_UP, new ItemStack(Items.REDSTONE), "in"),
        REACTIVITY_DOWN(BfrLang.REACTOR_LOGIC_REACTIVITY_DOWN, BfrLang.DESCRIPTION_REACTOR_REACTIVITY_DOWN, new ItemStack(Items.REDSTONE), "in");

        public static final Codec<FusionReactorLogic> CODEC = StringRepresentable.fromEnum(FusionReactorLogic::values);
        public static final IntFunction<FusionReactorLogic> BY_ID = ByIdMap.continuous(FusionReactorLogic::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, FusionReactorLogic> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FusionReactorLogic::ordinal);

        private final ILangEntry name;
        private final ILangEntry description;
        private final ItemStack renderStack;
        private final String serializedName;
        private final String direction;

        FusionReactorLogic(ILangEntry name, ILangEntry description, ItemStack stack, String dir) {
            this.name = name;
            this.description = description;
            this.renderStack = stack;
            this.serializedName = name().toLowerCase(Locale.ROOT);
            direction = dir;
        }

        @Override
        public ItemStack getRenderStack() {
            return renderStack;
        }

        @Override
        public String getTranslationKey() {
            return name.getTranslationKey();
        }

        @Override
        public Component getDescription() {
            return description.translate();
        }

        @Override
        public EnumColor getColor() {
            return EnumColor.RED;
        }


        @Override
        public String getSerializedName() {
            return serializedName;
        }
    }
}