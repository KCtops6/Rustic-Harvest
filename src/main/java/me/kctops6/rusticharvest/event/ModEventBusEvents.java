package me.kctops6.rusticharvest.event;

import me.kctops6.rusticharvest.Config;
import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

@Mod.EventBusSubscriber(modid = RusticHarvest.MODID)
public class ModEventBusEvents {

    /**
     * ???
     * @param event ???
     */
    @SubscribeEvent
    public static void onBlockDrops(BlockDropsEvent event) {
        boolean isOakLeaves = event.getState().is(Blocks.OAK_LEAVES) || event.getState().is(Blocks.DARK_OAK_LEAVES);
        if (isOakLeaves) {
            if (event.getLevel().getRandom().nextFloat() < Config.greenAppleChance) {
                event.getDrops().add(new ItemEntity(
                        event.getLevel(),
                        event.getPos().getX() + 0.5,
                        event.getPos().getY() + 0.5,
                        event.getPos().getZ() + 0.5,
                        new ItemStack(ModItems.GREEN_APPLE.get())
                ));
            }
        }
    }
}