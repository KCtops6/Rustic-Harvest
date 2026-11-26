package me.kctops6.rusticharvest.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TallCropBlock extends BaseCropBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    private final int doubleBlockAge;

    public TallCropBlock(Properties properties, Supplier<ItemLike> seedSupplier, int doubleBlockAge) {
        super(properties, seedSupplier);
        this.doubleBlockAge = doubleBlockAge;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(this.getAgeProperty(), 0)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int age = this.getAge(state);
        return state.getValue(HALF) == DoubleBlockHalf.UPPER
                ? UPPER_SHAPE_BY_AGE[age]
                : LOWER_SHAPE_BY_AGE[age];
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && super.isRandomlyTicking(state);
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState belowState = level.getBlockState(pos.below());
            return belowState.is(this)
                    && belowState.getValue(HALF) == DoubleBlockHalf.LOWER
                    && this.getAge(belowState) >= this.doubleBlockAge;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9) {
            int currentAge = this.getAge(state);
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(state, level, pos);
                // Multiplied by 0.5f to preserve your slower corn growth rate
                if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) (25.0F / growthSpeed) + 1) == 0 && random.nextFloat() < 0.5F)) {
                    this.setGrowthStage(level, pos, currentAge + 1);
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
        BlockState lowerState = level.getBlockState(lowerPos);
        if (!lowerState.is(this)) return;

        int nextAge = Math.min(this.getMaxAge(), this.getAge(lowerState) + this.getBonemealAgeIncrease(level));
        this.setGrowthStage(level, lowerPos, nextAge);
    }

    private void setGrowthStage(Level level, BlockPos lowerPos, int targetAge) {
        if (targetAge >= this.doubleBlockAge) {
            BlockPos upperPos = lowerPos.above();
            BlockState upperState = level.getBlockState(upperPos);
            // Only grow into the upper block if it's empty or already this crop's upper half
            if (level.isEmptyBlock(upperPos) || (upperState.is(this) && upperState.getValue(HALF) == DoubleBlockHalf.UPPER)) {
                level.setBlock(lowerPos, this.defaultBlockState().setValue(AGE, targetAge).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                level.setBlock(upperPos, this.defaultBlockState().setValue(AGE, targetAge).setValue(HALF, DoubleBlockHalf.UPPER), 3);
            } else {
                // Cap growth just below doubleBlockAge if blocked by a solid block above
                level.setBlock(lowerPos, this.defaultBlockState().setValue(AGE, this.doubleBlockAge - 1).setValue(HALF, DoubleBlockHalf.LOWER), 2);
            }
        } else {
            level.setBlock(lowerPos, this.defaultBlockState().setValue(AGE, targetAge).setValue(HALF, DoubleBlockHalf.LOWER), 2);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            DoubleBlockHalf half = state.getValue(HALF);
            BlockPos otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
            BlockState otherState = level.getBlockState(otherPos);
            if (otherState.is(this) && otherState.getValue(HALF) != half) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos, Block.getId(otherState));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}