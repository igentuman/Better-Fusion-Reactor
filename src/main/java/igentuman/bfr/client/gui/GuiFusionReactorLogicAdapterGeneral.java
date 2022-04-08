package igentuman.bfr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nonnull;

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
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiFusionReactorLogicAdapterGeneral extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    public GuiFusionReactorLogicAdapterGeneral(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i) instanceof GuiRedstoneControlTab) {
                buttons.remove(i);
                break;
            }
        }
        for(int i = 0; i < children.size(); i++) {
            if(children.get(i) instanceof GuiRedstoneControlTab) {
                children.remove(i);
                break;
            }
        }
        addButton(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.INPUT));
        addButton(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.OUTPUT));


        addButton(new GuiElementHolder(this, 16, 31, 130, 90));
        addButton(new ToggleButton(this, 16, 19, 11, tile::isActiveCooled,
              () -> Mekanism.packetHandler.sendToServer(new PacketGuiInteract(GuiInteraction.NEXT_MODE, tile)), getOnHover(BfrLang.REACTOR_LOGIC_TOGGLE_COOLING)));
    }

    @Override
    protected void drawForegroundText(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_ACTIVE_COOLING.translate(EnumColor.RED, OnOff.of(tile.isActiveCooled())), 29, 20, titleTextColor(), 117);
        drawTextScaledBound(matrix, BfrLang.REACTOR_LOGIC_REDSTONE_MODE.translate(EnumColor.RED, tile.logicType), 16, 123, titleTextColor(), 144);
        drawCenteredText(matrix, MekanismLang.STATUS.translate(EnumColor.RED, tile.getRedstoneLevel() > 0 ? BfrLang.REACTOR_LOGIC_OUTPUTTING : MekanismLang.IDLE),
              0, imageWidth, 136, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}