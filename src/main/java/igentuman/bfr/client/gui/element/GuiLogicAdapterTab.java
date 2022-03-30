package igentuman.bfr.client.gui.element;

import igentuman.bfr.common.tile.reactor.TileEntityReactorLogicAdapter;
import mekanism.api.Coord4D;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.tab.TabType;
import mekanism.common.Mekanism;
import mekanism.common.network.PacketSimpleGui.SimpleGuiMessage;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mekanism.client.gui.element.tab.GuiTabElementType;

@SideOnly(Side.CLIENT)
public class GuiLogicAdapterTab extends GuiTabElementType<TileEntityReactorLogicAdapter, GuiLogicAdapterTab.LogicAdapterTab> {

    public GuiLogicAdapterTab(IGuiWrapper gui, TileEntityReactorLogicAdapter tile, LogicAdapterTab type, ResourceLocation def) {
        super(gui, tile, type, def);
    }

    public enum LogicAdapterTab implements TabType {
        SETTINGS("GuiConfigurationTab.png", 15, "gui.logic_port.settings", 6),
        INPUT("GuiInputTab.png", 70, "gui.logic_port.input", 34),
        OUTPUT("GuiOutputTab.png", 71, "gui.logic_port.output", 62);

        private final String description;
        private final String path;
        private final int guiId;
        private final int yPos;

        LogicAdapterTab(String path, int id, String desc, int y) {
            this.path = path;
            guiId = id;
            description = desc;
            yPos = y;
        }

        @Override
        public ResourceLocation getResource() {
            return new ResourceLocation("bfr", ResourceType.GUI_ELEMENT.getPrefix() + path);
        }

        @Override
        public void openGui(TileEntity tile) {
            Mekanism.packetHandler.sendToServer(new SimpleGuiMessage(Coord4D.get(tile), 1, guiId));
        }

        @Override
        public String getDesc() {
            return LangUtils.localize(description);
        }

        @Override
        public int getYPos() {
            return yPos;
        }
    }
}