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

    private static final ModConfigSpec.BooleanValue BLUEBERRY_BUSH_SLOW = BUILDER
            .comment("Whether blueberry bushes slow down entities walking through them")
            .define("blueberryBushSlow", false);

    private static final ModConfigSpec.BooleanValue BLUEBERRY_BUSH_DAMAGE = BUILDER
            .comment("Whether blueberry bushes deal thorn damage to entities moving through them")
            .define("blueberryBushDamage", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double greenAppleChance;
    public static boolean blueberryBushSlow;
    public static boolean blueberryBushDamage;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        greenAppleChance = GREEN_APPLE_CHANCE.get();
        blueberryBushSlow = BLUEBERRY_BUSH_SLOW.get();
        blueberryBushDamage = BLUEBERRY_BUSH_DAMAGE.get();
    }
}