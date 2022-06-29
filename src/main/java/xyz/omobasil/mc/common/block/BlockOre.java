package xyz.omobasil.mc.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockOre extends Block {

	public BlockOre(boolean deepslate) {
		super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound((deepslate) ? SoundType.DEEPSLATE : SoundType.STONE));
	}
	
}
