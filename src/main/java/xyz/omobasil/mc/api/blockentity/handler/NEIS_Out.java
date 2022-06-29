package xyz.omobasil.mc.api.blockentity.handler;

import net.minecraft.world.item.ItemStack;
import xyz.omobasil.mc.api.blockentity.AbstractNuclearExpansionBlockEntity;

public class NEIS_Out extends NuclearExpansionItemStack {

	public NEIS_Out(AbstractNuclearExpansionBlockEntity be, int size) {
		super(be, size);
	}
	
	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return false;
	}

}
