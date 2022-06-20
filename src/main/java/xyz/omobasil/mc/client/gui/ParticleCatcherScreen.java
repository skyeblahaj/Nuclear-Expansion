package xyz.omobasil.mc.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
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

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
		RenderSystem.setShaderTexture(0, GUI);
		int x = leftPos;
		int y = topPos;
		
		this.blit(p_97787_, x, y, 0, 0, imageWidth, imageHeight);
		//System.out.println(this.menu.getBlockEntity().get(7));
		if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.14f) {
			//System.out.println("drawing...");
			int powerBar = 40, raise = 10;
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.29f) {powerBar = 34; raise = 16;}
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.43f) {powerBar = 28; raise = 22;}
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.57f) {powerBar = 22; raise = 28;}
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.71f) {powerBar = 16; raise = 34;}
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.86f) {powerBar = 10; raise = 40;}
			if (this.menu.getBlockEntity().get(7) >= this.menu.getBlockEntity().get(4) * 0.95f) {powerBar = 0; raise = 50;}
			this.blit(p_97787_, x + 7, y + 17, 176, powerBar, 15, raise);
		}
		
		if (this.menu.getBlockEntity().isProcessing()) {
			int animation = 0;
			if (this.menu.getBlockEntity().get(0) >= 0) animation = 0;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.1f) animation = 1;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.2f) animation = 3;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.3f) animation = 5;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.4f) animation = 7;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.5f) animation = 9;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.6f) animation = 11;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.7f) animation = 13;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.8f) animation = 15;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1) * 0.9f) animation = 17;
			if (this.menu.getBlockEntity().get(0) >= this.menu.getBlockEntity().get(1)) animation = 21;
			this.blit(p_97787_, x + 110, y + 31, 176, 50, 10, animation);
			
			if (this.menu.getBlockEntity().get(2) >= 0) {
				int packageAnim = 124;
				if (this.menu.getBlockEntity().get(2) >= this.menu.getBlockEntity().get(3) * 0.333f) packageAnim = 84;
				if (this.menu.getBlockEntity().get(2) >= this.menu.getBlockEntity().get(3) * 0.666f) packageAnim = 104;
				this.blit(p_97787_, x + 129, y + 51, 176, packageAnim, 18, 20);
			}
		}
	}

	@Override
	public void render(PoseStack poseStack, int mX, int mY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mX, mY, delta);
		this.renderTooltip(poseStack, mX, mY);
	}
	
}
