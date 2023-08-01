package igentuman.bfr.client.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.api.energy.IEnergyContainer;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import mekanism.common.util.text.EnergyDisplay;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuiCustomVerticalBar extends GuiBar<IBarInfoHandler> {

    private static final ResourceLocation ENERGY_BAR = MekanismUtils.getResource(ResourceType.GUI_BAR, "vertical_power.png");
    private static final int texWidth = 4;
    private static final int texHeight = 52;

    private final double heightScale;

    public GuiCustomVerticalBar(IGuiWrapper gui, IEnergyContainer container, int x, int y, ResourceLocation texture) {
        this(gui, container, x, y, texHeight, texture);
    }

    public GuiCustomVerticalBar(IGuiWrapper gui, IEnergyContainer container, int x, int y) {
        this(gui, container, x, y, texHeight, ENERGY_BAR);
    }

    public GuiCustomVerticalBar(IGuiWrapper gui, IEnergyContainer container, int x, int y, int desiredHeight, ResourceLocation texture) {
        this(gui, new IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return EnergyDisplay.of(container).getTextComponent();
            }

            @Override
            public double getLevel() {
                return container.getEnergy().divideToLevel(container.getMaxEnergy());
            }
        }, x, y, desiredHeight, texture);
    }

    public GuiCustomVerticalBar(IGuiWrapper gui, IBarInfoHandler handler, int x, int y, ResourceLocation texture) {
        this(gui, handler, x, y, texHeight, texture);
    }

    public GuiCustomVerticalBar(IGuiWrapper gui, IBarInfoHandler handler, int x, int y, int desiredHeight, ResourceLocation texture) {
        super(texture, gui, handler, x, y, texWidth, desiredHeight, false);
        heightScale = desiredHeight / (double) texHeight;
    }

    @Override
    protected void renderBarOverlay(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks, double handlerLevel) {
        int displayInt = (int) (handlerLevel * texHeight);
        if (displayInt > 0) {
            int scaled = calculateScaled(heightScale, displayInt);
            matrix.blit(ENERGY_BAR, getX() + 1, getY() + height - 1 - scaled, texWidth, scaled, 0, 0, texWidth, displayInt, texWidth, texHeight);
        }
    }
}