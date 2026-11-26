package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.BLUEBERRIES.getId(), new Compostable(0.3F), false)
                .add(ModItems.GREEN_APPLE.getId(), new Compostable(0.65F), false)
                .add(ModItems.CHICKEN_OF_THE_WOODS.getId(), new Compostable(0.65F), false)
                .add(ModItems.BARLEY.getId(), new Compostable(0.65F), false)
                .add(ModItems.RYE.getId(), new Compostable(0.65F), false)
                .add(ModItems.GARLIC.getId(), new Compostable(0.65F), false)
                .add(ModItems.CORN.getId(), new Compostable(0.65F), false)
                .add(ModItems.BARLEY_SEEDS.getId(), new Compostable(0.3F), false)
                .add(ModItems.RYE_SEEDS.getId(), new Compostable(0.3F), false)
                .add(ModItems.GARLIC_SEEDS.getId(), new Compostable(0.3F), false)
                .add(ModItems.CORN_SEEDS.getId(), new Compostable(0.3F), false);
    }
}