package xyz.omobasil.mc.api.blockentity;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import xyz.omobasil.mc.api.blockentity.handler.EnergyStorage;

public interface IEnergyBlock {

	public EnergyStorage energy = new EnergyStorage(0);
	public LazyOptional<IEnergyStorage> storage = LazyOptional.of(() -> energy);
	
	public EnergyStorage initializeEnergyStorage();
	
}