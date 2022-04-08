package igentuman.bfr.common.network.to_server;

import mekanism.api.functions.TriConsumer;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * Used for informing the server that an action happened in a GUI
 */
public class PacketBfrGuiInteract implements IMekanismPacket {

    private final BfrGuiInteraction interaction;
    private final BlockPos tilePosition;
    private final double extra;

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, TileEntity tile) {
        this(interaction, tile.getBlockPos());
    }

    public PacketBfrGuiInteract(BfrGuiInteraction interaction, TileEntity tile, double extra) {
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
        PlayerEntity player = context.getSender();
        if (player != null) {
            TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level, tilePosition);
            if (tile != null) {
                interaction.consume(tile, player, extra);
            }
        }
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeEnum(interaction);
        buffer.writeBlockPos(tilePosition);
        buffer.writeDouble(extra);
    }

    public static PacketBfrGuiInteract decode(PacketBuffer buffer) {
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
        private final TriConsumer<TileEntityMekanism, PlayerEntity, Double> consumerForTile;

        BfrGuiInteraction(TriConsumer<TileEntityMekanism, PlayerEntity, Double> consumerForTile) {
            this.consumerForTile = consumerForTile;
        }

        public void consume(TileEntityMekanism tile, PlayerEntity player, double extra) {
            consumerForTile.accept(tile, player, extra);
        }
    }
}