package igentuman.bfr.common.compat.oc2;

import li.cil.oc2.api.bus.device.Device;
import li.cil.oc2.api.bus.device.provider.BlockDeviceProvider;
import li.cil.oc2.api.bus.device.provider.BlockDeviceQuery;
import li.cil.oc2.api.util.Invalidatable;
import mekanism.generators.common.tile.fission.TileEntityFissionReactorLogicAdapter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import static igentuman.bfr.common.BetterFusionReactor.MODID;
import static li.cil.oc2.api.util.Registries.BLOCK_DEVICE_PROVIDER;

public class OC2BlockDeviceProvider {
    private static final DeferredRegister<BlockDeviceProvider> BLOCK_DEVICE_PROVIDERS = DeferredRegister.create(BLOCK_DEVICE_PROVIDER, MODID);

    public static void init() {
        BLOCK_DEVICE_PROVIDERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_DEVICE_PROVIDERS.register("fission_reactor_port", Provider::new);
    }

    public static class Provider implements BlockDeviceProvider {

        @Override
        public Invalidatable<Device> getDevice(BlockDeviceQuery query) {
            BlockEntity blockEntity = query.getLevel().getBlockEntity(query.getQueryPosition());
            if (blockEntity instanceof TileEntityFissionReactorLogicAdapter reactorPort) {
                return Invalidatable.of(FissionLogicPortOC2Device.createDevice(reactorPort));
            } else {
                return Invalidatable.empty();
            }
        }
    }
}
