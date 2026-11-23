package me.kctops6.rusticharvest.item;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RusticHarvest.MODID);

    public static final DeferredItem<Item> GREEN_APPLE = ITEMS.registerSimpleItem("green_apple", new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f).build()));

    public static final DeferredItem<Item> BLUEBERRIES = ITEMS.register("blueberries",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_BUSH.get(), new Item.Properties().food(Foods.SWEET_BERRIES)));

    public static final DeferredItem<Item> BARLEY = ITEMS.register("barley",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BARLEY_SEEDS = ITEMS.register("barley_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BARLEY_CROP.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}