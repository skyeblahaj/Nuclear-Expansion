package xyz.omobasil.mc.api.blockentity.gui;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class AbstractNuclearExpansionScreen<Q extends AbstractContainerMenu> extends AbstractContainerScreen<Q> {
	
	public AbstractNuclearExpansionScreen(Q p_97741_, Inventory p_97742_, Component p_97743_) {
		super(p_97741_, p_97742_, p_97743_);
	}

	public void drawEnergy() {
		
	}
}