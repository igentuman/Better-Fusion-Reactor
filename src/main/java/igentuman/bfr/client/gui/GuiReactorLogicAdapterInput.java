package igentuman.bfr.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import igentuman.bfr.client.gui.button.GuiReactorLogicButton;
import igentuman.bfr.client.gui.element.GuiLogicAdapterTab;
import igentuman.bfr.client.gui.element.GuiReactorTab;
import igentuman.bfr.common.tile.reactor.TileEntityReactorLogicAdapter;
import mekanism.api.EnumColor;
import mekanism.api.TileNetworkList;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.button.GuiButtonDisableableImage;
import mekanism.common.Mekanism;
import mekanism.common.inventory.container.ContainerNull;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiReactorLogicAdapterInput extends GuiMekanismTile<TileEntityReactorLogicAdapter> {

    private List<GuiReactorLogicButton> typeButtons = new ArrayList<>();
    private int buttonID = 0;

    public GuiReactorLogicAdapterInput(InventoryPlayer inventory, final TileEntityReactorLogicAdapter tile) {
        super(tile, new ContainerNull(inventory.player, tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(new GuiLogicAdapterTab(this, tileEntity, GuiLogicAdapterTab.LogicAdapterTab.SETTINGS, resource));
        addGuiElement(new GuiLogicAdapterTab(this, tileEntity, GuiLogicAdapterTab.LogicAdapterTab.OUTPUT, resource));
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        int i = 0;
        for (TileEntityReactorLogicAdapter.ReactorLogic type : TileEntityReactorLogicAdapter.ReactorLogic.values()) {
            if(Objects.equals(type.getDirection(), "out")) continue;
            int typeShift = 22 * i;
            i++;
            GuiReactorLogicButton button = new GuiReactorLogicButton(buttonID++, guiLeft + 24, guiTop + 32 + typeShift, type, tileEntity, getGuiLocation());
            buttonList.add(button);
            typeButtons.add(button);
        }
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) throws IOException {
        super.actionPerformed(guibutton);
        for (GuiReactorLogicButton button : typeButtons) {
            if (guibutton.id == button.id) {
                Mekanism.packetHandler.sendToServer(new TileEntityMessage(tileEntity, TileNetworkList.withContents(1, button.getType().ordinal())));
                break;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(tileEntity.getName(), (xSize / 2) - (fontRenderer.getStringWidth(tileEntity.getName()) / 2), 6, 0x404040);
        renderScaledText(LangUtils.localize("gui.reactor_logic.input_tab"), 36, 20, 0x404040, 117);
        int xAxis = mouseX - guiLeft;
        int yAxis = mouseY - guiTop;
        int i = 0;
        for (GuiReactorLogicButton button : typeButtons) {
            TileEntityReactorLogicAdapter.ReactorLogic type = button.getType();
            int typeOffset = 22 * i;
            i++;
            renderItem(type.getRenderStack(), 27, 35 + typeOffset);
            fontRenderer.drawString(EnumColor.WHITE + type.getLocalizedName(), 46, 36 + typeOffset, 0x404040);
            if (button.isMouseOver()) {
                displayTooltips(MekanismUtils.splitTooltip(type.getDescription(), ItemStack.EMPTY), xAxis, yAxis);
            }
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return new ResourceLocation("bfr", ResourceType.GUI.getPrefix() + "GuiReactorLogicalAdapter.png");
    }
}