package me.kctops6.rusticharvest;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import me.kctops6.rusticharvest.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RusticHarvest.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FRUITS = CREATIVE_MODE_TABS.register("fruits",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GREEN_APPLE.get()))
                    .title(Component.translatable("creativetab.rusticharvest.fruits"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GREEN_APPLE.get());
                        output.accept(ModItems.BLUEBERRIES.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VEGETABLES = CREATIVE_MODE_TABS.register("vegetables",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GARLIC.get()))
                    .title(Component.translatable("creativetab.rusticharvest.vegetables"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GARLIC.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GRAINS = CREATIVE_MODE_TABS.register("grains",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BARLEY.get()))
                    .title(Component.translatable("creativetab.rusticharvest.grains"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BARLEY.get());
                        output.accept(ModItems.RYE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}