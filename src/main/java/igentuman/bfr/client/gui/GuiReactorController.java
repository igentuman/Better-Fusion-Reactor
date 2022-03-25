package igentuman.bfr.client.gui;

import java.util.ArrayList;
import java.util.Arrays;

import igentuman.bfr.client.gui.element.GuiReactorTab;
import igentuman.bfr.common.inventory.container.ContainerReactorController;
import igentuman.bfr.common.tile.reactor.TileEntityReactorController;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiEnergyInfo;
import mekanism.client.gui.element.GuiSlot;
import mekanism.client.gui.element.GuiSlot.SlotType;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiReactorController extends GuiMekanismTile<TileEntityReactorController> {

    public GuiReactorController(InventoryPlayer inventory, final TileEntityReactorController tile) {
        super(tile, new ContainerReactorController(inventory, tile));
        if (tileEntity.isFormed()) {
            ResourceLocation resource = getGuiLocation();
            addGuiElement(new GuiEnergyInfo(() -> tileEntity.isFormed() ? Arrays.asList(
                  LangUtils.localize("gui.storing") + ": " + MekanismUtils.getEnergyDisplay(tileEntity.getEnergy(), tileEntity.getMaxEnergy()),
                  LangUtils.localize("gui.producing") + ": " + MekanismUtils.getEnergyDisplay(tileEntity.getReactor().getPassiveGeneration(false, true)) + "/t")
                                                                        : new ArrayList<>(), this, resource));
            addGuiElement(new GuiSlot(SlotType.NORMAL, this, resource, 79, 38));
            addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.HEAT, resource));
            addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.FUEL, resource));
            addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.STAT, resource));
            addGuiElement(new GuiReactorTab(this, tileEntity, GuiReactorTab.ReactorTab.EFFICIENCY, resource));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(tileEntity.getName(), 46, 6, 0x404040);
        if (tileEntity.getActive()) {
            fontRenderer.drawString(LangUtils.localize("gui.formed"), 8, 16, 0x404040);
        } else {
            fontRenderer.drawString(LangUtils.localize("gui.incomplete"), 8, 16, 0x404040);
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected ResourceLocation getGuiLocation() {
        return MekanismUtils.getResource(ResourceType.GUI, "GuiBlank.png");
    }
}