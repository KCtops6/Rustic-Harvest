package me.kctops6.rusticharvest;

import com.mojang.logging.LogUtils;
import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(RusticHarvest.MODID)
public class RusticHarvest {
    public static final String MODID = "rustic_harvest";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    /**
     * ???
     * @param modEventBus ???
     * @param modContainer ???
     */
    public RusticHarvest(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * ???
     * @param event ???
     */
    private void commonSetup(final FMLCommonSetupEvent event) { }

    /**
     * ???
     * @param event ???
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.GREEN_APPLE);
            event.accept(ModItems.BLUEBERRIES);
            event.accept(ModItems.BARLEY);
            event.accept(ModItems.CHICKEN_OF_THE_WOODS);
        }
    }

    /**
     * ???
     * @param event ???
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }
}