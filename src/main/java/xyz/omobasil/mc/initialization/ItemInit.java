package xyz.omobasil.mc.initialization;

import com.google.common.base.Supplier;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.elements.item.ItemBase;

public class ItemInit {

public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearExpansion.MODID);
	
	private static <E extends Item> RegistryObject<E> register(String name, Supplier<E> item){
		return ITEMS.register(name, item);
	}
	
	////////////////////////////////
	
	public static final RegistryObject<Item> URANIUM_235_INGOT = register("ingot_uranium_235", () -> new ItemBase()),
											 URANIUM_238_INGOT = register("ingot_uranium_238", () -> new ItemBase()),
											 PLUTONIUM_INGOT = register("ingot_plutonium", () -> new ItemBase()),
											 BERYLLIUM_INGOT = register("ingot_beryllium", () -> new ItemBase()),
											 FLUORITE_SHARD = register("shard_fluorite", () -> new ItemBase()),
											 ETHYLENE_MULCH = register("ethylene_mulch", () -> new ItemBase()),
											 TEFLON_PLATE = register("plate_teflon", () -> new ItemBase()),
											 CARBON_COMPOUND = register("carbon_compound", () -> new ItemBase()),
											 RAW_BERYLLIUM = register("raw_beryllium", () -> new ItemBase()),
											 RAW_URANIUM = register("raw_uranium", () -> new ItemBase()),
											 RAW_THORIUM = register("raw_thorium", () -> new ItemBase()),
											 ALPHA_PARTICLES = register("alpha_particles", () -> new ItemBase());
											 
	
}
