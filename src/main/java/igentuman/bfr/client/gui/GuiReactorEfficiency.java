package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiReactorTab;
import igentuman.bfr.common.tile.reactor.TileEntityReactorController;
import mekanism.api.TileNetworkList;
import mekanism.client.gui.element.GuiEnergyInfo;
import mekanism.client.gui.element.GuiProgress;
import mekanism.client.gui.element.GuiProgress.IProgressInfoHandler;
import mekanism.client.gui.element.GuiProgress.ProgressBar;
import mekanism.client.gui.element.GuiSlot;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.gui.element.gauge.GuiGauge.Type;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.Mekanism;
import mekanism.common.inventory.container.ContainerNull;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GuiReactorEfficiency extends GuiReactorInfo {

    private GuiTextField injectionRateField;
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();

    public GuiReactorEfficiency(InventoryPlayer inventory, final TileEntityReactorController tile) {
        super(tile, new ContainerNull(inventory.player, tile));
        ResourceLocation resource = getGuiLocation();
        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.HEAT, resource));
        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.FUEL, resource));
        addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.STAT, resource));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        fontRenderer.drawString(tileEntity.getName(), 46, 6, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.current_reactivity") + ": " + String.format("%.1f",tileEntity.getReactor().getCurrentReactivity()), 16, 35, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.target_reactivity") + ": " + String.format("%.1f",tileEntity.getReactor().getTargetReactivity()), 16, 45, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.efficiency") + ": " + String.format("%.1f", tileEntity.getReactor().getEfficiency()) + "%", 16, 55, 0x404040);
        fontRenderer.drawString(LangUtils.localize("gui.shutdown_chances") + ": " + String.format("%.1f",tileEntity.getReactor().getShutDownChances()) + "%", 16, 65, 0x404040);
        String str = LangUtils.localize("gui.reactor.injectionRate") + ": " + (tileEntity.getReactor() == null ? "None" : tileEntity.getReactor().getInjectionRate());
        fontRenderer.drawString(str, 16, 75, 0x404040);
    }
}