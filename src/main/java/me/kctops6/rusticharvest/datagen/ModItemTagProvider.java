package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, RusticHarvest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.FOX_FOOD).add(ModItems.BLUEBERRIES.get());

        tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(
                ModItems.BARLEY_SEEDS.get(),
                ModItems.GARLIC_SEEDS.get(),
                ModItems.RYE_SEEDS.get(),
                ModItems.CORN_SEEDS.get()
        );

        tag(Tags.Items.SEEDS).add(
                ModItems.BARLEY_SEEDS.get(),
                ModItems.GARLIC_SEEDS.get(),
                ModItems.RYE_SEEDS.get(),
                ModItems.CORN_SEEDS.get()
        );

        tag(Tags.Items.CROPS).add(
                ModItems.BARLEY.get(),
                ModItems.GARLIC.get(),
                ModItems.RYE.get(),
                ModItems.CORN.get()
        );
    }
}