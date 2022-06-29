package xyz.omobasil.mc.api.block;

import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import xyz.omobasil.mc.api.blockentity.IInventoryBlockEntity;

public abstract class AbstractNuclearExpansionBlock extends BaseEntityBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private final BiFunction<BlockPos, BlockState, BlockEntity> function;
	
	public AbstractNuclearExpansionBlock(BiFunction<BlockPos, BlockState, BlockEntity> function) {
		super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL));
		this.function = function;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
		return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation direction) {
		return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState p_60528_, Mirror p_60529_) {
		return p_60528_.rotate(p_60529_.getRotation(p_60528_.getValue(FACING)));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
		if (p_60515_.getBlock() != p_60518_.getBlock()){
			BlockEntity be = p_60516_.getBlockEntity(p_60517_);
			if (be instanceof IInventoryBlockEntity) {
				((IInventoryBlockEntity) be).getDrops();
			}
		}
		super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return this.function.apply(p_153215_, p_153216_);
	}
}