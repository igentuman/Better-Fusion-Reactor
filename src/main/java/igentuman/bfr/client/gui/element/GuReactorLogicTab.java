package igentuman.bfr.client.gui.element;

import igentuman.bfr.client.BfrSpecialColors;
import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress;
import igentuman.bfr.common.network.to_server.PacketBfrGuiButtonPress.ClickedGeneratorsTileButton;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import mekanism.api.text.ILangEntry;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.tab.GuiTabElementType;
import mekanism.client.gui.element.tab.TabType;
import mekanism.client.render.lib.ColorAtlas.ColorRegistryObject;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuReactorLogicTab extends GuiTabElementType<TileEntityFusionReactorLogicAdapter, GuReactorLogicTab.ReactorLogicTab> {

    public GuReactorLogicTab(IGuiWrapper gui, TileEntityFusionReactorLogicAdapter tile, ReactorLogicTab type) {
        super(gui, tile, type);
    }

    public enum ReactorLogicTab implements TabType<TileEntityFusionReactorLogicAdapter> {
        GENERAL(MekanismUtils.getResource(ResourceType.GUI, "wrench.png"), BfrLang.LOGIC_GENERAL_TAB, 6, ClickedGeneratorsTileButton.TAB_LOGIC_GENERAL, BfrSpecialColors.TAB_MULTIBLOCK_HEAT),
        INPUT(BetterFusionReactor.rl(ResourceType.GUI.getPrefix() +"logic_input.png"), BfrLang.LOGIC_IN_TAB, 34, ClickedGeneratorsTileButton.TAB_LOGIC_INPUT, BfrSpecialColors.TAB_MULTIBLOCK_HEAT),
        OUTPUT(BetterFusionReactor.rl(ResourceType.GUI.getPrefix() + "logic_output.png"), BfrLang.LOGIC_OUT_TAB, 62, ClickedGeneratorsTileButton.TAB_LOGIC_OUTPUT, BfrSpecialColors.TAB_MULTIBLOCK_FUEL);

        private final ClickedGeneratorsTileButton button;
        private final ColorRegistryObject colorRO;
        private final ILangEntry description;
        private final ResourceLocation path;
        private final int yPos;

        ReactorLogicTab(ResourceLocation path, ILangEntry description, int y, ClickedGeneratorsTileButton button, ColorRegistryObject colorRO) {
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
        public void onClick(TileEntityFusionReactorLogicAdapter tile) {
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