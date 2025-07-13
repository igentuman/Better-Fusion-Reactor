package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiCustomVerticalBar;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.client.gui.element.button.HeatMultiplierButton;
import igentuman.bfr.client.gui.element.button.HelpButton;
import igentuman.bfr.client.gui.element.button.LaserReadyButton;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.gui.element.button.TranslationButton;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class GuiFusionReactorEfficiency extends GuiFusionReactorInfo {

    private MekanismButton reactivityUpButton;
    private MekanismButton reactivityDownButton;
    private LaserReadyButton reactorLaserReadyButton;
    private HelpButton helpButton;
    private HeatMultiplierButton heatMultiplierButton;

    public GuiFusionReactorEfficiency(EmptyTileContainer<TileEntityFusionReactorController> container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        BFReactorMultiblockData multiblock = tile.getMultiblock();
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            return Arrays.asList(MekanismLang.STORING.translate(EnergyDisplay.of(multiblock.energyContainer)),
                    GeneratorsLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(multiblock.getPassiveGeneration(false, true))));
        }));

        addRenderableWidget(new GuiVerticalPowerBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_CURRENT_REACTIVITY.translate(String.format("%.2f",multiblock.getCurrentReactivity()));
            }

            @Override
            public double getLevel() {
                return multiblock.getCurrentReactivity() / 100;
            }
        }, 30, 55));

        addRenderableWidget(new GuiVerticalPowerBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_TARGET_REACTIVITY.translate(String.format("%.2f",multiblock.getTargetReactivity()));
            }
            @Override
            public double getLevel() {
                return multiblock.getTargetReactivity() / 100;
            }
        }, 64, 55));

        addRenderableWidget(new GuiCustomVerticalBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_EFFICIENCY.translate(String.format("%.2f",multiblock.getEfficiency()) + "%");
            }
            @Override
            public double getLevel() {
                return multiblock.getEfficiency() / 100;
            }
        }, 102, 55, BetterFusionReactor.rl("gui/bar/vertical_power.png")));

        addRenderableWidget(new GuiCustomVerticalBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_ERROR_LEVEL.translate(String.format("%.2f",multiblock.getErrorLevel()) + "%");
            }
            @Override
            public double getLevel() {
                return multiblock.getErrorLevel() / 100;
            }
        }, 142, 55, BetterFusionReactor.rl("gui/bar/vertical_power.png")));

        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.HEAT));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.FUEL));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.STAT));

        reactivityUpButton = addRenderableWidget(new TranslationButton(this, 8, 55, 20, 20,
                BfrLang.REACTOR_BUTTON_REACTIVITY_UP,
                (button, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.GeneratorsGuiInteraction.CHANGE_CR, tile, 5))));

        reactivityDownButton = addRenderableWidget(new TranslationButton(this, 8, 89, 20, 20,
                BfrLang.REACTOR_BUTTON_REACTIVITY_DOWN,
                (button, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.GeneratorsGuiInteraction.CHANGE_CR, tile, -5))));

        reactorLaserReadyButton = addRenderableWidget(new LaserReadyButton(this, 8, 132, 120));
        helpButton = addRenderableWidget(new HelpButton(this, 152, 6, 121));
        heatMultiplierButton = addRenderableWidget(new HeatMultiplierButton(this, 152, 118, 122));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        updateEnabledButtons();
    }

    private void updateEnabledButtons() {
        BFReactorMultiblockData multiblock = tile.getMultiblock();
        reactivityUpButton.active = multiblock.getAdjustment() == 0;
        reactivityDownButton.active = multiblock.getAdjustment() == 0;
        helpButton.active = false;
        heatMultiplierButton.active = false;
        reactorLaserReadyButton.active = false;

    }

    @Override
    protected void drawForegroundText(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        drawTitleText(matrix, GeneratorsLang.FUSION_REACTOR.translate(), titleLabelY);
        BFReactorMultiblockData multiblock = tile.getMultiblock();
        Font font = Minecraft.getInstance().font;
        matrix.drawString(font, BfrLang.REACTOR_CR.translate(), 30, 35, titleTextColor());
        matrix.drawString(font, BfrLang.REACTOR_TR.translate(), 64, 35, titleTextColor());
        matrix.drawString(font, BfrLang.REACTOR_EF.translate(), 102, 35, titleTextColor());
        matrix.drawString(font, BfrLang.REACTOR_ER.translateColored(EnumColor.DARK_RED), 142, 35, titleTextColor());

        matrix.drawString(font, Component.literal(String.format("%.1f",multiblock.getCurrentReactivity())), 30, 45, titleTextColor());
        matrix.drawString(font, Component.literal(String.format("%.1f",multiblock.getTargetReactivity())), 64, 45, titleTextColor());
        matrix.drawString(font, Component.literal(String.format("%.1f",multiblock.getEfficiency())), 102, 45, titleTextColor());
        matrix.drawString(font, Component.literal(String.format("%.1f",multiblock.getErrorLevel())), 142, 45, titleTextColor());

        if (multiblock.isFormed() && multiblock.isBurning()) {
            drawScaledScrollingString(matrix, BfrLang.REACTOR_HEAT_MULTIPLIER.translate(String.format("%.2f",multiblock.getKt()*10)), 8, 120, TextAlignment.CENTER, titleTextColor(), 156, false, 0.5f);
            heatMultiplierButton.visible = true;
            if(multiblock.getLaserShootCountdown() == 0) {
                reactorLaserReadyButton.visible = true;
            }
        } else {
            heatMultiplierButton.visible = false;
            reactorLaserReadyButton.visible = false;
        }
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}