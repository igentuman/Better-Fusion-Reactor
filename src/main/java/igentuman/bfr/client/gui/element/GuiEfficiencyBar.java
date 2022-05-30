package igentuman.bfr.client.gui.element;

import mekanism.api.energy.IStrictEnergyStorage;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiElement;
import mekanism.common.util.MekanismUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEfficiencyBar extends GuiElement {
    private final IStrictEnergyStorage tileEntity;
    private final GuiEfficiencyBar.IPowerInfoHandler handler;
    private final int xLocation;
    private final int yLocation;
    private final int width = 6;
    private final int height = 56;

    public GuiEfficiencyBar(IGuiWrapper gui, IStrictEnergyStorage tile, ResourceLocation def, int x, int y) {

        super(new ResourceLocation("bfr", MekanismUtils.ResourceType.GUI_ELEMENT.getPrefix() + "GuiEfficiencyBar.png"), gui, def);
        this.tileEntity = tile;
        this.handler = new GuiEfficiencyBar.IPowerInfoHandler() {
            public String getTooltip() {
                return MekanismUtils.getEnergyDisplay(GuiEfficiencyBar.this.tileEntity.getEnergy(), GuiEfficiencyBar.this.tileEntity.getMaxEnergy());
            }

            public double getLevel() {
                return GuiEfficiencyBar.this.tileEntity.getEnergy() / GuiEfficiencyBar.this.tileEntity.getMaxEnergy();
            }
        };
        this.xLocation = x;
        this.yLocation = y;
    }

    public GuiEfficiencyBar(IGuiWrapper gui, GuiEfficiencyBar.IPowerInfoHandler h, ResourceLocation def, int x, int y) {
        super(new ResourceLocation("bfr", MekanismUtils.ResourceType.GUI_ELEMENT.getPrefix() + "GuiEfficiencyBar.png"), gui, def);
        this.tileEntity = null;
        this.handler = h;
        this.xLocation = x;
        this.yLocation = y;
    }

    public Rectangle4i getBounds(int guiWidth, int guiHeight) {
        return new Rectangle4i(guiWidth + this.xLocation, guiHeight + this.yLocation, 6, 56);
    }

    protected boolean inBounds(int xAxis, int yAxis) {
        return xAxis >= this.xLocation && xAxis <= this.xLocation + 6 && yAxis >= this.yLocation && yAxis <= this.yLocation + 56;
    }

    public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight) {
        mc.renderEngine.bindTexture(this.RESOURCE);
        this.guiObj.drawTexturedRect(guiWidth + this.xLocation, guiHeight + this.yLocation, 0, 0, 6, 56);
        if (this.handler.getLevel() > 0.0D) {
            int displayInt = (int)(this.handler.getLevel() * 52.0D) + 2;
            this.guiObj.drawTexturedRect(guiWidth + this.xLocation, guiHeight + this.yLocation + 56 - displayInt, 6, 56 - displayInt, 6, displayInt);
        }

        mc.renderEngine.bindTexture(this.defaultLocation);
    }

    public void renderForeground(int xAxis, int yAxis) {
        mc.renderEngine.bindTexture(this.RESOURCE);
        if (this.handler.getTooltip() != null && this.inBounds(xAxis, yAxis)) {
            this.displayTooltip(this.handler.getTooltip(), xAxis, yAxis);
        }

        mc.renderEngine.bindTexture(this.defaultLocation);
    }

    public void preMouseClicked(int xAxis, int yAxis, int button) {
    }

    public void mouseClicked(int xAxis, int yAxis, int button) {
    }

    public abstract static class IPowerInfoHandler {
        public IPowerInfoHandler() {
        }

        public String getTooltip() {
            return null;
        }

        public abstract double getLevel();
    }
}
