package xyz.omobasil.mc.api.blockentity;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xyz.omobasil.mc.api.blockentity.handler.AutomationStack;
import xyz.omobasil.mc.api.blockentity.handler.NuclearExpansionItemStack;

public interface IInventoryBlockEntity {
	
	public void getDrops();
	public AutomationStack getAutomationInput(IItemHandlerModifiable handler);
	public AutomationStack getAutomationOutput(IItemHandlerModifiable handler);
	public NuclearExpansionItemStack initializeInput();
	public NuclearExpansionItemStack initializeOutput();
	public NuclearExpansionItemStack getInputHandler();
	public NuclearExpansionItemStack getOutputHandler();
	public CombinedInvWrapper getAutomationInv();
	
}