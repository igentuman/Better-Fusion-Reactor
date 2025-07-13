package igentuman.bfr.client.gui;

import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.text.InputValidator;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract.GeneratorsGuiInteraction;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiFusionReactorFuel extends GuiFusionReactorInfo {

    private GuiTextField injectionRateField;

    public GuiFusionReactorFuel(EmptyTileContainer<TileEntityFusionReactorController> container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().deuteriumTank, () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.SMALL, this, 30, 64));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().fuelTank, () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.STANDARD, this, 84, 50));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getMultiblock().tritiumTank, () -> tile.getMultiblock().getChemicalTanks(null), GaugeType.SMALL, this, 138, 64));
        addRenderableWidget(new GuiProgress(() -> tile.getMultiblock().isBurning(), ProgressType.SMALL_RIGHT, this, 52, 76));
        addRenderableWidget(new GuiProgress(() -> tile.getMultiblock().isBurning(), ProgressType.SMALL_LEFT, this, 106, 76));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.HEAT));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.STAT));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.EFFICIENCY));
        injectionRateField = addRenderableWidget(new GuiTextField(this, 103, 115, 26, 11));
        injectionRateField.setInputValidator(InputValidator.DIGIT)
              .setEnterHandler(this::setInjection)
              .setMaxLength(2);
        setInitialFocus(injectionRateField);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_INJECTION_RATE.translate(tile.getMultiblock().getInjectionRate()), 0, 35, TextAlignment.CENTER, titleTextColor(), 16, false);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_EDIT_RATE.translate(), 4, 117, TextAlignment.RIGHT, titleTextColor(), 99, 2, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    private void setInjection() {
        if (!injectionRateField.getText().isEmpty()) {
            PacketUtils.sendToServer(new PacketBfrGuiInteract(GeneratorsGuiInteraction.INJECTION_RATE, tile, Integer.parseInt(injectionRateField.getText())));
            injectionRateField.setText("");
        }
    }
}