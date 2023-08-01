package igentuman.bfr.common.network.to_server;

import java.util.function.BiFunction;

import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.util.WorldUtils;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.registries.BfrContainerTypes;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;


/**
 * Used for informing the server that a click happened in a GUI and the gui window needs to change
 */
public class PacketBfrGuiButtonPress implements IMekanismPacket {

    private final ClickedGeneratorsTileButton tileButton;
    private final int extra;
    private final BlockPos tilePosition;

    public PacketBfrGuiButtonPress(ClickedGeneratorsTileButton buttonClicked, BlockPos tilePosition) {
        this(buttonClicked, tilePosition, 0);
    }

    public PacketBfrGuiButtonPress(ClickedGeneratorsTileButton buttonClicked, BlockPos tilePosition, int extra) {
        this.tileButton = buttonClicked;
        this.tilePosition = tilePosition;
        this.extra = extra;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player != null) {//If we are on the server (the only time we should be receiving this packet), let forge handle switching the Gui
            TileEntityMekanism tile = WorldUtils.getTileEntity(TileEntityMekanism.class, player.level(), tilePosition);
            if (tile != null) {
                MenuProvider provider = tileButton.getProvider(tile, extra);
                if (provider != null) {
                    //Ensure valid data
                    NetworkHooks.openScreen(player, provider, buf -> {
                        buf.writeBlockPos(tilePosition);
                        buf.writeVarInt(extra);
                    });
                }
            }
        }
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeEnum(tileButton);
        buffer.writeBlockPos(tilePosition);
        buffer.writeVarInt(extra);
    }

    public static PacketBfrGuiButtonPress decode(FriendlyByteBuf buffer) {
        return new PacketBfrGuiButtonPress(buffer.readEnum(ClickedGeneratorsTileButton.class), buffer.readBlockPos(), buffer.readVarInt());
    }

    public enum ClickedGeneratorsTileButton {
        TAB_HEAT((tile, extra) -> BfrContainerTypes.FUSION_REACTOR_HEAT.getProvider(BfrLang.HEAT_TAB, tile)),
        TAB_FUEL((tile, extra) -> BfrContainerTypes.FUSION_REACTOR_FUEL.getProvider(BfrLang.FUEL_TAB, tile)),
        TAB_EFFICIENCY((tile, extra) -> {
           if (tile instanceof TileEntityFusionReactorController) {
                return BfrContainerTypes.FUSION_REACTOR_EFFICIENCY.getProvider(BfrLang.EFFICIENCY_TAB, tile);
            }
            return null;
        }),
        TAB_STATS((tile, extra) -> {
           if (tile instanceof TileEntityFusionReactorController) {
                return BfrContainerTypes.FUSION_REACTOR_STATS.getProvider(BfrLang.STATS_TAB, tile);
            }
            return null;
        }),
        TAB_LOGIC_GENERAL((tile, extra) -> {
            if (tile instanceof TileEntityFusionReactorLogicAdapter) {
                return BfrContainerTypes.FUSION_REACTOR_LOGIC_GENERAL.getProvider(BfrLang.LOGIC_GENERAL_TAB, tile);
            }
            return null;
        }),
        TAB_LOGIC_INPUT((tile, extra) -> {
            if (tile instanceof TileEntityFusionReactorLogicAdapter) {
                return BfrContainerTypes.FUSION_REACTOR_LOGIC_IN.getProvider(BfrLang.LOGIC_IN_TAB, tile);
            }
            return null;
        }),
        TAB_LOGIC_OUTPUT((tile, extra) -> {
            if (tile instanceof TileEntityFusionReactorLogicAdapter) {
                return BfrContainerTypes.FUSION_REACTOR_LOGIC_OUT.getProvider(BfrLang.LOGIC_OUT_TAB, tile);
            }
            return null;
        });

        private final BiFunction<TileEntityMekanism, Integer, MenuProvider> providerFromTile;

        ClickedGeneratorsTileButton(BiFunction<TileEntityMekanism, Integer, MenuProvider> providerFromTile) {
            this.providerFromTile = providerFromTile;
        }

        public MenuProvider getProvider(TileEntityMekanism tile, int extra) {
            return providerFromTile.apply(tile, extra);
        }
    }
}