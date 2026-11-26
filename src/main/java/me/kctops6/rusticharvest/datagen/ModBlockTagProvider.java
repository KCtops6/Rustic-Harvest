package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RusticHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.CROPS).add(
                ModBlocks.BARLEY_CROP.get(),
                ModBlocks.GARLIC_CROP.get(),
                ModBlocks.RYE_CROP.get(),
                ModBlocks.CORN_CROP.get()
        );

        tag(BlockTags.MAINTAINS_FARMLAND).add(
                ModBlocks.BARLEY_CROP.get(),
                ModBlocks.GARLIC_CROP.get(),
                ModBlocks.RYE_CROP.get(),
                ModBlocks.CORN_CROP.get()
        );
    }
}