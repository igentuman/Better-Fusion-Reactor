package igentuman.bfr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import igentuman.bfr.client.gui.element.GuReactorLogicTab;
import igentuman.bfr.client.gui.element.button.ReactorLogicButton;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiElementHolder;
import mekanism.client.gui.element.button.ToggleButton;
import mekanism.client.gui.element.scroll.GuiScrollBar;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.common.Mekanism;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import mekanism.common.network.to_server.PacketGuiInteract;
import mekanism.common.network.to_server.PacketGuiInteract.GuiInteraction;
import mekanism.common.util.text.BooleanStateDisplay.OnOff;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class GuiFusionReactorLogicAdapterInput extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    private static final int DISPLAY_COUNT = 4;

    private GuiScrollBar scrollBar;

    public GuiFusionReactorLogicAdapterInput(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, PlayerInventory inv, ITextComponent title) {
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
        addButton(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.GENERAL));
        addButton(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.OUTPUT));
        scrollBar = addButton(new GuiScrollBar(this, 146, 31, 90, () -> tile.getInputModes().length, () -> DISPLAY_COUNT));
        for (int i = 0; i < DISPLAY_COUNT; i++) {
            int typeShift = 22 * i;
            addButton(new ReactorLogicButton<>(this, 17, 32 + typeShift, i, tile, scrollBar::getCurrentSelection, tile::getInputModes, type -> {
                if (type == null) {
                    return;
                }
                BetterFusionReactor.packetHandler.sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.BfrGuiInteraction.LOGIC_TYPE, tile, type.getId()));
            }));
        }
    }

    @Override
    protected void drawForegroundText(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
         super.drawForegroundText(matrix, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return super.mouseScrolled(mouseX, mouseY, delta) || scrollBar.adjustScroll(delta);
    }
}