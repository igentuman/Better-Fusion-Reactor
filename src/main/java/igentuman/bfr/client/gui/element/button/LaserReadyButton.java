package igentuman.bfr.client.gui.element.button;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.common.BfrLang;
import mekanism.api.math.FloatingLong;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class LaserReadyButton extends MekanismButton {
    private final int index;


    public LaserReadyButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 128, 22, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), () -> {}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.getButtonX() && pMouseY >= (double)this.getButtonY() && pMouseX < (double)(this.getButtonX() + this.width) && pMouseY < (double)(this.getButtonY() + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
        displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_LASER_MIN_ENERGY.translate(EnergyDisplay.of(FloatingLong.create(500000000))),BfrLang.REACTOR_LASER_MIN_ENERGY_DESCR.translate());
    }



    @Override
    public void renderForeground(GuiGraphics matrix, int mouseX, int mouseY) {
        drawString(matrix, BfrLang.REACTOR_LASER_READY_BUTTON.translate(), getButtonX(), getButtonY(), SpecialColors.TAB_ENERGY_CONFIG.argb());
        super.renderForeground(matrix, mouseX, mouseY);
    }
}