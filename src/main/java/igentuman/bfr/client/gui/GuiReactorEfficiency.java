package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiReactorTab;
import igentuman.bfr.common.tile.reactor.TileEntityReactorController;
import mekanism.api.TileNetworkList;
import mekanism.client.gui.element.*;
import mekanism.common.Mekanism;
import mekanism.common.inventory.container.ContainerNull;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiReactorEfficiency extends GuiReactorInfo {

    private GuiButton plusButton;
    private GuiButton minusButton;

    public GuiReactorEfficiency(InventoryPlayer inventory, final TileEntityReactorController tile) {
        super(tile, new ContainerNull(inventory.player, tile));
        ResourceLocation resource = getGuiLocation();

        addGuiElement(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return LangUtils.localize("gui.reactor.current_reactivity") + ": " + String.format("%.1f",tileEntity.getReactor().getCurrentReactivity());
            }

            @Override
            public double getLevel() {
                return tileEntity.getReactor().getCurrentReactivity() / 100;
            }
        }, resource, 30, 55));

        addGuiElement(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return LangUtils.localize("gui.reactor.target_reactivity") + ": " + String.format("%.1f",tileEntity.getReactor().getTargetReactivity());
            }

            @Override
            public double getLevel() {
                return tileEntity.getReactor().getTargetReactivity() / 100;
            }
        }, resource, 64, 55));

        addGuiElement(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return LangUtils.localize("gui.reactor.efficiency") + ": " + String.format("%.2f",tileEntity.getReactor().getEfficiency()) + "%";
            }

            @Override
            public double getLevel() {
                return tileEntity.getReactor().getEfficiency() / 100;
            }
        }, resource, 102, 55));

        addGuiElement(new GuiPowerBar(this, new GuiPowerBar.IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return LangUtils.localize("gui.reactor.error_level") + ": " + String.format("%.2f",tileEntity.getReactor().getErrorLevel()) + "%";
            }

            @Override
            public double getLevel() {
                return tileEntity.getReactor().getErrorLevel() / 100;
            }
        }, resource, 142, 55));
        addGuiElement(new GuiEnergyInfo(() -> tileEntity.isFormed() ? Arrays.asList(
                LangUtils.localize("gui.storing") + ": " + MekanismUtils.getEnergyDisplay(tileEntity.getEnergy(), tileEntity.getMaxEnergy()),
                LangUtils.localize("gui.producing") + ": " + MekanismUtils.getEnergyDisplay(tileEntity.getReactor().getPassiveGeneration(false, true)) + "/t")
                : new ArrayList<>(), this, resource));

        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.HEAT, resource));
        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.FUEL, resource));
        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.STAT, resource));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        fontRenderer.drawString(tileEntity.getName(), 46, 6, 0x404040);
        fontRenderer.drawString("CR", 30, 35, 0x404040);
        fontRenderer.drawString("TR", 64, 35, 0x404040);
        fontRenderer.drawString("EF", 102, 35, 0x404040);
        fontRenderer.drawString("ER", 142, 35, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.reactor.heatMultiplier") + ": " +  String.format("%.2f", tileEntity.getReactor().getKt()), 8, 120, 0x404040);
        int xAxis = mouseX - this.guiLeft;
        int yAxis = mouseY - this.guiTop;
        if (this.plusButton.isMouseOver()) {
            this.displayTooltip(LangUtils.localize("gui.reactor.plus_5_reactivity"), xAxis, yAxis);
        } else if (this.minusButton.isMouseOver()) {
            this.displayTooltip(LangUtils.localize("gui.reactor.minus_5_reactivity"), xAxis, yAxis);
        }
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(this.plusButton = new GuiButton(1, guiLeft+8, guiTop+56, 20, 20, "+5"));
        this.buttonList.add(this.minusButton = new GuiButton(2, guiLeft+8, guiTop+90, 20, 20, "-5"));
        this.updateEnabledButtons();
    }

    public void updateScreen() {
        super.updateScreen();
        this.updateEnabledButtons();
    }

    private void updateEnabledButtons() {
        this.plusButton.enabled = this.tileEntity.getReactor().getAdjustment() == 0;
        this.minusButton.enabled = this.tileEntity.getReactor().getAdjustment() == 0;
    }

    protected void actionPerformed(GuiButton guibutton) throws IOException {
        super.actionPerformed(guibutton);
        switch(guibutton.id) {
            case 1:
                Mekanism.packetHandler.sendToServer(new TileEntityMessage(this.tileEntity, TileNetworkList.withContents(new Object[]{1})));
                break;
            case 2:
                Mekanism.packetHandler.sendToServer(new TileEntityMessage(this.tileEntity, TileNetworkList.withContents(new Object[]{2})));
                break;
        }
    }
}