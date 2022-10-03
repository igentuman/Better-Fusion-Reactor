package igentuman.bfr.client.gui.element.button;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.common.BfrLang;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.common.util.text.EnergyDisplay;

import javax.annotation.Nonnull;

public class LaserReadyButton extends MekanismButton {
    private final int index;


    public LaserReadyButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 128, 22, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), null, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.x && pMouseY >= (double)this.y && pMouseX < (double)(this.x + this.width) && pMouseY < (double)(this.y + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
        displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_LASER_MIN_ENERGY.translate(EnergyDisplay.of(FloatingLong.create(500000000))),BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR.translate());
    }



    @Override
    public void renderForeground(PoseStack matrix, int mouseX, int mouseY) {
        drawString(matrix, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), x, y, SpecialColors.TAB_ENERGY_CONFIG.argb());
        super.renderForeground(matrix, mouseX, mouseY);
    }
}