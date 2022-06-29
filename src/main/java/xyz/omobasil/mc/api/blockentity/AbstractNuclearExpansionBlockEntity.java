package xyz.omobasil.mc.api.blockentity;

import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import xyz.omobasil.mc.NuclearExpansion;

public abstract class AbstractNuclearExpansionBlockEntity extends BlockEntity implements MenuProvider, Nameable {

	private Component name;
	
	public AbstractNuclearExpansionBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	@Override
	public Component getDisplayName() {
		return this.getName();
	}
	
	@Override
	@Nonnull
	public Component getName() {
		return this.name != null ? this.name : this.getDefaultName();
	}
	
	public void setName(Component name) {
		this.name = name;
	}
	
	protected Component getDefaultName() {
		return new TranslatableComponent(String.format("%s.container.%s", NuclearExpansion.MODID,
																		  Objects.requireNonNull(getType()
																				  .getRegistryName())
																		  		  .getPath()));
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		Objects.requireNonNull(pkt.getTag());
		this.load(pkt.getTag());
		super.onDataPacket(net, pkt);
	}
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	public void getDrops() {
		if (this instanceof IInventoryBlockEntity) {
			CombinedInvWrapper handler = ((IInventoryBlockEntity) this).getAutomationInv();
			SimpleContainer container = new SimpleContainer(handler.getSlots());
			for (int i = 0; i < handler.getSlots(); i++) {
				container.setItem(i, handler.getStackInSlot(i));
			}
			Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, container);
		}
	}
}