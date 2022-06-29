package xyz.omobasil.mc.api.blockentity.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import xyz.omobasil.mc.api.blockentity.AbstractNuclearExpansionBlockEntity;
import xyz.omobasil.mc.client.gui.slot.IInventoryMenu;
import xyz.omobasil.mc.common.block.blockentity.ParticleCatcherEntity;

public abstract class AbstractNuclearExpansionMenu extends AbstractContainerMenu implements IInventoryMenu {

	private final ContainerData data;
	private final AbstractNuclearExpansionBlockEntity blockEntity;
	
	protected AbstractNuclearExpansionMenu(MenuType<?> type, int id, Inventory inv, BlockEntity be, ContainerData data) {
		super(type, id);
		this.data = data;
		addPlayerHotbar(inv);
		addPlayerInventory(inv);
		addDataSlots(data);
		this.blockEntity = (AbstractNuclearExpansionBlockEntity) be;
	}

	// diesieben07
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
		if (sourceSlot == null || !sourceSlot.hasItem())
			return ItemStack.EMPTY; // EMPTY_ITEM
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyOfSourceStack = sourceStack.copy();

		// Check if the slot clicked is one of the vanilla container slots
		if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into the tile inventory
			if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
					TE_INVENTORY_FIRST_SLOT_INDEX + ParticleCatcherEntity.CONTAINER_SIZE, false)) {
				return ItemStack.EMPTY; // EMPTY_ITEM
			}
		} else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + ParticleCatcherEntity.CONTAINER_SIZE) {
			// This is a TE slot so merge the stack into the players inventory
			if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
					false)) {
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
	
}