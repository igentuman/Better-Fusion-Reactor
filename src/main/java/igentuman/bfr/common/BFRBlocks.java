package igentuman.bfr.common;

import igentuman.bfr.common.block.BlockReactor;
import igentuman.bfr.common.block.states.BlockStateReactor;
import igentuman.bfr.common.item.ItemBlockReactor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BFR.MODID)
public class BFRBlocks {

    public static final Block Reactor = BlockReactor.getReactorBlock(BlockStateReactor.ReactorBlock.REACTOR_BLOCK);
    public static final Block ReactorGlass = BlockReactor.getReactorBlock(BlockStateReactor.ReactorBlock.REACTOR_GLASS);

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(init(Reactor, "Reactor"));
        registry.register(init(ReactorGlass, "ReactorGlass"));
    }
    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(BFRItems.init(new ItemBlockReactor(Reactor), "Reactor"));
        registry.register(BFRItems.init(new ItemBlockReactor(ReactorGlass), "ReactorGlass"));
    }
    public static Block init(Block block, String name) {
        return block.setTranslationKey(name).setRegistryName(new ResourceLocation(BFR.MODID, name));
    }
}