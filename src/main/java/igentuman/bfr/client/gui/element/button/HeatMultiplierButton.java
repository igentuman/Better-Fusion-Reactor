package igentuman.bfr.client.gui.element.button;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.common.BfrLang;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;

import javax.annotation.Nonnull;

public class HeatMultiplierButton extends MekanismButton {
    private final int index;


    public HeatMultiplierButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 14, 14, TextComponentUtil.getString("?"),  () -> {}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.x && pMouseY >= (double)this.y && pMouseX < (double)(this.x + this.width) && pMouseY < (double)(this.y + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull PoseStack matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
        displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_HELP_HEAT_MULTIPLIER1.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER2.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER3.translate());
    }



    @Override
    public void renderForeground(PoseStack matrix, int mouseX, int mouseY) {
        drawString(matrix, TextComponentUtil.getString("?"), x, y, SpecialColors.TAB_ENERGY_CONFIG.argb());
        super.renderForeground(matrix, mouseX, mouseY);
    }
}