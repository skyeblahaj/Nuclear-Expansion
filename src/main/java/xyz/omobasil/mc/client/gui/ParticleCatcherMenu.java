package xyz.omobasil.mc.client.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.omobasil.mc.api.blockentity.gui.AbstractNuclearExpansionMenu;
import xyz.omobasil.mc.client.gui.slot.IInventoryMenu;
import xyz.omobasil.mc.client.gui.slot.ResultSlot;
import xyz.omobasil.mc.common.block.blockentity.ParticleCatcherEntity;
import xyz.omobasil.mc.initialization.BlockInit;
import xyz.omobasil.mc.initialization.ItemInit;
import xyz.omobasil.mc.initialization.MenuTypes;

public class ParticleCatcherMenu extends AbstractNuclearExpansionMenu implements IInventoryMenu {

	private final ParticleCatcherEntity entity;
	private final Level lvl;
	
	public ParticleCatcherMenu(int id, Inventory inv, FriendlyByteBuf data) {
		this(id, inv, inv.player.level.getBlockEntity(data.readBlockPos()), new SimpleContainerData(6));
	}

	public ParticleCatcherMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
		super(MenuTypes.PARTICLE_CATCHER_MENU.get(), id, inv, entity, data);
		checkContainerSize(inv, ParticleCatcherEntity.CONTAINER_SIZE);
		this.entity = (ParticleCatcherEntity) entity;
		this.lvl = inv.player.level;
		this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			this.addSlot(new RadioactiveInput(handler, 0, 107, 10));
			this.addSlot(new GlassInput(handler, 1, 152, 57));
			this.addSlot(new ResultSlot(handler, 2, 107, 57));
		});
	}
	
	
    
	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(lvl, entity.getBlockPos()), p_38874_, BlockInit.PARTICLE_CATCHER.get());
	}

	
	
	public ParticleCatcherEntity getBlockEntity() {
		return this.entity;
	}
	
	private class RadioactiveInput extends SlotItemHandler {

		public RadioactiveInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		
		@Override
		public boolean mayPlace(ItemStack stack) {
			if (stack.getItem() == ItemInit.RAW_URANIUM.get()) return true;
			if (stack.getItem() == ItemInit.RAW_THORIUM.get()) return true;
			return false;
		}
		
	}
	
	private class GlassInput extends SlotItemHandler {

		public GlassInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
		}
		@Override
		public boolean mayPlace(ItemStack stack) {
			return stack.getItem() == Items.GLASS_PANE;
		}
	}
}
