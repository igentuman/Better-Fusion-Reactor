package igentuman.bfr.common.registries;

import igentuman.bfr.common.block.fusion.BlockLaserFocusMatrix;
import igentuman.bfr.common.tile.TileEntityIrradiator;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorBlock;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorController;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorLogicAdapter;
import igentuman.bfr.common.tile.fusion.TileEntityFusionReactorPort;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.interfaces.IHasDescription;
import mekanism.common.block.prefab.BlockBasicMultiblock;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registries.MekanismDataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static igentuman.bfr.common.BetterFusionReactor.MODID;


public class BfrBlocks {

    public static List<String> ORES = List.of("tin", "osmium", "uranium", "iron", "lead", "gold", "copper");
    public static HashMap<String, BlockRegistryObject<Block, BlockItem>> ORE_BLOCKS = new HashMap<>();
    private BfrBlocks() {
    }

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MODID);
    public static final BlockRegistryObject<BlockTileModel<TileEntityIrradiator, Machine<TileEntityIrradiator>>, ItemBlockTooltip<BlockTileModel<TileEntityIrradiator, Machine<TileEntityIrradiator>>>> IRRADIATOR =
            BLOCKS.register("irradiator", () -> new BlockTileModel<>(BfrBlockTypes.IRRADIATOR, properties -> properties.mapColor(MapColor.METAL)), (block, properties) -> new ItemBlockTooltip<>(block, true, properties
                    .component(MekanismDataComponents.SIDE_CONFIG, AttachedSideConfig.ROTARY)
            )).forItemHolder(holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addOutput()
                            .build()
                    )
            );


    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorController>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorController>>> FUSION_REACTOR_CONTROLLER = registerTooltipBlock("fusion_reactor_controller", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_CONTROLLER, properties -> properties.mapColor(MapColor.COLOR_ORANGE)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorBlock>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorBlock>>> FUSION_REACTOR_FRAME = registerTooltipBlock("fusion_reactor_frame", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_FRAME, properties -> properties.mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorPort>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorPort>>> FUSION_REACTOR_PORT = registerTooltipBlock("fusion_reactor_port", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_PORT, properties -> properties.mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final BlockRegistryObject<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>, ItemBlockTooltip<BlockBasicMultiblock<TileEntityFusionReactorLogicAdapter>>> FUSION_REACTOR_LOGIC_ADAPTER = registerTooltipBlock("fusion_reactor_logic_adapter", () -> new BlockBasicMultiblock<>(BfrBlockTypes.FUSION_REACTOR_LOGIC_ADAPTER, properties -> properties.mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final BlockRegistryObject<BlockLaserFocusMatrix, ItemBlockTooltip<BlockLaserFocusMatrix>> LASER_FOCUS_MATRIX = registerTooltipBlock("laser_focus_matrix", BlockLaserFocusMatrix::new);

    static {
        for (String ore : ORES) {
            ORE_BLOCKS.put(ore, registerOre(ore));
        }
    }
    private static <BLOCK extends Block & IHasDescription> BlockRegistryObject<BLOCK, ItemBlockTooltip<BLOCK>> registerTooltipBlock(String name, Supplier<BLOCK> blockCreator) {
        return BLOCKS.register(name, blockCreator, ItemBlockTooltip::new);
    }
    public static final BlockBehaviour.Properties ORE_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2f).requiresCorrectToolForDrops();
    public static final Item.Properties ORE_ITEM_PROPERTIES = new Item.Properties().rarity(Rarity.UNCOMMON);
    private static BlockRegistryObject<Block, BlockItem> registerOre(String ore) {
        String name = "irradiated_" + ore + "_ore";
        BlockRegistryObject<Block, BlockItem> irradiatedOre = registerBlock(name);
        return irradiatedOre;
    }
    private static BlockRegistryObject<Block, BlockItem> registerBlock(String name) {
        return BLOCKS.register(name, () -> new Block(ORE_BLOCK_PROPERTIES));
    }

    /*public static <B extends Item> ItemRegistryObject<Item> fromBlock(ItemRegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ORE_ITEM_PROPERTIES));
    }*/

}