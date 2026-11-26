package me.kctops6.rusticharvest.datagen;

import me.kctops6.rusticharvest.block.ModBlocks;
import me.kctops6.rusticharvest.block.custom.BaseCropBlock;
import me.kctops6.rusticharvest.block.custom.BlueberryBushBlock;
import me.kctops6.rusticharvest.block.custom.TallCropBlock;
import me.kctops6.rusticharvest.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        // Standard Crops
        add(ModBlocks.BARLEY_CROP.get(), createCropDrops(ModBlocks.BARLEY_CROP.get(), ModItems.BARLEY.get(), ModItems.BARLEY_SEEDS.get(),
                cropCondition(ModBlocks.BARLEY_CROP.get(), 7)));

        add(ModBlocks.GARLIC_CROP.get(), createCropDrops(ModBlocks.GARLIC_CROP.get(), ModItems.GARLIC.get(), ModItems.GARLIC_SEEDS.get(),
                cropCondition(ModBlocks.GARLIC_CROP.get(), 7)));

        add(ModBlocks.RYE_CROP.get(), createCropDrops(ModBlocks.RYE_CROP.get(), ModItems.RYE.get(), ModItems.RYE_SEEDS.get(),
                cropCondition(ModBlocks.RYE_CROP.get(), 7)));

        // Corn Crop (TallCropBlock - drops only from LOWER half)
        LootItemCondition.Builder cornMatureLower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(TallCropBlock.AGE, 7)
                        .hasProperty(TallCropBlock.HALF, DoubleBlockHalf.LOWER));

        LootItemCondition.Builder cornAnyLower = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CORN_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(TallCropBlock.HALF, DoubleBlockHalf.LOWER));

        add(ModBlocks.CORN_CROP.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.CORN.get())
                                .when(cornMatureLower)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 7.0F)))
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.5F, 2))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.CORN_SEEDS.get())
                                .when(cornAnyLower))));

        // Blueberry Bush
        add(ModBlocks.BLUEBERRY_BUSH.get(), block -> applyExplosionDecay(block,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BLUEBERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlueberryBushBlock.AGE, 3)))
                                .add(LootItem.lootTableItem(ModItems.BLUEBERRIES.get()))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))
                        .withPool(LootPool.lootPool()
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BLUEBERRY_BUSH.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlueberryBushBlock.AGE, 2)))
                                .add(LootItem.lootTableItem(ModItems.BLUEBERRIES.get()))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))));

        // Chicken of the Woods
        dropSelf(ModBlocks.CHICKEN_OF_THE_WOODS.get());
    }

    private LootItemCondition.Builder cropCondition(Block crop, int age) {
        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(crop)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropBlock.AGE, age));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}