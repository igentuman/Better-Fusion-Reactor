package igentuman.bfr.common;

import javax.annotation.Nullable;
import mekanism.common.registration.impl.FluidRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tag.BaseTagProvider;
import mekanism.common.tag.MekanismTagProvider;
import mekanism.common.tags.MekanismTags;
import igentuman.bfr.common.registries.BfrBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrTagProvider extends BaseTagProvider {

    public BfrTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, BetterFusionReactor.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addEndermanBlacklist();
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
}