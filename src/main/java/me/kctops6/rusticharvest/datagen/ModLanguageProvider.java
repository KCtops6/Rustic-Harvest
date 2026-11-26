package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, RusticHarvest.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModItems.GREEN_APPLE.get(), "Green Apple");
        add(ModItems.BLUEBERRIES.get(), "Blueberries");
        add(ModBlocks.BLUEBERRY_BUSH.get(), "Blueberry Bush");
        add(ModBlocks.CHICKEN_OF_THE_WOODS.get(), "Chicken of the Woods");

        add(ModBlocks.RYE_CROP.get(), "Rye Crop");
        add(ModItems.RYE.get(), "Rye");
        add(ModItems.RYE_SEEDS.get(), "Rye Seeds");

        add(ModBlocks.BARLEY_CROP.get(), "Barley Crop");
        add(ModItems.BARLEY.get(), "Barley");
        add(ModItems.BARLEY_SEEDS.get(), "Barley Seeds");

        add(ModBlocks.CORN_CROP.get(), "Corn Crop");
        add(ModItems.CORN.get(), "Corn");
        add(ModItems.CORN_SEEDS.get(), "Corn Seeds");

        add(ModBlocks.GARLIC_CROP.get(), "Garlic Crop");
        add(ModItems.GARLIC.get(), "Garlic");
        add(ModItems.GARLIC_SEEDS.get(), "Garlic Seeds");

        add("creativetab.rusticharvest.fruits", "Rustic Harvest Fruits");
        add("creativetab.rusticharvest.grains", "Rustic Harvest Grains");
        add("creativetab.rusticharvest.vegetables", "Rustic Harvest Vegetables");
    }
}