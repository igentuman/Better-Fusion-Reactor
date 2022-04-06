package igentuman.bfr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import javax.annotation.Nonnull;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.text.EnergyDisplay;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.common.GeneratorsLang;
import igentuman.bfr.common.content.fusion.FusionReactorMultiblockData;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiFusionReactorController extends GuiMekanismTile<TileEntityFusionReactorController, MekanismTileContainer<TileEntityFusionReactorController>> {

    public GuiFusionReactorController(MekanismTileContainer<TileEntityFusionReactorController> container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY = 5;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        if (tile.getMultiblock().isFormed()) {
            addButton(new GuiEnergyTab(this, () -> {
                FusionReactorMultiblockData multiblock = tile.getMultiblock();
                return Arrays.asList(MekanismLang.STORING.translate(EnergyDisplay.of(multiblock.energyContainer)),
                      GeneratorsLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(multiblock.getPassiveGeneration(false, true))));
            }));
            addButton(new GuiFusionReactorTab(this, tile, FusionReactorTab.HEAT));
            addButton(new GuiFusionReactorTab(this, tile, FusionReactorTab.FUEL));
            addButton(new GuiFusionReactorTab(this, tile, FusionReactorTab.STAT));
        }
    }

    @Override
    protected void drawForegroundText(@Nonnull MatrixStack matrix, int mouseX, int mouseY) {
        drawTitleText(matrix, GeneratorsLang.FUSION_REACTOR.translate(), titleLabelY);
        drawString(matrix, MekanismLang.MULTIBLOCK_FORMED.translate(), 8, 16, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}