package xyz.omobasil.mc.initialization;

import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.omobasil.mc.NuclearExpansion;
import xyz.omobasil.mc.common.block.BlockBase;
import xyz.omobasil.mc.common.block.BlockOre;
import xyz.omobasil.mc.common.block.machine.ParticleCatcher;

public class BlockInit {

public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create/*builder*/(ForgeRegistries.BLOCKS, NuclearExpansion.MODID);
	
private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
}

private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
    ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(NuclearExpansion.TAB)));
}
	
	////////////////////////////////////////////////////
	
	public static final RegistryObject<Block> DEVELOPER = registerBlock("developer", () -> new BlockBase(Material.BARRIER, 10f)),
	
											  //METAL BLOCKS
											  BLOCK_OF_URANIUM_235 = registerBlock("block_uranium_235", () -> new BlockBase(Material.HEAVY_METAL, 5f)),
											  BLOCK_OF_URANIUM_238 = registerBlock("block_uranium_238", () -> new BlockBase(Material.HEAVY_METAL, 6f)),
											  BLOCK_OF_PLUTONIUM = registerBlock("block_plutonium", () -> new BlockBase(Material.HEAVY_METAL, 6f)),
											  BLOCK_OF_THORIUM = registerBlock("block_thorium", () -> new BlockBase(Material.HEAVY_METAL, 5.5f)),
											  BLOCK_OF_BERYLLIUM = registerBlock("block_beryllium", () -> new BlockBase(Material.HEAVY_METAL, 4f)),
											  
											  //BUILDING BLOCKS
											  TEFLON_SHEET_BLOCK = registerBlock("sheet_teflon", () -> new BlockBase(Material.STONE, 10f)),
											  REACTOR_CASING = registerBlock("reactor_casing", () -> new BlockBase(Material.METAL, 8f)),
											  
											  
											  //ORES
											  ORE_BERYLLIUM = registerBlock("ore_beryllium", () -> new BlockOre(false)),
											  ORE_BERYLLIUM_DEEPSLATE = registerBlock("ore_beryllium_deepslate", () -> new BlockOre(true)), //may add later
											  ORE_URANIUM = registerBlock("ore_uranium", () -> new BlockOre(false)),
											  ORE_URANIUM_DEEPSLATE = registerBlock("ore_uranium_deepslate", () -> new BlockOre(true)),
											  ORE_THORIUM = registerBlock("ore_thorium", () -> new BlockOre(false)),
											  ORE_THORIUM_DEEPSLATE = registerBlock("ore_thorium_deepslate", () -> new BlockOre(true)),
	
											  //MACHINES
											  CATALYTIC_COMPOSTER = registerBlock("catalytic_composter", () -> new BlockBase(Material.METAL, 4f)),
											  PARTICLE_CATCHER = registerBlock("particle_catcher", () -> new ParticleCatcher());
	
}
