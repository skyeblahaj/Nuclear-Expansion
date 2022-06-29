package xyz.omobasil.mc.common.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.initialization.ItemInit;

public class NucExpTab extends CreativeModeTab {

	public NucExpTab() {
		super(NuclearExpansion.MODNAME);
		
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.URANIUM_235_INGOT.get());
	}

}
