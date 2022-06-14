package xyz.omobasil.mc.elements.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockBase extends Block {

	public BlockBase(Material mat, float strength) {
		super(BlockBehaviour.Properties.of(mat).strength(strength).explosionResistance(strength));
		
	}
	
}
