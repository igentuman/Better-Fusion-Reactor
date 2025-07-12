package igentuman.bfr.common.network.to_server;

import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import mekanism.api.functions.TriConsumer;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Used for informing the server that an action happened in a GUI
 */
public record PacketGeneratorsGuiInteract(GeneratorsGuiInteraction interaction, BlockPos tilePosition, double extra) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketGeneratorsGuiInteract> TYPE = new CustomPacketPayload.Type<>(BetterFusionReactor.rl("gui_interact"));
    public static final StreamCodec<ByteBuf, PacketGeneratorsGuiInteract> STREAM_CODEC = StreamCodec.composite(
          GeneratorsGuiInteraction.STREAM_CODEC, PacketGeneratorsGuiInteract::interaction,
          BlockPos.STREAM_CODEC, PacketGeneratorsGuiInteract::tilePosition,
          ByteBufCodecs.DOUBLE, PacketGeneratorsGuiInteract::extra,
          PacketGeneratorsGuiInteract::new
    );

    public PacketGeneratorsGuiInteract(GeneratorsGuiInteraction interaction, BlockEntity tile) {
        this(interaction, tile.getBlockPos());
    }

    public PacketGeneratorsGuiInteract(GeneratorsGuiInteraction interaction, BlockEntity tile, double extra) {
        this(interaction, tile.getBlockPos(), extra);
    }

    public PacketGeneratorsGuiInteract(GeneratorsGuiInteraction interaction, BlockPos tilePosition) {
        this(interaction, tilePosition, 0);
    }

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketGeneratorsGuiInteract> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        Player player = context.player();
        TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), tilePosition);
        if (tile != null) {
            interaction.consume(tile, player, extra);
        }
    }

    public enum GeneratorsGuiInteraction {
        INJECTION_RATE((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorBlock reactorBlock) {
                reactorBlock.setInjectionRateFromPacket((int) Math.round(extra));
            }
        }),
        LOGIC_TYPE((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorLogicAdapter logicAdapter) {
                logicAdapter.setLogicTypeFromPacket(FusionReactorLogic.BY_ID.apply((int) Math.round(extra)));
            }
        });

        public static final IntFunction<GeneratorsGuiInteraction> BY_ID = ByIdMap.continuous(GeneratorsGuiInteraction::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, GeneratorsGuiInteraction> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, GeneratorsGuiInteraction::ordinal);

        private final TriConsumer<TileEntityMekanism, Player, Double> consumerForTile;

        GeneratorsGuiInteraction(TriConsumer<TileEntityMekanism, Player, Double> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityMekanism tile, Player player, double extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }
}