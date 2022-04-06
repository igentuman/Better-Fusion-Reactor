package igentuman.bfr.common.network;

import mekanism.common.network.BasePacketHandler;
import igentuman.bfr.common.MekanismGenerators;
import igentuman.bfr.common.network.to_server.PacketGeneratorsGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketGeneratorsGuiInteract;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class GeneratorsPacketHandler extends BasePacketHandler {

    private static final SimpleChannel netHandler = createChannel(MekanismGenerators.rl(MekanismGenerators.MODID));

    @Override
    protected SimpleChannel getChannel() {
        return netHandler;
    }

    @Override
    public void initialize() {
        //Client to server messages
        registerClientToServer(PacketGeneratorsGuiButtonPress.class, PacketGeneratorsGuiButtonPress::decode);
        registerClientToServer(PacketGeneratorsGuiInteract.class, PacketGeneratorsGuiInteract::decode);
        //Server to client messages
    }
}