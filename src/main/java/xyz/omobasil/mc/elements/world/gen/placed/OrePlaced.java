package xyz.omobasil.mc.elements.world.gen.placed;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import xyz.omobasil.mc.elements.world.gen.OreGen;

public class OrePlaced {

	private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
	      return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
	      return orePlacement(CountPlacement.of(p_195344_), p_195345_);
	}

	/*private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
	      return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
	}*/
	
	public static final Holder<PlacedFeature> ORE_BERYLLIUM_PLACED = PlacementUtils.register(
			"ore_beryllium_placed", OreGen.ORE_BERYLLIUM, commonOrePlacement(10, //veins per chunk
			HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top())));
	
	public static final Holder<PlacedFeature> ORE_BERYLLIUM_DEEPSLATE_PLACED = PlacementUtils.register(
			"ore_beryllium_deepslate_placed", OreGen.ORE_BERYLLIUM, commonOrePlacement(4,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(0))));
	
	public static final Holder<PlacedFeature> ORE_URANIUM_PLACED = PlacementUtils.register(
			"ore_uranium_placed", OreGen.ORE_URANIUM, commonOrePlacement(2,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(20))));
	
	public static final Holder<PlacedFeature> ORE_URANIUM_DEEPSLATE_PLACED = PlacementUtils.register(
			"ore_uranium_deepslate_placed", OreGen.ORE_URANIUM, commonOrePlacement(5,
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));
	
	public static final Holder<PlacedFeature> ORE_THORIUM_PLACED = PlacementUtils.register(
			"ore_thorium_placed", OreGen.ORE_THORIUM, commonOrePlacement(2,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(20))));
	
	public static final Holder<PlacedFeature> ORE_THORIUM_DEEPSLATE_PLACED = PlacementUtils.register(
			"ore_thorium_deepslate_placed", OreGen.ORE_THORIUM, commonOrePlacement(3,
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));
	
	public static final Holder<PlacedFeature> DUMMY_PLACED = PlacementUtils.register(
			"dummy_placed", OreGen.DUMMY, commonOrePlacement(0, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
			
}


