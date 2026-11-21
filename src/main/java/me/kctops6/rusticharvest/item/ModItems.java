package me.kctops6.rusticharvest.item;

import me.kctops6.rusticharvest.RusticHarvest;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RusticHarvest.MODID);

    public static final DeferredItem<Item> GREEN_APPLE = ITEMS.registerSimpleItem("green_apple", new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).build()));

    /**
     * ???
     * @param eventBus ???
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}