package xyz.omobasil.mc.elements.energy;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;
import xyz.omobasil.mc.util.Functions;

public class EnergyBase extends EnergyStorage {

	private final BlockEntity blockEntity;
	
	public EnergyBase(BlockEntity blockEntity, int capacity) {
		super(capacity);
		this.blockEntity = blockEntity;
	}

	public EnergyBase(BlockEntity blockEntity, int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
		this.blockEntity = blockEntity;
	}

	public EnergyBase(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
		this.blockEntity = blockEntity;
	}

	public EnergyBase(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
		this.blockEntity = blockEntity;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		this.blockEntity.setChanged();
		return super.extractEnergy(maxExtract, simulate);
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		this.blockEntity.setChanged();
		return super.receiveEnergy(maxReceive, simulate);
	}
	
	public void setEnergy(int energy) {
		this.energy = Functions.clampInt(0, energy, this.capacity);
	}
	
	
	
	
	
}
