package xyz.omobasil.mc.initialization;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.common.world.gen.OreGen;

@EventBusSubscriber(modid=NuclearExpansion.MODID)
public class WorldInit {

	@SubscribeEvent
	public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
		OreGen.generateOres(event);
	}
	
}
