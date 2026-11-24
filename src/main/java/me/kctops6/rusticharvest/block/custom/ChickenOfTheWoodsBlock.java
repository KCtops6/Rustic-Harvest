package me.kctops6.rusticharvest.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChickenOfTheWoodsBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape NORTH_SHAPE = Block.box(3.0D, 4.0D, 12.0D, 13.0D, 12.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(3.0D, 4.0D, 0.0D, 13.0D, 12.0D, 4.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(12.0D, 4.0D, 3.0D, 16.0D, 12.0D, 13.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0D, 4.0D, 3.0D, 4.0D, 12.0D, 13.0D);

    public ChickenOfTheWoodsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction opposite = direction.getOpposite();
                state = state.setValue(FACING, opposite);
                if (state.canSurvive(levelreader, blockpos)) {
                    return state;
                }
            }
        }
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos supportPos = pos.relative(direction);
        BlockState supportState = level.getBlockState(supportPos);
        return supportState.is(net.minecraft.tags.BlockTags.LOGS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}