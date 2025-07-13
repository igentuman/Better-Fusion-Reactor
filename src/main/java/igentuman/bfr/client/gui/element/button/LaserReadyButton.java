package igentuman.bfr.client.gui.element.button;

import igentuman.bfr.common.BfrLang;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class LaserReadyButton extends MekanismButton {
    private final int index;


    public LaserReadyButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 128, 22, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), (element, mouseX, mouseY) -> {return false;}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
       // displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_LASER_MIN_ENERGY.translate(EnergyDisplay.of(FloatingLong.create(500000000))),BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR.translate());
    }

}