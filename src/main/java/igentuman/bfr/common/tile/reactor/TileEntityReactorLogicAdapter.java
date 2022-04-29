package igentuman.bfr.common.tile.reactor;

import io.netty.buffer.ByteBuf;
import javax.annotation.Nonnull;
import mekanism.api.TileNetworkList;
import mekanism.common.integration.computer.IComputerIntegration;
import mekanism.common.util.LangUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityReactorLogicAdapter extends TileEntityReactorBlock implements IComputerIntegration {

    private static final String[] methods = new String[]{
            "isIgnited", "canIgnite", "getPlasmaHeat", "getMaxPlasmaHeat", "getCaseHeat", "getMaxCaseHeat",
            "getInjectionRate", "setInjectionRate", "hasFuel", "getProducing", "getIgnitionTemp", "getEnergy",
            "getMaxEnergy", "getWater", "getSteam", "getFuel", "getDeuterium", "getTritium", "getEfficiency",
            "getErrorLevel","adjustReactivity"};
    public ReactorLogic logicType = ReactorLogic.READY;
    public boolean activeCooled;
    public int prevRedstoneLevel;

    public TileEntityReactorLogicAdapter() {
        super();
        fullName = "ReactorLogicAdapter";
    }
    int prevInputRedstone = 0;

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote) {
            int outputting = getRedstoneLevel();
            if (outputting != prevRedstoneLevel) {
                world.notifyNeighborsOfStateChange(getPos(), getBlockType(), true);
            }
            prevRedstoneLevel = outputting;
            int redstone = world.getRedstonePowerFromNeighbors(getPos());
            if(prevInputRedstone > 0) {
                prevInputRedstone = redstone;
                return;
            }
            prevInputRedstone = redstone;
            if (getReactor() != null && redstone > 0) {
                switch (logicType) {
                    case INJECTION_UP:
                        getReactor().setInjectionRate(getReactor().getInjectionRate() + 2);
                        break;
                    case INJECTION_DOWN:
                        getReactor().setInjectionRate(getReactor().getInjectionRate() - 2);
                        break;
                    case REACTIVITY_UP:
                        getReactor().adjustReactivity(redstone);
                        break;
                    case REACTIVITY_DOWN:
                        getReactor().adjustReactivity(-redstone);
                        break;
                }
            }
        }
    }

    @Override
    public boolean isFrame() {
        return false;
    }

    public int getRedstoneLevel()
    {
        if (world.isRemote) {
            return prevRedstoneLevel;
        }
        if (getReactor() == null || !getReactor().isFormed()) {
            return 0;
        }
        switch (logicType) {
            case READY:
                return getReactor().getPlasmaTemp() >= getReactor().getIgnitionTemperature(activeCooled)  ? 15 : 0;
            case CAPACITY:
                return getReactor().getPlasmaTemp() >= getReactor().getMaxPlasmaTemperature(activeCooled) ? 15 : 0;
            case ERROR_LEVEL:
                return (int)(getReactor().getErrorLevel() / (100 / 15));
            case EFFICIENCY:
                return (int)(getReactor().getEfficiency() / (100 / 15));
            case DEPLETED:
                return (getReactor().getDeuteriumTank().getStored() < getReactor().getInjectionRate() / 2) ||
                        (getReactor().getTritiumTank().getStored() < getReactor().getInjectionRate() / 2) ? 15 : 0;
            default:
                return 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTags) {
        super.readFromNBT(nbtTags);
        logicType = ReactorLogic.values()[nbtTags.getInteger("logicType")];
        activeCooled = nbtTags.getBoolean("activeCooled");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTags) {
        super.writeToNBT(nbtTags);
        nbtTags.setInteger("logicType", logicType.ordinal());
        nbtTags.setBoolean("activeCooled", activeCooled);
        return nbtTags;
    }

    @Override
    public void handlePacketData(ByteBuf dataStream) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            int type = dataStream.readInt();
            if (type == 0) {
                activeCooled = !activeCooled;
            } else if (type == 1) {
                logicType = ReactorLogic.values()[dataStream.readInt()];
            }
            return;
        }

        super.handlePacketData(dataStream);

        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            logicType = ReactorLogic.values()[dataStream.readInt()];
            activeCooled = dataStream.readBoolean();
            prevRedstoneLevel = dataStream.readInt();
        }
    }

    @Override
    public TileNetworkList getNetworkedData(TileNetworkList data) {
        super.getNetworkedData(data);
        data.add(logicType.ordinal());
        data.add(activeCooled);
        data.add(prevRedstoneLevel);
        return data;
    }

    @Override
    public String[] getMethods() {
        return methods;
    }

    @Override
    public Object[] invoke(int method, Object[] arguments) throws NoSuchMethodException {
        if (getReactor() == null || !getReactor().isFormed()) {
            return new Object[]{"Unformed."};
        }
        switch (method) {
            case 0:
                return new Object[]{getReactor().isBurning()};
            case 1:
                return new Object[]{getReactor().getPlasmaTemp() >= getReactor().getIgnitionTemperature(activeCooled)};
            case 2:
                return new Object[]{getReactor().getPlasmaTemp()};
            case 3:
                return new Object[]{getReactor().getMaxPlasmaTemperature(activeCooled)};
            case 4:
                return new Object[]{getReactor().getCaseTemp()};
            case 5:
                return new Object[]{getReactor().getMaxCasingTemperature(activeCooled)};
            case 6:
                return new Object[]{getReactor().getInjectionRate()};
            case 7:
                if (arguments[0] instanceof Double) {
                    getReactor().setInjectionRate(((Double) arguments[0]).intValue());
                    return new Object[]{"Injection rate set."};
                }
                return new Object[]{"Invalid parameters."};
            case 8:
                return new Object[]{(getReactor().getDeuteriumTank().getStored() >= getReactor().getInjectionRate() / 2) &&
                                    (getReactor().getTritiumTank().getStored() >= getReactor().getInjectionRate() / 2)};
            case 9:
                return new Object[]{getReactor().getPassiveGeneration(false, true)};
            case 10:
                return new Object[]{getReactor().getIgnitionTemperature(activeCooled)};
            case 11:
                return new Object[]{getReactor().getBufferedEnergy()};
            case 12:
                return new Object[]{getReactor().getBufferSize()};
            case 13:
                return new Object[]{getReactor().getWaterTank().getFluidAmount()};
            case 14:
                return new Object[]{getReactor().getSteamTank().getFluidAmount()};
            case 15:
                return new Object[]{getReactor().getFuelTank().getStored()};
            case 16:
                return new Object[]{getReactor().getDeuteriumTank().getStored()};
            case 17:
                return new Object[]{getReactor().getTritiumTank().getStored()};
            case 18:
                return new Object[]{getReactor().getEfficiency()};
            case 19:
                return new Object[]{getReactor().getErrorLevel()};
            case 20:
                return new Object[]{getReactor().adjustReactivity(((Double) arguments[0]).floatValue())};
            default:
                throw new NoSuchMethodException();
        }
    }

    public enum ReactorLogic {
        READY("ready", new ItemStack(Items.REDSTONE),"out"),
        CAPACITY("capacity", new ItemStack(Items.REDSTONE), "out"),
        DEPLETED("depleted", new ItemStack(Items.REDSTONE), "out"),
        ERROR_LEVEL("error_level", new ItemStack(Items.REDSTONE), "out"),
        EFFICIENCY("efficiency", new ItemStack(Items.REDSTONE), "out"),
        REACTIVITY_UP("reactivity_up", new ItemStack(Items.REDSTONE), "in"),
        REACTIVITY_DOWN("reactivity_down", new ItemStack(Items.REDSTONE), "in"),
        INJECTION_UP("injection_up", new ItemStack(Items.REDSTONE), "in"),
        INJECTION_DOWN("injection_down", new ItemStack(Items.REDSTONE), "in");


        private String name;
        private ItemStack renderStack;
        private String direction;

        ReactorLogic(String s, ItemStack stack, String dir) {
            name = s;
            renderStack = stack;
            direction = dir;
        }

        public ItemStack getRenderStack() {
            return renderStack;
        }

        public String getDirection() {
            return direction;
        }

        public String getLocalizedName() {
            return LangUtils.localize("reactor." + name);
        }

        public String getDescription() {
            return LangUtils.localize("reactor." + name + ".desc");
        }
    }
}