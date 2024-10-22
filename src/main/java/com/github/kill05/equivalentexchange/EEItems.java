package com.github.kill05.equivalentexchange;

import com.github.kill05.equivalentexchange.config.EEConfig;
import com.github.kill05.equivalentexchange.items.EmcItem;
import com.github.kill05.equivalentexchange.items.PhilosopherStoneItem;
import com.github.kill05.equivalentexchange.items.SuperSecretItem;
import com.github.kill05.equivalentexchange.items.tools.*;
import net.minecraft.client.render.item.model.ItemModelStandard;
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
	public static final Item KLEIN_STAR_ZWEI = simpleItem("stars/zwei", new EmcItem("klein_star_zwei", EEConfig.ITEM_ID++, 200_000));
	public static final Item KLEIN_STAR_DREI = simpleItem("stars/drei", new EmcItem("klein_star_drei", EEConfig.ITEM_ID++, 800_000));
	public static final Item KLEIN_STAR_VIER = simpleItem("stars/vier", new EmcItem("klein_star_vier", EEConfig.ITEM_ID++, 3_200_000));
	public static final Item KLEIN_STAR_SPHERE = simpleItem("stars/sphere", new EmcItem("klein_star_sphere", EEConfig.ITEM_ID++, 12_800_000));
	public static final Item KLEIN_STAR_OMEGA = simpleItem("stars/omega", new EmcItem("klein_star_omega", EEConfig.ITEM_ID++, 51_200_000));

	// Dark matter tools
	public static final EESwordItem DARK_MATTER_SWORD = toolItem("tools/dm/sword",
		new EESwordItem("dark_matter_sword", EEConfig.ITEM_ID++, MatterToolMaterial.DARK_MATTER)
	);
	public static final EEPickaxeItem DARK_MATTER_PICKAXE = toolItem("tools/dm/pickaxe",
		new EEPickaxeItem("dark_matter_pickaxe", EEConfig.ITEM_ID++, MatterToolMaterial.DARK_MATTER)
	);
	public static final EEAxeItem DARK_MATTER_AXE = toolItem("tools/dm/axe",
		new EEAxeItem("dark_matter_axe", EEConfig.ITEM_ID++, MatterToolMaterial.DARK_MATTER)
	);
	public static final EEShovelItem DARK_MATTER_SHOVEL = toolItem("tools/dm/shovel",
		new EEShovelItem("dark_matter_shovel", EEConfig.ITEM_ID++, MatterToolMaterial.DARK_MATTER)
	);

	// Red matter tools
	public static final EESwordItem RED_MATTER_SWORD = toolItem("tools/rm/sword",
		new EESwordItem("red_matter_sword", EEConfig.ITEM_ID++, MatterToolMaterial.RED_MATTER)
	);
	public static final EEPickaxeItem RED_MATTER_PICKAXE = toolItem("tools/rm/pickaxe",
		new EEPickaxeItem("red_matter_pickaxe", EEConfig.ITEM_ID++, MatterToolMaterial.RED_MATTER)
	);
	public static final EEAxeItem RED_MATTER_AXE = toolItem("tools/rm/axe",
		new EEAxeItem("red_matter_axe", EEConfig.ITEM_ID++, MatterToolMaterial.RED_MATTER)
	);
	public static final EEShovelItem RED_MATTER_SHOVEL = toolItem("tools/rm/shovel",
		new EEShovelItem("red_matter_shovel", EEConfig.ITEM_ID++, MatterToolMaterial.RED_MATTER)
	);

	public static final Item SUPER_SECRET_ITEM = EEConfig.isSecretSettingEnabled() ? simpleItem("super_secret_item", new SuperSecretItem(EEConfig.ITEM_ID++)) : null;


	private static Item simpleItem(String name, String texture) {
		return simpleItem(texture, new Item(name, EEConfig.ITEM_ID++));
	}

	private static <T extends Item> T simpleItem(String texture, T item) {
		return new ItemBuilder(EquivalentExchange.MOD_ID)
			.setIcon(EquivalentExchange.MOD_ID + ":item/" + texture)
			.build(item);
	}

	private static <T extends Item> T toolItem(String texture, T item) {
		return new ItemBuilder(EquivalentExchange.MOD_ID)
			.setItemModel(item1 -> new ItemModelStandard(item, EquivalentExchange.MOD_ID).setFull3D())
			.setIcon(EquivalentExchange.MOD_ID + ":item/" + texture)
			.build(item);
	}
}
