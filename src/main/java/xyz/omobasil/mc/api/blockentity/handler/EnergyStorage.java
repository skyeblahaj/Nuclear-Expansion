package xyz.omobasil.mc.api.blockentity.handler;

public class EnergyStorage extends net.minecraftforge.energy.EnergyStorage {

	public EnergyStorage(int capacity) {
		super(capacity);
	}
	
	protected void onEnergyChanged() {}
	
	public void setEnergy(int energy) {
		this.energy = energy;
		onEnergyChanged();
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getEnergyStored();
		}
		onEnergyChanged();
	}
	
	public void consumeEnergy(int energy) {
		this.energy -= energy;
		if (this.energy < 0) {
			this.energy = 0;
		}
		onEnergyChanged();
	}
}