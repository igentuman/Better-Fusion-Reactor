package igentuman.bfr.common.network;

import mekanism.common.network.BasePacketHandler;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BfrPacketHandler extends BasePacketHandler {

    private static final SimpleChannel netHandler = createChannel(BetterFusionReactor.rl(BetterFusionReactor.MODID));

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        //Client to server messages
        registerClientToServer(PacketBfrGuiButtonPress.class, PacketBfrGuiButtonPress::decode);
        registerClientToServer(PacketBfrGuiInteract.class, PacketBfrGuiInteract::decode);
        //Server to client messages
    }
}