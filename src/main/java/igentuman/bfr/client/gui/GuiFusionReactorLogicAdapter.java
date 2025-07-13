package igentuman.bfr.client.gui;

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
import igentuman.bfr.client.gui.element.button.ReactorLogicButton;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract.GeneratorsGuiInteraction;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter.FusionReactorLogic;
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
        addRenderableWidget(new GuiElementHolder(this, 26, 31, 130, 90));
        addRenderableWidget(new ToggleButton(this, 26, 19, 11, tile::isActiveCooled,
              (element, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, ((GuiFusionReactorLogicAdapter) element.gui()).tile))))
              .setTooltip(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING);
        scrollBar = addRenderableWidget(new GuiScrollBar(this, 156, 31, 90, () -> tile.getModes().length, () -> DISPLAY_COUNT));
        for (int i = 0; i < DISPLAY_COUNT; i++) {
            int typeShift = 22 * i;
            addRenderableWidget(new ReactorLogicButton<>(this, 27, 32 + typeShift, i, tile, FusionReactorLogic.class, scrollBar::getCurrentSelection, tile::getModes, this::changeLogic));
        }
    }

    private void changeLogic(FusionReactorLogic type) {
        if (type != null) {
            PacketUtils.sendToServer(new PacketBfrGuiInteract(GeneratorsGuiInteraction.LOGIC_TYPE, tile, type.ordinal()));
        }
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_ACTIVE_COOLING.translate(EnumColor.RED, OnOff.of(tile.isActiveCooled())), 23, 20, TextAlignment.LEFT,
              titleTextColor(), getXSize() - 23, 16, false);
        drawScrollingString(guiGraphics, BfrLang.REACTOR_LOGIC_REDSTONE_MODE.translate(EnumColor.RED, tile.logicType), 0, 123, TextAlignment.CENTER, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, MekanismLang.STATUS.translate(EnumColor.RED, tile.checkMode() ? BfrLang.REACTOR_LOGIC_OUTPUTTING : MekanismLang.IDLE),
              0, 136, TextAlignment.CENTER, titleTextColor(), 4, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY) || scrollBar.adjustScroll(deltaY);
    }
}