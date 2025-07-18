package igentuman.bfr.client.gui.element.button;

import igentuman.bfr.common.BfrLang;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.gui.tooltip.TooltipUtils;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class LaserReadyButton extends MekanismButton {
    private final int index;


    public LaserReadyButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 148, 22, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), (element, mouseX, mouseY) -> {return false;}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height);
    }

    @Override
    public void updateTooltip(int mouseX, int mouseY) {
        setTooltip(TooltipUtils.create(BfrLang.REACTOR_LASER_MIN_ENERGY.translate(EnergyDisplay.of(500000000L)), BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR.translate()));
    }

}