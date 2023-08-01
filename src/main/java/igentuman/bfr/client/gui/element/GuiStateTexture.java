package igentuman.bfr.client.gui.element;

import java.util.function.BooleanSupplier;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.common.util.MekanismUtils.ResourceType;
import igentuman.bfr.common.BetterFusionReactor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GuiStateTexture extends GuiTexturedElement {

    private static final ResourceLocation stateHolder = BetterFusionReactor.rl(ResourceType.GUI.getPrefix() + "state_holder.png");

    private final BooleanSupplier onSupplier;
    private final ResourceLocation onTexture;
    private final ResourceLocation offTexture;

    public GuiStateTexture(IGuiWrapper gui, int x, int y, BooleanSupplier onSupplier, ResourceLocation onTexture, ResourceLocation offTexture) {
        super(stateHolder, gui, x, y, 16, 16);
        this.onSupplier = onSupplier;
        this.onTexture = onTexture;
        this.offTexture = offTexture;
    }

    @Override
    public void drawBackground(@Nonnull GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderTexture(0, getResource());
        int x = getButtonX();
        int y = getButtonY();
        matrix.blit(stateHolder, x, y, 0, 0, width, height, width, height);
        RenderSystem.setShaderTexture(0, onSupplier.getAsBoolean() ? onTexture : offTexture);
        matrix.blit(stateHolder, x + 2, y + 2, 0, 0, width - 4, height - 4, width - 4, height - 4);
    }
}