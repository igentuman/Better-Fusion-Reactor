package igentuman.bfr.client.gui;

import java.util.List;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.EnergyDisplay;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class    GuiFusionReactorController extends GuiMekanismTile<TileEntityFusionReactorController, MekanismTileContainer<TileEntityFusionReactorController>> {

    public GuiFusionReactorController(MekanismTileContainer<TileEntityFusionReactorController> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += 10;
        inventoryLabelX += 5;
        titleLabelY = 5;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        if (tile.getMultiblock().isFormed()) {
            addRenderableWidget(new GuiEnergyTab(this, () -> {
                BFReactorMultiblockData multiblock = tile.getMultiblock();
                return List.of(MekanismLang.STORING.translate(EnergyDisplay.of(multiblock.energyContainer)),
                        GeneratorsLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(multiblock.getPassiveGeneration(false, true))));
            }));
            addRenderableWidget(new GuiHeatTab(this, () -> {
                BFReactorMultiblockData multiblock = tile.getMultiblock();
                Component transfer = MekanismUtils.getTemperatureDisplay(multiblock.lastTransferLoss, TemperatureUnit.KELVIN, false);
                Component environment = MekanismUtils.getTemperatureDisplay(multiblock.lastEnvironmentLoss, TemperatureUnit.KELVIN, false);
                return List.of(MekanismLang.TRANSFERRED_RATE.translate(transfer), MekanismLang.DISSIPATED_RATE.translate(environment));
            }));

            addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.HEAT));
            addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.FUEL));
            addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.STAT));
            addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.EFFICIENCY));
        }
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        drawScrollingString(guiGraphics, MekanismLang.MULTIBLOCK_FORMED.translate(), 0, 16, TextAlignment.LEFT, titleTextColor(), 13, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}