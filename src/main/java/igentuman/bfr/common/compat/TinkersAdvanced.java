package igentuman.bfr.common.compat;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import com.c2h6s.tinkers_advanced.util.BlockUtil;
import igentuman.bfr.common.content.fusion.BFReactorMultiblockData;
import igentuman.bfr.common.registries.BfrBlocks;
import mekanism.api.Coord4D;
import mekanism.common.lib.radiation.RadiationManager;
import mekanism.common.registries.MekanismDamageTypes;
import mekanism.generators.common.registries.GeneratorsBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

public class TinkersAdvanced {
    public static void blowIron(BFReactorMultiblockData data, Level world) {
        if(!TiAcConfig.COMMON.EXPLODING_FUSION_REACTOR.get()) {
            return;
        }
        int craftCount = 0;

        for(ItemEntity itemEntity : world.getEntitiesOfClass(ItemEntity.class, data.deathZone)) {
            float countStack = 0.0F;
            if (itemEntity.getItem().is(Tags.Items.INGOTS_IRON)) {
                countStack = 1.0F;
            }

            if (itemEntity.getItem().is(Tags.Items.STORAGE_BLOCKS_IRON)) {
                countStack = 9.0F;
            }

            if (itemEntity.getItem().is(Tags.Items.NUGGETS_IRON)) {
                countStack = 0.1F;
            }

            if (itemEntity.getItem().is(net.minecraft.world.item.Items.ANVIL)) {
                countStack = 31.0F;
            }

            Item leftOver = itemEntity.getItem().getItem();
            if (leftOver instanceof ToolPartItem) {
                ToolPartItem partItem = (ToolPartItem)leftOver;
                if (partItem.getMaterial(itemEntity.getItem()).getId().getPath().equals(MaterialIds.iron.getPath())) {
                    countStack = (float) com.c2h6s.tinkers_advanced.TinkersAdvanced.RANDOM.nextInt(4);
                }
            }

            if (countStack > 0.0F) {
                itemEntity.discard();
            }

            if (itemEntity.getItem().getItem() instanceof IModifiable) {
                ToolStack tool = ToolStack.from(itemEntity.getItem());
                tool.setDamage(Integer.MAX_VALUE);

                for(MaterialVariant variant : tool.getMaterials()) {
                    if (variant.getId().getPath().equals(MaterialIds.iron.getPath())) {
                        countStack = 16.0F;
                        break;
                    }
                }
            }

            countStack *= (float)itemEntity.getItem().getCount();
            craftCount += (int)countStack;
        }

        if (craftCount > 0) {
            Vec3 posCenter = data.deathZone.getCenter();
            BlockUtil.getPosInRange(data.deathZone.inflate((double)1.0F)).forEach((blockPos) -> {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.is(GeneratorsBlocks.REACTOR_GLASS.getBlock()) || blockState.is(BfrBlocks.FUSION_REACTOR_PORT.getBlock()) || blockState.is(BfrBlocks.FUSION_REACTOR_FRAME.getBlock()) || blockState.is(BfrBlocks.FUSION_REACTOR_LOGIC_ADAPTER.getBlock()) || blockState.is(BfrBlocks.FUSION_REACTOR_CONTROLLER.getBlock()) || blockState.is(BfrBlocks.LASER_FOCUS_MATRIX.getBlock())) {
                    world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                }

            });
            RadiationManager radiationManager = RadiationManager.get();
            radiationManager.radiate(new Coord4D(posCenter.x, posCenter.y, posCenter.z, world.dimension()), (double)1000.0F);
            Explosion explosion = world.explode((Entity)null, MekanismDamageTypes.RADIATION.source(world), (ExplosionDamageCalculator)null, posCenter, 24.0F, true, Level.ExplosionInteraction.TNT);
            explosion.getHitPlayers().forEach((player, vec3) -> {
                player.setDeltaMovement(player.getDeltaMovement().add(vec3.scale((double)10.0F)));
                player.invulnerableTime = 0;
                player.hurt(MekanismDamageTypes.RADIATION.source(world), 1024.0F);
                radiationManager.radiate(player, (double)1000.0F);
            });
            int entityCount = craftCount / 64;
            int leftOver = craftCount % 64;

            for(int i = 0; i < entityCount; ++i) {
                ItemEntity entity = new ItemEntity(world, posCenter.x, posCenter.y, posCenter.z, new ItemStack((ItemLike) TiAcItems.NEUTRONITE_INGOT.get(), 64));
                world.addFreshEntity(entity);
            }

            if (leftOver > 0) {
                ItemEntity entity = new ItemEntity(world, posCenter.x, posCenter.y, posCenter.z, new ItemStack((ItemLike)TiAcItems.NEUTRONITE_INGOT.get(), leftOver));
                world.addFreshEntity(entity);
            }
        }
    }
}
