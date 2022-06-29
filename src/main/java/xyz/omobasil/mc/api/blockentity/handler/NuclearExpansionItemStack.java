package xyz.omobasil.mc.api.blockentity.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.omobasil.mc.api.blockentity.AbstractNuclearExpansionBlockEntity;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public class NuclearExpansionItemStack extends ItemStackHandler {

	private AbstractNuclearExpansionBlockEntity blockEntity;
	
	public NuclearExpansionItemStack(AbstractNuclearExpansionBlockEntity be, int size) {
		super(size);
		this.blockEntity = be;
	}
	
	public void increment(int slot, int amount) {
		ItemStack stack = this.getStackInSlot(slot);
		if (stack.getCount() + amount <= stack.getMaxStackSize()) {
			stack.setCount(stack.getCount() + amount);
		}
	}
	
	public void set(int slot, ItemStack stack) {
		if (!stack.isEmpty()) {
			if (this.getStackInSlot(slot).isEmpty()) {
				this.setStackInSlot(slot, stack);
			} else {
				this.increment(slot, stack.getCount());
			}
		}
	}
	
	public void decrement(int slot, int amount) {
		ItemStack stack = this.getStackInSlot(slot);
		if (stack.isEmpty()) return;
		if (stack.getCount() - amount < 0) return;
		
		stack.shrink(amount);
		if (stack.getCount() <= 0) {
			this.setStackInSlot(slot, ItemStack.EMPTY);
		} else {
			this.setStackInSlot(slot, stack);
		}
	}
	
	public List<ItemStack> getSlotsAsList() {
        List<ItemStack> toReturn = new ArrayList<>();
        for (int index = 0; index < getSlots(); index++) {
            toReturn.add(getStackInSlot(index));
        }
        return toReturn;
    }
	
	public boolean eject(Direction dir) {
		BlockEntity be = Objects.requireNonNull(this.blockEntity.getLevel()).getBlockEntity(this.blockEntity.getBlockPos()
						 .offset(dir.getNormal()));
		LazyOptional<IItemHandler> origin = this.blockEntity.getCapability(ITEM_HANDLER_CAPABILITY, dir),
								   target = Objects.requireNonNull(be).getCapability(ITEM_HANDLER_CAPABILITY, dir.getOpposite());
		if (origin.isPresent() && target.isPresent()) {
			IItemHandler self = origin.orElseThrow(NullPointerException::new),
					  other = target.orElseThrow(NullPointerException::new);
			for (int i = 0; i < other.getSlots(); i++) {
	            for (int j = 0; j < self.getSlots(); j++) {
	                if (!self.getStackInSlot(j).isEmpty()) {
	                    int stackSize = self.getStackInSlot(j).getCount();
	                    if (other.insertItem(i, self.extractItem(j, stackSize, true), true).isEmpty()) {
	                        other.insertItem(i, self.extractItem(j, stackSize, false), false);
	                        return true;
	                    }
	                }
	            }
	        }
		}
		return false;
	}
}