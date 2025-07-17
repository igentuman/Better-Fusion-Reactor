package igentuman.bfr.client.gui;

import igentuman.bfr.client.gui.element.GuiReactorLogicTab;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.button.ToggleButton;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import mekanism.common.util.text.BooleanStateDisplay.OnOff;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiFusionReactorLogicAdapter extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    public GuiFusionReactorLogicAdapter(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, Inventory inv, Component title) {
        super(container, inv, title);
        imageWidth += 20;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiReactorLogicTab(this, tile, GuiReactorLogicTab.ReactorLogicTab.INPUT));
        addRenderableWidget(new GuiReactorLogicTab(this, tile, GuiReactorLogicTab.ReactorLogicTab.OUTPUT));

        addRenderableWidget(new ToggleButton(this, 6, 19, 11, tile::isActiveCooled,
              (element, mouseX, mouseY) -> PacketUtils.sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, ((GuiFusionReactorLogicAdapter) element.gui()).tile))))
              .setTooltip(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.75f, 0.75f, 1);
        Font font = Minecraft.getInstance().gui.getFont();
        guiGraphics.drawWordWrap(font, BfrLang.REACTOR_LOGIC_HELP1.translate(), 10, 65, 220, titleTextColor());
        guiGraphics.drawWordWrap(font, BfrLang.REACTOR_LOGIC_HELP2.translate(), 10, 85, 220, titleTextColor());
        guiGraphics.drawWordWrap(font, BfrLang.REACTOR_LOGIC_HELP3.translate(), 10, 105, 220, titleTextColor());
        guiGraphics.drawWordWrap(font, BfrLang.REACTOR_LOGIC_HELP4.translate(), 10, 125, 220, titleTextColor());
        guiGraphics.pose().popPose();
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_LOGIC_ACTIVE_COOLING.translate(EnumColor.RED, OnOff.of(tile.isActiveCooled())), 15, 20, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_LOGIC_REDSTONE_MODE.translate(EnumColor.RED, tile.logicType), 1, 123, TextAlignment.LEFT, titleTextColor(), 4, false);
        drawScrollingString(guiGraphics, MekanismLang.STATUS.translate(EnumColor.RED, tile.checkMode() > 0 ? GeneratorsLang.REACTOR_LOGIC_OUTPUTTING : MekanismLang.IDLE),
                1, 136, TextAlignment.LEFT, titleTextColor(), 4, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }
}