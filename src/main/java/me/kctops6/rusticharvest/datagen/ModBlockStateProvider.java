package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.block.custom.BaseCropBlock;
import me.kctops6.rusticharvest.block.custom.BlueberryBushBlock;
import me.kctops6.rusticharvest.block.custom.TallCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RusticHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeCrop((BaseCropBlock) ModBlocks.BARLEY_CROP.get(), "barley_crop_stage", "barley_crop_stage");
        makeCrop((BaseCropBlock) ModBlocks.GARLIC_CROP.get(), "garlic_crop_stage", "garlic_crop_stage");
        makeCrop((BaseCropBlock) ModBlocks.RYE_CROP.get(), "rye_crop_stage", "rye_crop_stage");

        makeTallCrop((TallCropBlock) ModBlocks.CORN_CROP.get(), "corn_crop_stage", "corn_crop_stage");
        makeBlueberryBush((BlueberryBushBlock) ModBlocks.BLUEBERRY_BUSH.get(), "blueberry_bush_stage", "blueberry_bush_stage");

        // Chicken of the Woods (Horizontal Directional using your existing block model)
        horizontalBlock(ModBlocks.CHICKEN_OF_THE_WOODS.get(),
                models().getExistingFile(modLoc("block/chicken_of_the_woods")));
    }

    private void makeCrop(BaseCropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> {
            int age = state.getValue(BaseCropBlock.AGE);
            return new ConfiguredModel[]{
                    new ConfiguredModel(models().crop(modelName + age,
                            modLoc("block/" + textureName + age)).renderType("cutout"))
            };
        };
        getVariantBuilder(block).forAllStates(function);
    }

    private void makeTallCrop(TallCropBlock block, String modelName, String textureName) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(TallCropBlock.AGE);
            DoubleBlockHalf half = state.getValue(TallCropBlock.HALF);

            // Upper half only exists at age >= 4 (doubleBlockAge)
            if (half == DoubleBlockHalf.UPPER && age < 4) {
                return new ConfiguredModel[]{
                        new ConfiguredModel(models().crop(modelName + "0", modLoc("block/" + textureName + "0")).renderType("cutout"))
                };
            }

            String suffix = half == DoubleBlockHalf.UPPER ? "_top" : "";
            String fullName = modelName + age + suffix;
            return new ConfiguredModel[]{
                    new ConfiguredModel(models().crop(fullName, modLoc("block/" + fullName)).renderType("cutout"))
            };
        });
    }

    private void makeBlueberryBush(BlueberryBushBlock block, String modelName, String textureName) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(BlueberryBushBlock.AGE);
            return new ConfiguredModel[]{
                    new ConfiguredModel(models().cross(modelName + age,
                            modLoc("block/" + textureName + age)).renderType("cutout"))
            };
        });
    }
}