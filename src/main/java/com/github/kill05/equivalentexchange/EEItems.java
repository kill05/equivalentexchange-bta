package com.github.kill05.equivalentexchange;

import com.github.kill05.equivalentexchange.config.EEConfig;
import com.github.kill05.equivalentexchange.items.EmcItem;
import com.github.kill05.equivalentexchange.items.PhilosopherStoneItem;
import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.ItemBuilder;

public final class EEItems {

	public static final Item LOW_COVALENCE_DUST = simpleItem("covalence_dust_low", "covalence_dust/low");
	public static final Item MEDIUM_COVALENCE_DUST = simpleItem("covalence_dust_medium", "covalence_dust/medium");
	public static final Item HIGH_COVALENCE_DUST = simpleItem("covalence_dust_high", "covalence_dust/high");

	public static final Item ALCHEMICAL_COAL = simpleItem("alchemical_coal", "fuels/alchemical_coal");
	public static final Item MOBIUS_FUEL = simpleItem("mobius_fuel", "fuels/mobius");
	public static final Item AETERNALIS_FUEL = simpleItem("aeternalis_fuel", "fuels/aeternalis");

	public static final Item DARK_MATTER = simpleItem("dark_matter", "matter/dark");
	public static final Item RED_MATTER = simpleItem("red_matter", "matter/red");

	public static final Item PHILOSOPHER_STONE = simpleItem("philosopher_stone", new PhilosopherStoneItem(EEConfig.ITEM_ID++));
	public static final Item KLEIN_STAR_EIN = simpleItem("stars/ein", new EmcItem("klein_star_ein", EEConfig.ITEM_ID++, 50_000));
	public static final Item KLEIN_STAR_DREI = simpleItem("stars/drei", new EmcItem("klein_star_drei", EEConfig.ITEM_ID++, 200_000));
	public static final Item KLEIN_STAR_ZWEI = simpleItem("stars/zwei", new EmcItem("klein_star_zwei", EEConfig.ITEM_ID++, 800_000));
	public static final Item KLEIN_STAR_VIER = simpleItem("stars/vier", new EmcItem("klein_star_vier", EEConfig.ITEM_ID++, 3_200_000));
	public static final Item KLEIN_STAR_SPHERE = simpleItem("stars/sphere", new EmcItem("klein_star_sphere", EEConfig.ITEM_ID++, 12_800_000));
	public static final Item KLEIN_STAR_OMEGA = simpleItem("stars/omega", new EmcItem("klein_star_omega", EEConfig.ITEM_ID++, 51_200_000));

	private static Item simpleItem(String name, String texture) {
		return simpleItem(texture, new Item(name, EEConfig.ITEM_ID++));
	}

	private static <T extends Item> T simpleItem(String texture, T item) {
		return new ItemBuilder(EquivalentExchange.MOD_ID)
			.setIcon(EquivalentExchange.MOD_ID + ":item/" + texture)
			.build(item);
	}
}
