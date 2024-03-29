package igentuman.bfr.client.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.client.gui.GuiUtils;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GuiRadiationIcon extends GuiTexturedElement {

    private static final ResourceLocation ICON = MekanismUtils.getResource(ResourceType.GUI, "radioactive.png");

    public GuiRadiationIcon(IGuiWrapper gui, int x, int y) {
        super(ICON, gui, x, y, 18, 18);
    }

    @Override
    public boolean isMouseOver(double xAxis, double yAxis) {
        return this.active && this.visible && xAxis >= getButtonX() + 16 && xAxis < getButtonX() + width - 1 && yAxis >= getButtonY() + 1 && yAxis < getButtonY() + height - 1;
    }

    @Override
    public void drawBackground(@NotNull GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(matrix, mouseX, mouseY, partialTicks);
        RenderSystem.setShaderTexture(0, getResource());
        matrix.blit(ICON, getButtonX(), getButtonY(), 0, 0, width, height, width, height);
    }
}