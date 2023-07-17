package igentuman.bfr.client.gui;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.client.gui.element.GuReactorLogicTab;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiElementHolder;
import mekanism.client.gui.element.button.ToggleButton;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import mekanism.common.util.text.BooleanStateDisplay.OnOff;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class GuiFusionReactorLogicAdapterGeneral extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    public GuiFusionReactorLogicAdapterGeneral(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        for(int i = 0; i < renderables.size(); i++) {
            if(renderables.get(i) instanceof GuiRedstoneControlTab) {
                renderables.remove(i);
                break;
            }
        }
        for(int i = 0; i < ((List<GuiEventListener>) children()).size(); i++) {
            if(((List<GuiEventListener>) children()).get(i) instanceof GuiRedstoneControlTab) {
                ((List<GuiEventListener>) children()).remove(i);
                break;
            }
        }
        addRenderableWidget(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.INPUT));
        addRenderableWidget(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.OUTPUT));



        addRenderableWidget(new ToggleButton(this, 16, 19, 11, tile::isActiveCooled,
              () -> Mekanism.packetHandler().sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, tile)), getOnHover(GeneratorsLang.REACTOR_LOGIC_TOGGLE_COOLING)));
    }

    @Override
    protected void drawForegroundText(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_HELP1.translate(), 10, 45, titleTextColor(), 150);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_HELP2.translate(), 10, 55, titleTextColor(), 150);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_HELP3.translate(), 10, 65, titleTextColor(), 150);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_HELP4.translate(), 10, 75, titleTextColor(), 150);
        drawTextScaledBound(matrix, GeneratorsLang.REACTOR_LOGIC_ACTIVE_COOLING.translate(EnumColor.RED, OnOff.of(tile.isActiveCooled())), 29, 20, titleTextColor(), 117);
        drawTextScaledBound(matrix, GeneratorsLang.REACTOR_LOGIC_REDSTONE_MODE.translate(EnumColor.RED, tile.logicType), 16, 123, titleTextColor(), 144);
        drawCenteredText(matrix, MekanismLang.STATUS.translate(EnumColor.RED, tile.getRedstoneLevel() > 0 ? GeneratorsLang.REACTOR_LOGIC_OUTPUTTING : MekanismLang.IDLE),
              0, imageWidth, 136, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}