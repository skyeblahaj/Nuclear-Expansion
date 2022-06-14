package xyz.omobasil.mc.initialization;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.client.gui.ParticleCatcherMenu;

public class MenuTypes {
	
	private MenuTypes() {}

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, NuclearExpansion.MODID);;

	public static final RegistryObject<MenuType<ParticleCatcherMenu>> PARTICLE_CATCHER_MENU = registerMenu(ParticleCatcherMenu::new, "particle_catcher_menu");
	
	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenu(IContainerFactory<T> factory, String name){
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}
	
}