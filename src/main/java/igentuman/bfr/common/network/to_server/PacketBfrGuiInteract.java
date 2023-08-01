package igentuman.bfr.common.network.to_server;

import mekanism.api.functions.TriConsumer;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;


/**
 * Used for informing the server that an action happened in a GUI
 */
public class PacketBfrGuiInteract implements IMekanismPacket {

    private final BfrGuiInteraction interaction;
    private final BlockPos tilePosition;
    private final double extra;

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, BlockEntity tile) {
        this(interaction, tile.getBlockPos());
    }

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, BlockEntity tile, double extra) {
        this(interaction, tile.getBlockPos(), extra);
    }

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, BlockPos tilePosition) {
        this(interaction, tilePosition, 0);
    }

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, BlockPos tilePosition, double extra) {
        this.interaction = interaction;
        this.tilePosition = tilePosition;
        this.extra = extra;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player player = context.getSender();
        if (player != null) {
            TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), tilePosition);
            if (tile != null) {
                interaction.consume(tile, player, extra);
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(interaction);
        buffer.writeBlockPos(tilePosition);
        buffer.writeDouble(extra);
    }

    public static PacketBfrGuiInteract decode(FriendlyByteBuf buffer) {
        return new PacketBfrGuiInteract(buffer.readEnum(BfrGuiInteraction.class), buffer.readBlockPos(), buffer.readDouble());
    }

    public enum BfrGuiInteraction {
        INJECTION_RATE((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorBlock) {
                ((TileEntityFusionReactorController) tile).setInjectionRateFromPacket((int) Math.round(extra));
            }
        }),
        LOGIC_TYPE((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorLogicAdapter) {
                ((TileEntityFusionReactorLogicAdapter) tile).setLogicTypeFromPacket(FusionReactorLogic.byIndexStatic((int) Math.round(extra)));
            }
        }),
        REACTIVITY_UP((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorBlock) {
                ((TileEntityFusionReactorBlock) tile).adjustReactivityFromPacket(5F);
            }
        }),
        REACTIVITY_DOWN((tile, player, extra) -> {
            if (tile instanceof TileEntityFusionReactorBlock) {
                ((TileEntityFusionReactorBlock) tile).adjustReactivityFromPacket(-5F);
            }
        });
        private final TriConsumer<TileEntityMekanism, Player, Double> consumerForTile;

        BfrGuiInteraction(TriConsumer<TileEntityMekanism, Player, Double> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityMekanism tile, Player player, double extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }
}