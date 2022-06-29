package xyz.omobasil.mc.common.block.blockentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xyz.omobasil.mc.api.blockentity.AbstractNuclearExpansionBlockEntity;
import xyz.omobasil.mc.api.blockentity.IEnergyBlock;
import xyz.omobasil.mc.api.blockentity.IInventoryBlockEntity;
import xyz.omobasil.mc.api.blockentity.handler.AutomationStack;
import xyz.omobasil.mc.api.blockentity.handler.EnergyStorage;
import xyz.omobasil.mc.api.blockentity.handler.NEIS_Out;
import xyz.omobasil.mc.api.blockentity.handler.NuclearExpansionItemStack;
import xyz.omobasil.mc.client.gui.ParticleCatcherMenu;
import xyz.omobasil.mc.initialization.BlockEntityInit;
import xyz.omobasil.mc.initialization.ItemInit;

public class ParticleCatcherEntity extends AbstractNuclearExpansionBlockEntity implements IInventoryBlockEntity, IEnergyBlock, IProcessor {

	public static final int CONTAINER_INPUTS = 2;
	public static final int CONTAINER_OUTPUTS = 1;
	public static final int CONTAINER_SIZE = CONTAINER_INPUTS + CONTAINER_OUTPUTS;
	
	private int currentDecayTime = 0;
	private int maxDecayTime = 1200; //not final since may add upgrades
	private int currentPackagingTime = 0;
	private int maxPackagingTime = 60; //applies here too
	
	protected final ContainerData data;

	private final EnergyStorage energy = initializeEnergyStorage();
	private final LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> energy);
	
	private final NuclearExpansionItemStack input = initializeInput(),
											output = initializeOutput();
	private final AutomationStack automationInput = getAutomationInput(this.input),
								  automationOutput = getAutomationOutput(this.output);
	private final CombinedInvWrapper wrapper = new CombinedInvWrapper(this.automationInput, this.automationOutput);
	private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> this.wrapper);
	
	private int capacity = 6000; //rf/fe
	private int maxRecieve = 200; //input a tick
	private final int energyPerTick = 20; //power consumption

	public ParticleCatcherEntity(BlockPos xyz, BlockState state) {
		super(BlockEntityInit.PARTICLE_CATCHER_ENTITY.get(), xyz, state);
		this.data = new ContainerData() {

			@Override
			public int get(int p_39284_) {
				return switch (p_39284_) {
				case 0 -> currentDecayTime;
				case 1 -> maxDecayTime;
				case 2 -> currentPackagingTime;
				case 3 -> maxPackagingTime;
				case 4 -> energy.getEnergyStored();
				case 5 -> energy.getMaxEnergyStored();
				default -> 0;
				};
			}

			@Override
			public void set(int p_39285_, int p_39286_) {
				switch (p_39285_) {
				case 0 -> currentDecayTime = p_39286_;
				//case 1 -> maxDecayTime = p_39286_;
				case 1 -> currentPackagingTime = p_39286_;
				case 2 -> energy.setEnergy(p_39286_);
				}
			}

			@Override
			public int getCount() {
				return 6;
			}
		};
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return this.lazyItemHandler.cast();
		else if (cap == CapabilityEnergy.ENERGY)
			return this.lazyEnergyHandler.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
		return new ParticleCatcherMenu(p_39954_, p_39955_, this, this.data);
	}
	
	@Override
	public EnergyStorage initializeEnergyStorage() {
		return new EnergyStorage(this.capacity) {
			@Override
			protected void onEnergyChanged() {
				setChanged();
			}
		};
	}

	@Override
	public NuclearExpansionItemStack initializeInput() {
		return new NuclearExpansionItemStack(this, CONTAINER_INPUTS);
	}
	
	@Override
	public NuclearExpansionItemStack initializeOutput() {
		return new NEIS_Out(this, CONTAINER_OUTPUTS);
	}
	
	@Override
	public NuclearExpansionItemStack getInputHandler() {
		return this.input;
	}
	
	@Override
	public NuclearExpansionItemStack getOutputHandler() {
		return this.output;
	}
	
	@Override
	public AutomationStack getAutomationInput(IItemHandlerModifiable handler) {
		return new AutomationStack(handler) {
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				return ItemStack.EMPTY;
			}
		};
	}
	
	@Override
	public AutomationStack getAutomationOutput(IItemHandlerModifiable handler) {
		return new AutomationStack(handler) {
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				if (getStackInSlot(slot).isEmpty()) {
					return super.extractItem(slot, amount, simulate);
				}
				return ItemStack.EMPTY;
			}
		};
	}
	
	@Override
	public CombinedInvWrapper getAutomationInv() {
		return this.wrapper;
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
		lazyEnergyHandler.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.putInt("decay time", this.currentDecayTime);
		tag.putInt("packaging time", this.currentPackagingTime);
		tag.put("input", this.input.serializeNBT());
		tag.put("output", this.output.serializeNBT());
		tag.put("energy", this.energy.serializeNBT());
		super.saveAdditional(tag);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.currentDecayTime = nbt.getInt("decay time");
		this.currentPackagingTime = nbt.getInt("packaging time");
		this.input.deserializeNBT(nbt.getCompound("input"));
		this.output.deserializeNBT(nbt.getCompound("output"));
		this.energy.deserializeNBT(nbt.getCompound("energy"));
	}

	@Override
	public boolean canProcess() {
		boolean[] recipes = {this.input.getStackInSlot(0).is(ItemInit.RAW_URANIUM.get()) && this.input.getStackInSlot(0).getCount() > 0,
							  this.input.getStackInSlot(0).is(ItemInit.RAW_THORIUM.get()) && this.input.getStackInSlot(0).getCount() > 0};
		boolean jar = this.input.getStackInSlot(1).is(Items.GLASS_BOTTLE) && this.input.getStackInSlot(1).getCount() > 0;
		boolean output = this.output.getStackInSlot(2).getCount() < this.output.getStackInSlot(2).getMaxStackSize();
		
		for (boolean b : recipes) {
			if (b) return jar && output;
		}
		
		return false;
	}

	@Override
	public void process() {
		
		if (this.currentDecayTime <= this.maxDecayTime) this.currentDecayTime++;
		else {
			if (this.currentPackagingTime <= this.maxPackagingTime) this.currentPackagingTime++;
			else {
				this.currentDecayTime = 0;
				this.currentPackagingTime = 0;
				this.input.decrement(0, 1);
				this.input.decrement(1, 1);
				this.output.set(2, new ItemStack(ItemInit.ALPHA_PARTICLES.get(),
						this.output.getStackInSlot(2).getCount() + 1));
			}
		}
		this.energy.extractEnergy(this.energyPerTick, false);
	}
}