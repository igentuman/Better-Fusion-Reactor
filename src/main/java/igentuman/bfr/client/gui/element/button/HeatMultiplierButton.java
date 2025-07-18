package igentuman.bfr.client.gui.element.button;

import igentuman.bfr.common.BfrLang;
import mekanism.api.text.TextComponentUtil;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.gui.tooltip.TooltipUtils;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class HeatMultiplierButton extends MekanismButton {
    private final int index;

    public HeatMultiplierButton(IGuiWrapper gui, int x, int y, int index) {
        super(gui, x, y, 14, 14, TextComponentUtil.getString("?"),  (element, mouseX, mouseY) -> {return false;}, null);
        this.index = index;
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height);
    }

    @Override
    public void updateTooltip(int mouseX, int mouseY) {
        setTooltip(TooltipUtils.create(BfrLang.REACTOR_HELP_HEAT_MULTIPLIER1.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER2.translate(),BfrLang.REACTOR_HELP_HEAT_MULTIPLIER3.translate()));
    }
}