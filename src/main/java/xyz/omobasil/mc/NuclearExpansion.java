package xyz.omobasil.mc;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.omobasil.mc.client.gui.ParticleCatcherScreen;
import xyz.omobasil.mc.elements.tab.NucExpTab;
import xyz.omobasil.mc.initialization.BlockEntityInit;
import xyz.omobasil.mc.initialization.BlockInit;
import xyz.omobasil.mc.initialization.ItemInit;
import xyz.omobasil.mc.initialization.MenuTypes;

@Mod(NuclearExpansion.MODID)
public class NuclearExpansion {

	public static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "nuclearexpansion";
	public static final String MODNAME = "Nuclear Expansion";
	public static final CreativeModeTab TAB = new NucExpTab();
	
	public NuclearExpansion() {
		
		final IEventBus pointer = FMLJavaModLoadingContext.get().getModEventBus();
		
		ItemInit.ITEMS.register(pointer);
		BlockInit.BLOCKS.register(pointer);
		BlockEntityInit.BLOCK_ENTITIES.register(pointer);
		MenuTypes.MENUS.register(pointer);
		
		pointer.addListener(this::buildClient);
		
		////////////////////////////////////////////////
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void buildClient(final FMLClientSetupEvent event) {
		MenuScreens.register(MenuTypes.PARTICLE_CATCHER_MENU.get(), ParticleCatcherScreen::new);
	}
	
	@SubscribeEvent
	public void serverStart(ServerStartingEvent event) {
		LOGGER.info(MODID + " server starting...");
	}
	
	
	
}