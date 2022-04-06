package igentuman.bfr.client.gui.element;

import mekanism.api.text.ILangEntry;
import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.tab.GuiTabElementType;
import mekanism.client.gui.element.tab.TabType;
import mekanism.client.render.lib.ColorAtlas.ColorRegistryObject;
import mekanism.common.MekanismLang;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import igentuman.bfr.client.gui.element.GuiTurbineTab.TurbineTab;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress.ClickedGeneratorsTileButton;
import igentuman.bfr.common.tile.turbine.TileEntityTurbineCasing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiTurbineTab extends GuiTabElementType<TileEntityTurbineCasing, TurbineTab> {

    public GuiTurbineTab(IGuiWrapper gui, TileEntityTurbineCasing tile, TurbineTab type) {
        super(gui, tile, type);
    }

    public enum TurbineTab implements TabType<TileEntityTurbineCasing> {
        MAIN("gases.png", MekanismLang.MAIN_TAB, ClickedGeneratorsTileButton.TAB_MAIN, SpecialColors.TAB_MULTIBLOCK_MAIN),
        STAT("stats.png", BfrLang.TURBINE_STATS, ClickedGeneratorsTileButton.TAB_STATS, SpecialColors.TAB_MULTIBLOCK_STATS);

        private final ClickedGeneratorsTileButton button;
        private final ColorRegistryObject colorRO;
        private final ILangEntry description;
        private final String path;

        TurbineTab(String path, ILangEntry description, ClickedGeneratorsTileButton button, ColorRegistryObject colorRO) {
            this.path = path;
            this.description = description;
            this.button = button;
            this.colorRO = colorRO;
        }

        @Override
        public ResourceLocation getResource() {
            return MekanismUtils.getResource(ResourceType.GUI, path);
        }

        @Override
        public void onClick(TileEntityTurbineCasing tile) {
            BetterFusionReactor.packetHandler.sendToServer(new PacketBfrGuiButtonPress(button, tile.getBlockPos()));
        }

        @Override
        public ITextComponent getDescription() {
            return description.translate();
        }

        @Override
        public ColorRegistryObject getTabColor() {
            return colorRO;
        }
    }
}