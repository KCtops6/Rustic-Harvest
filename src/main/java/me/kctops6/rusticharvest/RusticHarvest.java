package me.kctops6.rusticharvest;

import com.mojang.logging.LogUtils;
import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(RusticHarvest.MODID)
public class RusticHarvest {
    public static final String MODID = "rustic_harvest";
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * ???
     * @param modEventBus ???
     * @param modContainer ???
     */
    public RusticHarvest(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
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
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) { }
}