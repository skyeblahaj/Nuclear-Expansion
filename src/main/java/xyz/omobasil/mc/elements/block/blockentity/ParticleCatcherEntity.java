package xyz.omobasil.mc.elements.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.omobasil.mc.client.gui.ParticleCatcherMenu;
import xyz.omobasil.mc.elements.block.machine.IMachineProcessor;
import xyz.omobasil.mc.elements.energy.EnergyBase;
import xyz.omobasil.mc.initialization.BlockEntityInit;
import xyz.omobasil.mc.initialization.ItemInit;

public class ParticleCatcherEntity extends BlockEntity implements MenuProvider, IMachineProcessor {

	public static final int CONTAINER_SIZE = 3;
	
	private int currentDecayTime;
	private int maxDecayTime = 1200; //not final since may add upgrades
	private int currentPackagingTime;
	private int maxPackagingTime = 60; //applies here too
	
	private final ItemStackHandler itemStackHandler = new ItemStackHandler(CONTAINER_SIZE) {
		protected void onContentsChanged(int slot) {
			setChanged();
		};
	};

	private LazyOptional<IItemHandler> lazyHandler = LazyOptional.empty();
	
	private final EnergyBase energyStorage;
	private LazyOptional<EnergyBase> energy;
	private int capacity = 6000; //rf/fe
	private int maxRecieve = 100;
	private int energyPerTick = 20;

	public ParticleCatcherEntity(BlockPos xyz, BlockState state) {
		super(BlockEntityInit.PARTICLE_CATCHER_ENTITY.get(), xyz, state);
		this.energyStorage = createEnergyStorage();
		this.energy = LazyOptional.of(() -> this.energyStorage);
	}
	
	@Override
	public int get(int field) {
		switch(field) {
		case 0: return this.currentDecayTime;
		case 1: return this.maxDecayTime;
		case 2: return this.currentPackagingTime;
		case 3: return this.maxPackagingTime;
		case 4: return this.capacity;
		case 5: return this.maxRecieve;
		case 6: return this.energyPerTick;
		default: return 0;
		}
	}
	
	@Override
	public void set(int field, int newValue) {
		switch(field) {
		case 0: this.currentDecayTime = newValue; break;
		case 1: this.maxDecayTime = newValue; break;
		case 2: this.currentPackagingTime = newValue; break;
		case 3: this.maxPackagingTime = newValue; break;
		case 4: this.capacity = newValue; break;
		case 5: this.maxRecieve = newValue; break;
		case 6: this.energyPerTick = newValue; break;
		}
	}

	@Override
	public Component getDisplayName() {
		return new TextComponent("Particle Catcher");
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return this.lazyHandler.cast();
		if (cap == CapabilityEnergy.ENERGY) this.energy.cast();
		return super.getCapability(cap, side);
	}

	@Override
	public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
		return new ParticleCatcherMenu(p_39954_, p_39955_, this);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyHandler = LazyOptional.of(() -> itemStackHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyHandler.invalidate();
		energy.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("inventory", itemStackHandler.serializeNBT());
		tag.putInt("energy", getEnergy());
		super.saveAdditional(tag);
	}

	public int getEnergy() {
		return energyStorage.getEnergyStored();
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
		energyStorage.setEnergy(nbt.getInt("energy"));
	}

	public void drops() {
		SimpleContainer container = new SimpleContainer(itemStackHandler.getSlots());
		for (int i = 0; i < itemStackHandler.getSlots(); i++) {
			container.setItem(i, itemStackHandler.getStackInSlot(i));
		}
		Containers.dropContents(this.level, this.worldPosition, container);
	}

	public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ParticleCatcherEntity blockEntity) {
		if (hasRecipe(blockEntity) && hasNotReachedStackLimit(blockEntity) && hasEnoughEnergy(blockEntity)) {
			if (blockEntity.currentDecayTime >= blockEntity.maxDecayTime) {
				//STARTS PACKAGING
				if (blockEntity.currentPackagingTime >= blockEntity.maxPackagingTime) {
					blockEntity.currentDecayTime = 0;
					blockEntity.currentPackagingTime = 0;
					craftItem(blockEntity); //PROCESS FINISHED
				}
				else {
					blockEntity.currentPackagingTime++;
					blockEntity.energyStorage.extractEnergy(20, false); //USING ENERGY TO PACKAGE
				}
				
			} else {
				blockEntity.currentDecayTime++;
				blockEntity.energyStorage.extractEnergy(20, false); //USING ENERGY TO CAPTURE PARTICLES
			}
		} else {
			if (blockEntity.currentDecayTime != 0) blockEntity.currentDecayTime = 0;
			if (blockEntity.currentPackagingTime != 0) blockEntity.currentPackagingTime = 0;
		}
	}

	private static void craftItem(ParticleCatcherEntity entity) {
		entity.itemStackHandler.extractItem(0, 1, false); //take radioactive material
		entity.itemStackHandler.extractItem(1, 1, false); //take glass pane

		entity.itemStackHandler.setStackInSlot(2, new ItemStack(ItemInit.ALPHA_PARTICLES.get(), entity.itemStackHandler.getStackInSlot(2).getCount() + 1));
	}

	private static boolean hasRecipe(ParticleCatcherEntity entity) {
		boolean uraniumRecipe = (entity.itemStackHandler.getStackInSlot(0).getItem() == ItemInit.RAW_URANIUM.get()) && (entity.itemStackHandler.getStackInSlot(1).getItem() == Items.GLASS_PANE);
		boolean thoriumRecipe = (entity.itemStackHandler.getStackInSlot(0).getItem() == ItemInit.RAW_THORIUM.get()) && (entity.itemStackHandler.getStackInSlot(1).getItem() == Items.GLASS_PANE);
		return uraniumRecipe || thoriumRecipe;
	}

	private static boolean hasNotReachedStackLimit(ParticleCatcherEntity entity) {
		return entity.itemStackHandler.getStackInSlot(2).getCount() < entity.itemStackHandler.getStackInSlot(2).getMaxStackSize();
	}
	
	private static boolean hasEnoughEnergy(ParticleCatcherEntity entity) {
		return entity.energyStorage.getEnergyStored() >= entity.energyPerTick;
	}
	
	@Override
	public boolean isProcessing() {
		return this.currentDecayTime > 0 || this.currentPackagingTime > 0;
	}
	
	private EnergyBase createEnergyStorage() {
		return new EnergyBase(this, this.capacity, this.maxRecieve, 0);
	}
	
}