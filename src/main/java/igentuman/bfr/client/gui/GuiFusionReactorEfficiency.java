package igentuman.bfr.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.network.BfrPacketHandler;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.gui.element.button.TranslationButton;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.client.gui.element.window.GuiWindow;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiFusionReactorEfficiency extends GuiFusionReactorInfo {

    private MekanismButton reactivityUpButton;
    private MekanismButton reactivityDownButton;
    private MekanismButton reactorLaserReadyButton;
    private ArrayList<String> help = new ArrayList<>();
    private ArrayList<String> laser = new ArrayList<>();

    public GuiFusionReactorEfficiency(EmptyTileContainer<TileEntityFusionReactorController> container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        FusionReactorMultiblockData multiblock = tile.getMultiblock();
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            return Arrays.asList(MekanismLang.STORING.translate(EnergyDisplay.of(multiblock.energyContainer)),
                  BfrLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(multiblock.getPassiveGeneration(false, true))));
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

        addRenderableWidget(new GuiVerticalPowerBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_EFFICIENCY.translate(String.format("%.2f",multiblock.getEfficiency()) + "%");
            }
            @Override
            public double getLevel() {
                return multiblock.getEfficiency() / 100;
            }
        }, 102, 55));

        addRenderableWidget(new GuiVerticalPowerBar(this, new GuiBar.IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return BfrLang.REACTOR_ERROR_LEVEL.translate(String.format("%.2f",multiblock.getErrorLevel()) + "%");
            }
            @Override
            public double getLevel() {
                return multiblock.getErrorLevel() / 100;
            }
        }, 142, 55));

        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.HEAT));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.FUEL));
        addRenderableWidget(new GuiFusionReactorTab(this, tile, FusionReactorTab.STAT));

        help.add(BfrLang.REACTOR_HELP1.translate().toString());
        help.add(BfrLang.REACTOR_HELP2.translate().toString());
        help.add(BfrLang.REACTOR_HELP3.translate().toString());

        laser.add(BfrLang.REACTOR_LASER_MIN_ENERGY.translate(EnergyDisplay.of(FloatingLong.create(multiblock.laserShootMinEnergy))).toString());
        laser.add(BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR.translate().toString());

        reactivityUpButton = addRenderableWidget(new TranslationButton(this, 8, 56, 20, 20,
                BfrLang.REACTOR_BUTTON_REACTIVITY_UP,
                () -> BetterFusionReactor.packetHandler().sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.BfrGuiInteraction.REACTIVITY_UP, tile))));

        reactivityDownButton = addRenderableWidget(new TranslationButton(this, 8, 90, 20, 20,
                BfrLang.REACTOR_BUTTON_REACTIVITY_DOWN,
                () -> BetterFusionReactor.packetHandler().sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.BfrGuiInteraction.REACTIVITY_DOWN, tile))));

        reactorLaserReadyButton = addRenderableWidget(new TranslationButton(this, 8, 130, 120, 16,
                BfrLang.REACTOR_LASER_READY_BUTTON,
                null
                ));
    }

    @Override
    public void containerTick() {
        super.containerTick();
        updateEnabledButtons();
    }

    private void updateEnabledButtons() {
        FusionReactorMultiblockData multiblock = tile.getMultiblock();
        reactivityUpButton.active = multiblock.getAdjustment() == 0;
        reactivityDownButton.active = multiblock.getAdjustment() == 0;
        reactorLaserReadyButton.active = false;
        if(multiblock.getLaserShootCountdown() > 0) {
            reactorLaserReadyButton.visible = false;
        }
    }

    @Override
    protected void drawForegroundText(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        drawTitleText(matrix, BfrLang.FUSION_REACTOR.translate(), titleLabelY);
        FusionReactorMultiblockData multiblock = tile.getMultiblock();

        drawString(matrix, BfrLang.REACTOR_CR.translate(), 30, 35, titleTextColor());
        drawString(matrix, BfrLang.REACTOR_TR.translate(), 64, 35, titleTextColor());
        drawString(matrix, BfrLang.REACTOR_EF.translate(), 102, 35, titleTextColor());
        drawString(matrix, BfrLang.REACTOR_ER.translateColored(EnumColor.DARK_RED), 142, 35, titleTextColor());

        drawString(matrix, Component.literal(String.format("%.1f",multiblock.getCurrentReactivity())), 30, 45, titleTextColor());
        drawString(matrix, Component.literal(String.format("%.1f",multiblock.getTargetReactivity())), 64, 45, titleTextColor());
        drawString(matrix, Component.literal(String.format("%.1f",multiblock.getEfficiency())), 102, 45, titleTextColor());
        drawString(matrix, Component.literal(String.format("%.1f",multiblock.getErrorLevel())), 142, 45, titleTextColor());

        if (multiblock.isFormed()) {
            drawTextScaledBound(matrix, BfrLang.REACTOR_HEAT_MULTIPLIER.translate(String.format("%.2f",multiblock.getKt()*10)), 8, 120, titleTextColor(), 156);
            if(multiblock.getLaserShootCountdown() == 0) {
                reactorLaserReadyButton.visible = true;
            }
        }
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}