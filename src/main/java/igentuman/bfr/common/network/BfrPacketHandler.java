package igentuman.bfr.common.network;

import mekanism.common.lib.Version;
import mekanism.common.network.BasePacketHandler;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.network.to_server.PacketBfrTileButtonPress;
import net.neoforged.bus.api.IEventBus;

public class BfrPacketHandler extends BasePacketHandler {

    public BfrPacketHandler(IEventBus modEventBus, Version version) {
        super(modEventBus, version);
    }

    @Override
    protected void registerClientToServer(PacketRegistrar registrar) {
        registrar.play(PacketBfrTileButtonPress.TYPE, PacketBfrTileButtonPress.STREAM_CODEC);
        registrar.play(PacketBfrGuiInteract.TYPE, PacketBfrGuiInteract.STREAM_CODEC);
    }

    @Override
    protected void registerServerToClient(PacketRegistrar registrar) {
    }
}