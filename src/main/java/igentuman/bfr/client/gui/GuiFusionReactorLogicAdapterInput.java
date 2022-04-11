package igentuman.bfr.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.client.gui.element.GuReactorLogicTab;
import igentuman.bfr.client.gui.element.button.ReactorLogicButton;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.network.to_server.PacketBfrGuiInteract;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.scroll.GuiScrollBar;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.common.inventory.container.tile.EmptyTileContainer;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import java.util.List;

public class GuiFusionReactorLogicAdapterInput extends GuiMekanismTile<TileEntityFusionReactorLogicAdapter, EmptyTileContainer<TileEntityFusionReactorLogicAdapter>> {

    private static final int DISPLAY_COUNT = 4;

    private GuiScrollBar scrollBar;

    public GuiFusionReactorLogicAdapterInput(EmptyTileContainer<TileEntityFusionReactorLogicAdapter> container, Inventory inv, Component title) {
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
        addRenderableWidget(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.GENERAL));
        addRenderableWidget(new GuReactorLogicTab(this, tile, GuReactorLogicTab.ReactorLogicTab.OUTPUT));
        scrollBar = addRenderableWidget(new GuiScrollBar(this, 146, 31, 90, () -> tile.getInputModes().length, () -> DISPLAY_COUNT));
        for (int i = 0; i < DISPLAY_COUNT; i++) {
            int typeShift = 22 * i;
            addRenderableWidget(new ReactorLogicButton<>(this, 17, 32 + typeShift, i, tile, scrollBar::getCurrentSelection, tile::getInputModes, type -> {
                if (type == null) {
                    return;
                }
                BetterFusionReactor.packetHandler().sendToServer(new PacketBfrGuiInteract(PacketBfrGuiInteract.BfrGuiInteraction.LOGIC_TYPE, tile, type.getId()));
            }));
        }
    }

    @Override
    protected void drawForegroundText(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
         super.drawForegroundText(matrix, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return super.mouseScrolled(mouseX, mouseY, delta) || scrollBar.adjustScroll(delta);
    }
}