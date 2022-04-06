package igentuman.bfr.common;

import javax.annotation.Nullable;
import igentuman.common.registration.impl.FluidRegistryObject;
import igentuman.common.registration.impl.TileEntityTypeRegistryObject;
import igentuman.common.tag.BaseTagProvider;
import igentuman.common.tag.MekanismTagProvider;
import igentuman.common.tags.MekanismTags;
import igentuman.bfr.common.registries.BfrBlocks;
import igentuman.bfr.common.registries.BfrFluids;
import igentuman.bfr.common.registries.BfrGases;
import igentuman.bfr.common.registries.BfrTileEntityTypes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BfrTagProvider extends BaseTagProvider {

    public BfrTagProvider(DataGenerator gen, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, MekanismGenerators.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addBoxBlacklist();
        addEndermanBlacklist();
        addFluids();
        addGases();
    }

    private void addBoxBlacklist() {
        addToTag(MekanismTags.Blocks.RELOCATION_NOT_SUPPORTED,
              BfrBlocks.ADVANCED_SOLAR_GENERATOR,
              BfrBlocks.WIND_GENERATOR
        );
        TileEntityTypeRegistryObject<?>[] tilesToBlacklist = {
              BfrTileEntityTypes.ADVANCED_SOLAR_GENERATOR,
              BfrTileEntityTypes.WIND_GENERATOR
        };
        addToTag(MekanismTags.TileEntityTypes.IMMOVABLE, tilesToBlacklist);
        addToTag(MekanismTags.TileEntityTypes.RELOCATION_NOT_SUPPORTED, tilesToBlacklist);
    }

    private void addEndermanBlacklist() {
        addToTag(Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST,
              BfrBlocks.TURBINE_CASING,
              BfrBlocks.TURBINE_VALVE,
              BfrBlocks.TURBINE_VENT,
              BfrBlocks.ELECTROMAGNETIC_COIL,
              BfrBlocks.ROTATIONAL_COMPLEX,
              BfrBlocks.SATURATING_CONDENSER,
              BfrBlocks.TURBINE_ROTOR,
              BfrBlocks.FISSION_REACTOR_CASING,
              BfrBlocks.FISSION_REACTOR_PORT,
              BfrBlocks.FISSION_REACTOR_LOGIC_ADAPTER,
              BfrBlocks.FISSION_FUEL_ASSEMBLY,
              BfrBlocks.CONTROL_ROD_ASSEMBLY,
              BfrBlocks.FUSION_REACTOR_CONTROLLER,
              BfrBlocks.FUSION_REACTOR_PORT,
              BfrBlocks.FUSION_REACTOR_FRAME,
              BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER,
              BfrBlocks.LASER_FOCUS_MATRIX,
              BfrBlocks.REACTOR_GLASS
        );
    }

    private void addFluids() {
        addToTag(GeneratorTags.Fluids.BIOETHANOL, BfrFluids.BIOETHANOL);
        addToTag(GeneratorTags.Fluids.DEUTERIUM, BfrFluids.DEUTERIUM);
        addToTag(GeneratorTags.Fluids.FUSION_FUEL, BfrFluids.FUSION_FUEL);
        addToTag(GeneratorTags.Fluids.TRITIUM, BfrFluids.TRITIUM);
        //Prevent all our fluids from being duped by create
        for (FluidRegistryObject<?, ?, ?, ?> fluid : BfrFluids.FLUIDS.getAllFluids()) {
            addToTag(MekanismTagProvider.CREATE_NO_INFINITE_FLUID, fluid);
        }
    }

    private void addGases() {
        addToTag(GeneratorTags.Gases.DEUTERIUM, BfrGases.DEUTERIUM);
        addToTag(GeneratorTags.Gases.TRITIUM, BfrGases.TRITIUM);
        addToTag(GeneratorTags.Gases.FUSION_FUEL, BfrGases.FUSION_FUEL);
    }
}