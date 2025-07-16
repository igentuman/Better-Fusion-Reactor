package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiReactorLogicTab;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiElementHolder;
import mekanism.client.gui.element.button.ToggleButton;
import mekanism.client.gui.element.scroll.GuiScrollBar;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import mekanism.common.util.text.BooleanStateDisplay.OnOff;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiFusionReactorLogicAdapter extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    private static final int DISPLAY_COUNT = 4;

    private GuiScrollBar scrollBar;

    public GuiFusionReactorLogicAdapter(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, Inventory inv, Component title) {
        super(container, inv, title);
        imageWidth += 20;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiReactorLogicTab(this, tile, GuiReactorLogicTab.ReactorLogicTab.INPUT));
        addRenderableWidget(new GuiReactorLogicTab(this, tile, GuiReactorLogicTab.ReactorLogicTab.OUTPUT));
        addRenderableWidget(new GuiElementHolder(this, 26, 31, 130, 90));
        addRenderableWidget(new ToggleButton(this, 26, 19, 11, tile::isActiveCooled,
              (element, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, ((GuiFusionReactorLogicAdapter) element.gui()).tile))))
              .setTooltip(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);

        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_HELP1.translate(), 0, 45, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_HELP2.translate(), 0, 55, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_HELP3.translate(), 0, 65, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_HELP4.translate(), 0, 75, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_LOGIC_ACTIVE_COOLING.translate(EnumColor.RED, OnOff.of(tile.isActiveCooled())), 29, 20, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_LOGIC_REDSTONE_MODE.translate(EnumColor.RED, tile.logicType), 16, 123, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, MekanismLang.STATUS.translate(EnumColor.RED, tile.checkMode() > 0 ? GeneratorsLang.REACTOR_LOGIC_OUTPUTTING : MekanismLang.IDLE),
                0, 136, TextAlignment.LEFT, titleTextColor(), 4, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY) || scrollBar.adjustScroll(deltaY);
    }
}