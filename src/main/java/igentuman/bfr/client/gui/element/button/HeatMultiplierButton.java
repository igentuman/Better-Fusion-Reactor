package igentuman.bfr.client.gui.element.button;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.common.BfrLang;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class HeatMultiplierButton extends MekanismButton {
    private final int index;


    public HeatMultiplierButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 14, 14, TextComponentUtil.getString("?"),  () -> {}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
        displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_HELP_HEAT_MULTIPLIER1.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER2.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER3.translate());
    }

}