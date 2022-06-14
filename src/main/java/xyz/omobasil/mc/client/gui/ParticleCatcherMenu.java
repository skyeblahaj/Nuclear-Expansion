package xyz.omobasil.mc.client.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.omobasil.mc.client.gui.slot.IInventoryMenu;
import xyz.omobasil.mc.client.gui.slot.ResultSlot;
import xyz.omobasil.mc.elements.block.blockentity.ParticleCatcherEntity;
import xyz.omobasil.mc.initialization.BlockInit;
import xyz.omobasil.mc.initialization.ItemInit;
import xyz.omobasil.mc.initialization.MenuTypes;

public class ParticleCatcherMenu extends AbstractContainerMenu implements IInventoryMenu {

	private final ParticleCatcherEntity entity;
	private final Level lvl;
	
	public ParticleCatcherMenu(int id, Inventory inv, FriendlyByteBuf data) {
		this(id, inv, inv.player.level.getBlockEntity(data.readBlockPos()));
	}

	public ParticleCatcherMenu(int id, Inventory inv, BlockEntity entity) {
		super(MenuTypes.PARTICLE_CATCHER_MENU.get(), id);
		checkContainerSize(inv, ParticleCatcherEntity.CONTAINER_SIZE);
		this.entity = (ParticleCatcherEntity) entity;
		this.lvl = inv.player.level;
		addPlayerInventory(inv);
		addPlayerHotbar(inv);
		this.entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			this.addSlot(new RadioactiveInput(handler, 0, 107, 10));
			this.addSlot(new GlassInput(handler, 1, 152, 57));
			this.addSlot(new ResultSlot(handler, 2, 107, 57));
		});
	}
	
	//diesieben07
	private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + ParticleCatcherEntity.CONTAINER_SIZE, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + ParticleCatcherEntity.CONTAINER_SIZE) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
    
	@Override
	public boolean stillValid(Player p_38874_) {
		return stillValid(ContainerLevelAccess.create(lvl, entity.getBlockPos()), p_38874_, BlockInit.PARTICLE_CATCHER.get());
	}

	@Override
	public void addPlayerInventory(Inventory inv) {
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
	}
	
	@Override
	public void addPlayerHotbar(Inventory inv) {
		for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
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
