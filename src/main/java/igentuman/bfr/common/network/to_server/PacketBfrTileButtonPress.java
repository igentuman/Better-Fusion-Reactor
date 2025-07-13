package igentuman.bfr.common.network.to_server;

import io.netty.buffer.ByteBuf;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used for informing the server that a click happened in a GUI and the gui window needs to change
 */
public record PacketBfrTileButtonPress(ClickedGeneratorsTileButton buttonClicked, BlockPos pos) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketBfrTileButtonPress> TYPE = new CustomPacketPayload.Type<>(BetterFusionReactor.rl("tile_button"));
    public static final StreamCodec<ByteBuf, PacketBfrTileButtonPress> STREAM_CODEC = StreamCodec.composite(
          ClickedGeneratorsTileButton.STREAM_CODEC, PacketBfrTileButtonPress::buttonClicked,
          BlockPos.STREAM_CODEC, PacketBfrTileButtonPress::pos,
          PacketBfrTileButtonPress::new
    );

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketBfrTileButtonPress> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        //If we are on the server (the only time we should be receiving this packet), let forge handle switching the Gui
        TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), pos);
        MenuProvider provider = buttonClicked.getProvider(tile);
        if (provider != null) {
            player.openMenu(provider, buf -> {
                buf.writeBlockPos(pos);
                buttonClicked.encodeExtraData(buf, tile);
            });
        }
    }

    public enum ClickedGeneratorsTileButton {
        TAB_MAIN(tile -> {
            return null;
        }, (buffer, tile) -> {
            tile.encodeExtraContainerData(buffer);
        }),
        TAB_HEAT(tile -> BfrContainerTypes.FUSION_REACTOR_HEAT.getProvider(BfrLang.FUSION_REACTOR, tile)),
        TAB_FUEL(tile -> BfrContainerTypes.FUSION_REACTOR_FUEL.getProvider(BfrLang.FUSION_REACTOR, tile)),
        TAB_EFFICIENCY(tile -> {
            if (tile instanceof TileEntityFusionReactorController) {
                return BfrContainerTypes.FUSION_REACTOR_EFFICIENCY.getProvider(BfrLang.EFFICIENCY_TAB, tile);
            }
            return null;
        }),
        TAB_STATS(tile -> {
            if (tile instanceof TileEntityFusionReactorController) {
                return BfrContainerTypes.FUSION_REACTOR_STATS.getProvider(BfrLang.FUSION_REACTOR, tile);
            }
            return null;
        });

        public static final IntFunction<ClickedGeneratorsTileButton> BY_ID = ByIdMap.continuous(ClickedGeneratorsTileButton::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, ClickedGeneratorsTileButton> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, ClickedGeneratorsTileButton::ordinal);

        private final Function<TileEntityMekanism, @Nullable MenuProvider> providerFromTile;
        @Nullable
        private final BiConsumer<RegistryFriendlyByteBuf, TileEntityMekanism> extraEncodingData;

        ClickedGeneratorsTileButton(Function<TileEntityMekanism, @Nullable MenuProvider> providerFromTile) {
            this(providerFromTile, null);
        }

        ClickedGeneratorsTileButton(Function<TileEntityMekanism, @Nullable MenuProvider> providerFromTile,
              @Nullable BiConsumer<RegistryFriendlyByteBuf, TileEntityMekanism> extraEncodingData) {
            this.providerFromTile = providerFromTile;
            this.extraEncodingData = extraEncodingData;
        }

        @Nullable
        @Contract("null -> null")
        public MenuProvider getProvider(@Nullable TileEntityMekanism tile) {
            return tile == null ? null : providerFromTile.apply(tile);
        }

        private void encodeExtraData(RegistryFriendlyByteBuf buffer, TileEntityMekanism tile) {
            if (extraEncodingData != null) {
                extraEncodingData.accept(buffer, tile);
            }
        }
    }
}