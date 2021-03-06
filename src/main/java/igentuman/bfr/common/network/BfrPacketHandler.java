package igentuman.bfr.common.network;

import mekanism.common.network.BasePacketHandler;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import mekanism.generators.common.MekanismGenerators;
import net.minecraftforge.network.simple.SimpleChannel;

public class BfrPacketHandler extends BasePacketHandler {

    private static final SimpleChannel netHandler = createChannel(BetterFusionReactor.rl(BetterFusionReactor.MODID), BetterFusionReactor.instance.versionNumber);

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