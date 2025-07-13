package igentuman.bfr.client.gui.element.button;

import igentuman.bfr.common.BfrLang;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class HelpButton extends MekanismButton {
    private final int index;


    public HelpButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 14, 14, TextComponentUtil.getString("?"), (element, mouseX, mouseY) -> {
            return false;
        }, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)getX() && pMouseY >= (double)getY() && pMouseX < (double)(getX() + this.width) && pMouseY < (double)(getY() + this.height);
    }

    @Override
    public void renderToolTip(@Nonnull GuiGraphics matrix, int mouseX, int mouseY) {
        super.renderToolTip(matrix, mouseX, mouseY);
       // displayTooltips(matrix, mouseX, mouseY, BfrLang.REACTOR_HELP1.translate(),BfrLang.REACTOR_HELP2.translate(), BfrLang.REACTOR_HELP3.translate());
    }
}