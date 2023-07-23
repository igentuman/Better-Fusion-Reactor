package igentuman.bfr.datagen;

import javax.annotation.Nullable;

import igentuman.bfr.common.BetterFusionReactor;
import igentuman.bfr.datagen.tag.BaseTagProvider;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.generators.common.registries.GeneratorsBlocks;
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
              BfrBlocks.REACTOR_GLASS,
              BfrBlocks.IRRADIATOR
        );
    }

    private void addHarvestRequirements() {
        addToHarvestTag(BlockTags.MINEABLE_WITH_PICKAXE,
                BfrBlocks.REACTOR_GLASS,
                BfrBlocks.LASER_FOCUS_MATRIX,
                BfrBlocks.FUSION_REACTOR_CONTROLLER,
                BfrBlocks.FUSION_REACTOR_FRAME,
                BfrBlocks.FUSION_REACTOR_PORT,
                BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER,
                BfrBlocks.IRRADIATOR
        );
    }
}