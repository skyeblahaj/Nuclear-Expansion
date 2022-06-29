package xyz.omobasil.mc.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.omobasil.mc.NuclearExpansion;

public class ParticleCatcherScreen extends AbstractContainerScreen<ParticleCatcherMenu> {

	private static final ResourceLocation GUI = new ResourceLocation(NuclearExpansion.MODID, "textures/gui/particle_catcher.png");
	
	public ParticleCatcherScreen(ParticleCatcherMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
	}	
}