package xyz.omobasil.mc.elements.block.machine;

public interface IMachineProcessor {

	//craftItem separate private static void methods
	
	public void set(int field, int newValue);
	
	public int get(int field);
	
	public boolean isProcessing();
	
}
