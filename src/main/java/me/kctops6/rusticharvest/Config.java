package me.kctops6.rusticharvest;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = RusticHarvest.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue GREEN_APPLE_CHANCE = BUILDER
            .comment("Chance (0.0 to 1.0) for oak and dark oak leaves to drop a green apple (half of vanilla apple's 0.005 chance is 0.0025)")
            .defineInRange("greenAppleChance", 0.0025, 0.0, 1.0);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double greenAppleChance;

    /**
     * ???
     * @param event ???
     */
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        greenAppleChance = GREEN_APPLE_CHANCE.get();
    }
}