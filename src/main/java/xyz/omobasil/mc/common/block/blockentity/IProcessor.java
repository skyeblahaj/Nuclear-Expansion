package xyz.omobasil.mc.common.block.blockentity;

import net.minecraft.world.level.Level;

public interface IProcessor {
	public default void tick(Level lvl) {
		if (!lvl.isClientSide && canProcess()) {
			process();
		}
	}
	
	public boolean canProcess();
	public void process();
}
