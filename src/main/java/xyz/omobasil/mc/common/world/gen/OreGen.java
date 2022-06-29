package xyz.omobasil.mc.common.world.gen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import xyz.omobasil.mc.common.world.gen.placed.OrePlaced;
import xyz.omobasil.mc.initialization.BlockInit;

public class OreGen {
	
	public static final List<List<OreConfiguration.TargetBlockState>> NUCLEAR_EXPANSION_ORES = new ArrayList<>();
	
	public static final List<OreConfiguration.TargetBlockState> ORE_BERYLLIUM_TARGET = List.of(
			OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.ORE_BERYLLIUM.get().defaultBlockState()),
			OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.ORE_BERYLLIUM_DEEPSLATE.get().defaultBlockState()));
	
	public static final List<OreConfiguration.TargetBlockState> ORE_URANIUM_TARGET = List.of(
			OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.ORE_URANIUM.get().defaultBlockState()),
			OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.ORE_URANIUM_DEEPSLATE.get().defaultBlockState()));
	
	public static final List<OreConfiguration.TargetBlockState> ORE_THORIUM_TARGET = List.of(
			OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.ORE_THORIUM.get().defaultBlockState()),
			OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, BlockInit.ORE_THORIUM_DEEPSLATE.get().defaultBlockState()));
	
	public static final List<OreConfiguration.TargetBlockState> DUMMY_TARGET = List.of(
			OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockInit.DEVELOPER.get().defaultBlockState()));
	
	//////////////////
	
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_BERYLLIUM = FeatureUtils.register(
			"ore_beryllium", Feature.ORE, new OreConfiguration(ORE_BERYLLIUM_TARGET, 7));
	
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_URANIUM = FeatureUtils.register(
			"ore_uranium", Feature.ORE, new OreConfiguration(ORE_URANIUM_TARGET, 4));
	
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_THORIUM = FeatureUtils.register(
			"ore_thorium", Feature.ORE, new OreConfiguration(ORE_THORIUM_TARGET, 2));
	
	public static final Holder<ConfiguredFeature<OreConfiguration, ?>> DUMMY = FeatureUtils.register("dummy", Feature.ORE, new OreConfiguration(DUMMY_TARGET, 0));
	
	//////////////////
	
	public static void generateOres(BiomeLoadingEvent event) {
		
		List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(
				GenerationStep.Decoration.UNDERGROUND_ORES);
		base.add(OrePlaced.ORE_BERYLLIUM_PLACED);
		base.add(OrePlaced.ORE_BERYLLIUM_DEEPSLATE_PLACED);
		base.add(OrePlaced.ORE_URANIUM_PLACED);
		base.add(OrePlaced.ORE_URANIUM_DEEPSLATE_PLACED);
		base.add(OrePlaced.ORE_THORIUM_PLACED);
		base.add(OrePlaced.ORE_THORIUM_DEEPSLATE_PLACED);
		
		//base.add(OrePlaced.DUMMY_PLACED);
		
		NUCLEAR_EXPANSION_ORES.add(ORE_BERYLLIUM_TARGET);
		NUCLEAR_EXPANSION_ORES.add(ORE_URANIUM_TARGET);
		NUCLEAR_EXPANSION_ORES.add(ORE_THORIUM_TARGET);
		
	}
	
}