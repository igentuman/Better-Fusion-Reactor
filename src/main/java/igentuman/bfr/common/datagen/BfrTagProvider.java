package igentuman.bfr.common.datagen;

import javax.annotation.Nullable;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.common.datagen.tag.BaseTagProvider;
import igentuman.bfr.common.registries.BfrBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrTagProvider extends BaseTagProvider {

    public BfrTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, BetterFusionReactor.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addEndermanBlacklist();
        addHarvestRequirements();
    }


    private void addEndermanBlacklist() {
        addToTag(Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST,
              BfrBlocks.FUSION_REACTOR_CONTROLLER,
              BfrBlocks.FUSION_REACTOR_PORT,
              BfrBlocks.FUSION_REACTOR_FRAME,
              BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER,
              BfrBlocks.LASER_FOCUS_MATRIX,
              BfrBlocks.REACTOR_GLASS
        );
    }

    private void addHarvestRequirements() {
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE,
                BfrBlocks.REACTOR_GLASS,
                BfrBlocks.FUSION_REACTOR_CONTROLLER,
                BfrBlocks.FUSION_REACTOR_FRAME,
                BfrBlocks.FUSION_REACTOR_PORT,
                BfrBlocks.LASER_FOCUS_MATRIX,
                BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER
        );
    }
}