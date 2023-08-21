package igentuman.bfr.common.events;

import mekanism.api.Coord4D;
import mekanism.common.lib.radiation.RadiationManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

import static igentuman.bfr.common.registries.BfrItems.SOLIDIFIED_WASTE;
import static mekanism.common.registries.MekanismItems.*;

public class RadiationEvents {

    protected Map<Item, Double> itemRadiation;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntityJoinLevelEvent event) {
        if(itemRadiation == null) {
            itemRadiation = Map.of(
                    SOLIDIFIED_WASTE.asItem(), 0.5D,
                    POLONIUM_PELLET.asItem(), 0.5D,
                    PLUTONIUM_PELLET.asItem(), 0.5D,
                    REPROCESSED_FISSILE_FRAGMENT.asItem(), 0.5D
            );
        }
        Entity entity = event.getEntity();
        if (entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) entity).getItem();
            if(stack.isEmpty()) {
                return;
            }
            double radiation = itemRadiation.getOrDefault(stack.getItem(), 0D)*stack.getCount();
            if(radiation == 0) return;
            RadiationManager.INSTANCE.radiate(
                    new Coord4D(
                            entity.blockPosition().getX(),
                            entity.blockPosition().getY(),
                            entity.blockPosition().getZ(),
                            entity.level().dimension()),
                    radiation);
        }
    }

}
