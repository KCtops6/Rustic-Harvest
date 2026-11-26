package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.RusticHarvest;
import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.block.custom.BaseCropBlock;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_BARLEY_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(RusticHarvest.MODID, "wild_barley"));

    public static final ResourceKey<PlacedFeature> WILD_BARLEY_PLACED_KEY =
            ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(RusticHarvest.MODID, "wild_barley"));

    public static final ResourceKey<BiomeModifier> ADD_WILD_BARLEY_KEY =
            ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(RusticHarvest.MODID, "wild_barley"));

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModDatapackProvider::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, ModDatapackProvider::bootstrapPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModDatapackProvider::bootstrapBiomeModifiers);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(RusticHarvest.MODID));
    }

    private static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        SimpleWeightedRandomList<BlockState> stateList = SimpleWeightedRandomList.<BlockState>builder()
                .add(ModBlocks.BARLEY_CROP.get().defaultBlockState().setValue(BaseCropBlock.AGE, 7), 5)
                .add(ModBlocks.BARLEY_CROP.get().defaultBlockState().setValue(BaseCropBlock.AGE, 6), 3)
                .add(ModBlocks.BARLEY_CROP.get().defaultBlockState().setValue(BaseCropBlock.AGE, 5), 1)
                .build();

        BlockPredicate predicate = BlockPredicate.allOf(
                BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.SHORT_GRASS, Blocks.FERN),
                BlockPredicate.matchesBlocks(new net.minecraft.core.Vec3i(0, -1, 0), Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT)
        );

        context.register(WILD_BARLEY_KEY, new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(24, 4, 1,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(new WeightedStateProvider(stateList)),
                                predicate))));
    }

    private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(WILD_BARLEY_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(WILD_BARLEY_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                        BiomeFilter.biome()
                )));
    }

    private static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(ADD_WILD_BARLEY_KEY, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
                HolderSet.direct(placedFeatures.getOrThrow(WILD_BARLEY_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
}