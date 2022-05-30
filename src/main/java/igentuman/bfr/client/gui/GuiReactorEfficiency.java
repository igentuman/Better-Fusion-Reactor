package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiEfficiencyBar;
import igentuman.bfr.client.gui.element.GuiHeatBar;
import igentuman.bfr.client.gui.element.GuiReactorTab;
import igentuman.bfr.common.tile.reactor.TileEntityReactorController;
import mekanism.api.EnumColor;
import mekanism.api.TileNetworkList;
import mekanism.client.gui.button.GuiButtonDisableableImage;
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
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiReactorEfficiency extends GuiReactorInfo {

    private GuiButton plusButton;
    private GuiButton minusButton;
    private GuiButton helpButton;
    private GuiButton laserReadyButton;
    private ArrayList<String> help = new ArrayList<>();
    private ArrayList<String> laser = new ArrayList<>();

    public GuiReactorEfficiency(InventoryPlayer inventory, final TileEntityReactorController tile) {
        super(tile, new ContainerNull(inventory.player, tile));
        ResourceLocation resource = getGuiLocation();
        help.add(LangUtils.localize("gui.reactor.efficiency_help1"));
        help.add(LangUtils.localize("gui.reactor.efficiency_help2"));
        help.add(LangUtils.localize("gui.reactor.efficiency_help3"));

        laser.add(LangUtils.localize("gui.reactor.laserMinEnergy") + ": " + MekanismUtils.getEnergyDisplay(tileEntity.getReactor().laserShootMinEnergy));
        laser.add(LangUtils.localize("gui.reactor.laserMinEnergy.desc"));
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

        addGuiElement(new GuiEfficiencyBar(this, new GuiEfficiencyBar.IPowerInfoHandler() {
            @Override
            public String getTooltip() {
                return LangUtils.localize("gui.reactor.efficiency") + ": " + String.format("%.2f",tileEntity.getReactor().getEfficiency()) + "%";
            }

            @Override
            public double getLevel() {
                return tileEntity.getReactor().getEfficiency() / 100;
            }
        }, resource, 102, 55));

        addGuiElement(new GuiHeatBar(this, new GuiHeatBar.IPowerInfoHandler() {
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
        fontRenderer.drawString(String.format("%.1f",tileEntity.getReactor().getCurrentReactivity()), 30, 45, 0x404040);
        fontRenderer.drawString(String.format("%.1f",tileEntity.getReactor().getTargetReactivity()), 64, 45, 0x404040);
        fontRenderer.drawString(String.format("%.1f",tileEntity.getReactor().getEfficiency()), 102, 45, 0x404040);
        fontRenderer.drawString(String.format("%.1f",tileEntity.getReactor().getErrorLevel()), 142, 45, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.reactor.heatMultiplier") + ": " +  String.format("%.2f", tileEntity.getReactor().getKt() * 10), 8, 120, 0x404040);
        if(tileEntity.getReactor().getLaserShootCountdown() == 0) {
            laserReadyButton.visible = true;
        }
        int xAxis = mouseX - this.guiLeft;
        int yAxis = mouseY - this.guiTop;


        if(this.laserReadyButton.isMouseOver()) {
            this.displayTooltips(laser, xAxis, yAxis);
        } else if(this.helpButton.isMouseOver()) {
            this.displayTooltips(help, xAxis, yAxis);
        } else if (this.plusButton.isMouseOver()) {
            this.displayTooltip(LangUtils.localize("gui.reactor.plus_5_reactivity"), xAxis, yAxis);
        } else if (this.minusButton.isMouseOver()) {
            this.displayTooltip(LangUtils.localize("gui.reactor.minus_5_reactivity"), xAxis, yAxis);
        }
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(this.plusButton = new GuiButton(1, guiLeft+8, guiTop+56, 20, 20, "+5"));
        this.buttonList.add(this.minusButton = new GuiButton(2, guiLeft+8, guiTop+90, 20, 20, "-5"));
        this.buttonList.add(this.helpButton = new GuiButton(99, guiLeft + 156, guiTop + 6, 14, 14, "?"));
        this.buttonList.add(this.laserReadyButton = new GuiButton(98, guiLeft + 8, guiTop + 130, 120, 16, EnumColor.DARK_GREEN + LangUtils.localize("gui.reactor.laser_ready_button")));
        this.updateEnabledButtons();
    }

    public void updateScreen() {
        super.updateScreen();
        this.updateEnabledButtons();
    }

    private void updateEnabledButtons() {
        this.plusButton.enabled = this.tileEntity.getReactor().getAdjustment() == 0;
        this.minusButton.enabled = this.tileEntity.getReactor().getAdjustment() == 0;
        this.helpButton.enabled = false;
        this.laserReadyButton.enabled = false;
        if(tileEntity.getReactor().getLaserShootCountdown() > 0) {
            this.laserReadyButton.visible = false;
        }
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