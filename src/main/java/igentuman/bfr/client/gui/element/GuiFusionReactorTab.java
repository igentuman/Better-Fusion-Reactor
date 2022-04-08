package igentuman.bfr.client.gui.element;

import mekanism.api.text.ILangEntry;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.tab.GuiTabElementType;
import mekanism.client.gui.element.tab.TabType;
import mekanism.client.render.lib.ColorAtlas.ColorRegistryObject;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import igentuman.bfr.client.BfrSpecialColors;
import igentuman.bfr.client.gui.element.GuiFusionReactorTab.FusionReactorTab;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress.ClickedGeneratorsTileButton;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiFusionReactorTab extends GuiTabElementType<TileEntityFusionReactorController, FusionReactorTab> {

    public GuiFusionReactorTab(IGuiWrapper gui, TileEntityFusionReactorController tile, FusionReactorTab type) {
        super(gui, tile, type);
    }

    public enum FusionReactorTab implements TabType<TileEntityFusionReactorController> {
        HEAT(MekanismUtils.getResource(ResourceType.GUI, "heat.png"), BfrLang.HEAT_TAB, 6, ClickedGeneratorsTileButton.TAB_HEAT, BfrSpecialColors.TAB_MULTIBLOCK_HEAT),
        FUEL(BetterFusionReactor.rl(ResourceType.GUI.getPrefix() + "fuel.png"), BfrLang.FUEL_TAB, 34, ClickedGeneratorsTileButton.TAB_FUEL, BfrSpecialColors.TAB_MULTIBLOCK_FUEL),
        STAT(MekanismUtils.getResource(ResourceType.GUI, "stats.png"), BfrLang.STATS_TAB, 62, ClickedGeneratorsTileButton.TAB_STATS, SpecialColors.TAB_MULTIBLOCK_STATS),
        EFFICIENCY(MekanismUtils.getResource(ResourceType.GUI, "visuals.png"), BfrLang.EFFICIENCY_TAB, 90, ClickedGeneratorsTileButton.TAB_EFFICIENCY, SpecialColors.TAB_MULTIBLOCK_STATS);

        private final ClickedGeneratorsTileButton button;
        private final ColorRegistryObject colorRO;
        private final ILangEntry description;
        private final ResourceLocation path;
        private final int yPos;

        FusionReactorTab(ResourceLocation path, ILangEntry description, int y, ClickedGeneratorsTileButton button, ColorRegistryObject colorRO) {
            this.path = path;
            this.description = description;
            this.yPos = y;
            this.button = button;
            this.colorRO = colorRO;
        }

        @Override
        public ResourceLocation getResource() {
            return path;
        }

        @Override
        public void onClick(TileEntityFusionReactorController tile) {
            BetterFusionReactor.packetHandler.sendToServer(new PacketBfrGuiButtonPress(button, tile.getBlockPos()));
        }

        @Override
        public ITextComponent getDescription() {
            return description.translate();
        }

        @Override
        public int getYPos() {
            return yPos;
        }

        @Override
        public ColorRegistryObject getTabColor() {
            return colorRO;
        }
    }
}