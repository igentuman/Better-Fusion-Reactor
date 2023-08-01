package igentuman.bfr.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import igentuman.bfr.client.gui.element.GuiIrradiationSlot;
import igentuman.bfr.client.gui.element.GuiRadiationIcon;
import igentuman.bfr.common.BfrLang;
import igentuman.bfr.common.tile.TileEntityIrradiator;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.client.gui.element.GuiUpArrow;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.client.gui.element.tab.window.GuiUpgradeWindowTab;
import mekanism.client.gui.element.window.GuiWindow;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.Color;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.machine.TileEntityCombiner;
import mekanism.common.util.MekanismUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class GuiIrradiator extends GuiConfigurableTile<TileEntityIrradiator, MekanismTileContainer<TileEntityIrradiator>> {

    public GuiIrradiationSlot irradiationSlot;
    public GuiRadiationIcon sourceIcon;
    public GuiIrradiator(MekanismTileContainer<TileEntityIrradiator> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        if(menu.getTileEntity().hasRadiationSource) {
            irradiationSlot.overlayColor(() -> 0x2F50C878);
            irradiationSlot.setTooltip(BfrLang.IRRADIATOR_HAS_SOURCE.translate());
            String fluxFormatted = String.format("%.2f", menu.getTileEntity().getRadiativeFlux());
            irradiationSlot.addTooltip(BfrLang.IRRADIATOR_FLUX.translate(fluxFormatted));
            //sourceIcon.visible = true;
        } else {
            irradiationSlot.overlayColor(() -> 0x0C0C0C0C);
            irradiationSlot.setTooltip(BfrLang.IRRADIATOR_NO_SOURCE.translate());
            irradiationSlot.addTooltip(BfrLang.IRRADIATOR_SOURCE_GUIDE.translate());
            //sourceIcon.visible = false;
        }
    }

    @Override
    protected void addSlots() {
        int size = menu.slots.size();
        for (int i = 0; i < size; i++) {
            Slot slot = menu.slots.get(i);
            if (slot instanceof InventoryContainerSlot containerSlot) {
                ContainerSlotType slotType = containerSlot.getSlotType();
                DataType dataType = findDataType(containerSlot);
                //Shift the slots by one as the elements include the border of the slot
                SlotType type;
                if (dataType != null) {
                    type = SlotType.get(dataType);
                } else if (slotType == ContainerSlotType.INPUT || slotType == ContainerSlotType.OUTPUT || slotType == ContainerSlotType.EXTRA) {
                    type = SlotType.NORMAL;
                } else if (slotType == ContainerSlotType.POWER) {
                    type = SlotType.POWER;
                    continue;
                } else if (slotType == ContainerSlotType.NORMAL || slotType == ContainerSlotType.VALIDITY) {
                    type = SlotType.NORMAL;
                } else {//slotType == ContainerSlotType.IGNORED: don't do anything
                    continue;
                }
                if(slotType == ContainerSlotType.POWER) continue;
                if(slotType == ContainerSlotType.OUTPUT) {
                    type = SlotType.OUTPUT_2;
                }
                GuiSlot guiSlot = new GuiSlot(type, this, slot.x - 1, slot.y - 1);

                containerSlot.addWarnings(guiSlot);
                SlotOverlay slotOverlay = containerSlot.getSlotOverlay();
                if (slotOverlay != null) {
                    guiSlot.with(slotOverlay);
                }
                if (slotType == ContainerSlotType.VALIDITY) {
                    int index = i;
                    guiSlot.validity(() -> checkValidity(index));
                }
                addRenderableWidget(guiSlot);
            } else {
                addRenderableWidget(new GuiSlot(SlotType.NORMAL, this, slot.x - 1, slot.y - 1));
            }
        }
        irradiationSlot = addRenderableWidget(new GuiIrradiationSlot(SlotType.ORE, this, 63, 52));
    }

    @Override
    protected void addGenericTabs() {
        if (tile.supportsRedstone()) {
            addRenderableWidget(new GuiRedstoneControlTab(this, tile));
        }
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiUpArrow(this, 68, 38));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.BAR, this, 86, 38).jeiCategory(tile))
              .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT, tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        sourceIcon = addRenderableWidget(new GuiRadiationIcon( this, 63, 52));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics matrix, int mouseX, int mouseY) {
        renderTitleText(matrix);
        drawString(matrix, playerInventoryTitle, inventoryLabelX, inventoryLabelY, titleTextColor());
        super.drawForegroundText(matrix, mouseX, mouseY);
    }
}