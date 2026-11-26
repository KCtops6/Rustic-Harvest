package me.kctops6.rusticharvest.block;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.custom.BaseCropBlock;
import me.kctops6.rusticharvest.block.custom.BlueberryBushBlock;
import me.kctops6.rusticharvest.block.custom.ChickenOfTheWoodsBlock;
import me.kctops6.rusticharvest.block.custom.TallCropBlock;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RusticHarvest.MODID);

    public static final DeferredBlock<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush",
            () -> new BlueberryBushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .randomTicks()
                    .noCollission()
                    .sound(SoundType.SWEET_BERRY_BUSH)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> BARLEY_CROP = BLOCKS.register("barley_crop",
            () -> new BaseCropBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().sound(SoundType.CROP),
                    ModItems.BARLEY_SEEDS::get));
    public static final DeferredBlock<Block> GARLIC_CROP = BLOCKS.register("garlic_crop",
            () -> new BaseCropBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().sound(SoundType.CROP),
                    ModItems.GARLIC_SEEDS::get));
    public static final DeferredBlock<Block> RYE_CROP = BLOCKS.register("rye_crop",
            () -> new BaseCropBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().sound(SoundType.CROP),
                    ModItems.RYE_SEEDS::get));

    public static final DeferredBlock<Block> CORN_CROP = BLOCKS.register("corn_crop",
            () -> new TallCropBlock(BlockBehaviour.Properties.of().noCollission().randomTicks().sound(SoundType.CROP),
                    ModItems.CORN_SEEDS::get, 4));

    public static final DeferredBlock<Block> CHICKEN_OF_THE_WOODS = BLOCKS.register("chicken_of_the_woods",
            () -> new ChickenOfTheWoodsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .noCollission()
                    .randomTicks()
                    .sound(SoundType.FUNGUS)
                    .strength(0.2F)
                    .pushReaction(PushReaction.DESTROY)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}