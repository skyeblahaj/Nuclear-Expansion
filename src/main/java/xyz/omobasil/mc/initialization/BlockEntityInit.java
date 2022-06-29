package xyz.omobasil.mc.initialization;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.common.block.blockentity.ParticleCatcherEntity;

public class BlockEntityInit {

	private BlockEntityInit() {}
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NuclearExpansion.MODID);
	
	public static final RegistryObject<BlockEntityType<ParticleCatcherEntity>> PARTICLE_CATCHER_ENTITY = BLOCK_ENTITIES.register(
			"particle_catcher_block_entity", () -> BlockEntityType.Builder.of(ParticleCatcherEntity::new, BlockInit.PARTICLE_CATCHER.get()).build(null));
	
}
