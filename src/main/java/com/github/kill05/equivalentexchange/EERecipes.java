package com.github.kill05.equivalentexchange;

import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShapeless;

public final class EERecipes {

	public static final RecipeNamespace RECIPE_NAMESPACE = new RecipeNamespace();
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> RECIPE_WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));

	public static void registerRecipes() {
		// Blocks
		blockRecipe3(EEItems.ALCHEMICAL_COAL, EEBlocks.ALCHEMICAL_COAL_BLOCK.getDefaultStack(), "alchemical_coal_block");
		blockRecipe3(EEItems.MOBIUS_FUEL, EEBlocks.MOBIUS_FUEL_BLOCK.getDefaultStack(), "mobius_fuel_block");
		blockRecipe3(EEItems.AETERNALIS_FUEL, EEBlocks.AETERNALIS_FUEL_BLOCK.getDefaultStack(), "aeternalis_fuel_block");

		blockRecipe2(EEItems.DARK_MATTER, EEBlocks.DARK_MATTER_BLOCK.getDefaultStack(), "dark_matter_block");
		blockRecipe2(EEItems.RED_MATTER, EEBlocks.RED_MATTER_BLOCK.getDefaultStack(), "red_matter_block");

		// Covalence dust
		RecipeBuilderShapeless lowDust = RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID);
		for(int i = 0; i < 8; i++) {
			lowDust.addInput("minecraft:cobblestones");
		}

		lowDust.addInput(Item.coal, 1)
			.create("covalence_dust_low", new ItemStack(EEItems.LOW_COVALENCE_DUST, 40));

		RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID)
			.addInput(Item.dustRedstone)
			.addInput(Item.ingotIron)
			.create("covalence_dust_medium", new ItemStack(EEItems.MEDIUM_COVALENCE_DUST, 40));

		RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID)
			.addInput(Item.coal)
			.addInput(Item.diamond)
			.create("covalence_dust_high", new ItemStack(EEItems.HIGH_COVALENCE_DUST, 40));

		// Philosopher's stone recipes
		philoRecipe(
			Item.ingotIron,
			Item.ingotGold.getDefaultStack(),
			"iron_to_gold", 8
		);

		philoRecipe(
			Item.ingotGold,
			Item.diamond.getDefaultStack(),
			"gold_to_diamond", 4
		);

		philoRecipe(
			Item.coal,
			EEItems.ALCHEMICAL_COAL.getDefaultStack(),
			"coal_to_alchemical", 4
		);

		philoRecipe(
			EEItems.ALCHEMICAL_COAL,
			EEItems.MOBIUS_FUEL.getDefaultStack(),
			"alchemical_to_mobius", 4
		);

		philoRecipe(
			EEItems.MOBIUS_FUEL,
			EEItems.AETERNALIS_FUEL.getDefaultStack(),
			"mobius_to_aeternalis", 4
		);

		RecipeBuilderShaped surroundLineRecipe = RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xxx", "ooo", "xxx");

		// Philosopher's stone
		surroundAlternateRecipe(
			Item.diamond, Item.dustGlowstone, Item.dustRedstone,
			EEItems.PHILOSOPHER_STONE.getDefaultStack(),
			"philosopher_stone"
		);

		// Transmutation table
		surroundAlternateRecipe(
			Item.diamond, Item.dustGlowstone, Item.dustRedstone,
			EEItems.PHILOSOPHER_STONE.getDefaultStack(),
			"philosopher_stone"
		);

		surroundAlternateRecipe(
			EEItems.PHILOSOPHER_STONE, Block.stone, Block.obsidian,
			EEBlocks.TRANSMUTATION_TABLE.getDefaultStack(),
			"transmutation_table"
		);

		// Matter
		surroundRecipe(Block.blockDiamond, EEItems.AETERNALIS_FUEL, EEItems.DARK_MATTER.getDefaultStack(), "dark_matter");

		surroundLineRecipe.addInput('x', EEItems.AETERNALIS_FUEL)
			.addInput('o', EEItems.DARK_MATTER)
			.create("red_matter", EEItems.RED_MATTER.getDefaultStack());

		// Klein star
		surroundRecipe(Item.diamond, EEItems.MOBIUS_FUEL, EEItems.KLEIN_STAR_EIN.getDefaultStack(), "dark_matter");
		compactRecipe(EEItems.KLEIN_STAR_EIN, EEItems.KLEIN_STAR_ZWEI.getDefaultStack(), "klein_star_zwei", 4);
		compactRecipe(EEItems.KLEIN_STAR_ZWEI, EEItems.KLEIN_STAR_DREI.getDefaultStack(), "klein_star_drei", 4);
		compactRecipe(EEItems.KLEIN_STAR_DREI, EEItems.KLEIN_STAR_VIER.getDefaultStack(), "klein_star_vier", 4);
		compactRecipe(EEItems.KLEIN_STAR_VIER, EEItems.KLEIN_STAR_SPHERE.getDefaultStack(), "klein_star_sphere", 4);
		compactRecipe(EEItems.KLEIN_STAR_SPHERE, EEItems.KLEIN_STAR_OMEGA.getDefaultStack(), "klein_star_omega", 4);

		// Containers
		RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "123", "sds", "ici")
			.addInput('1', EEItems.LOW_COVALENCE_DUST)
			.addInput('2', EEItems.MEDIUM_COVALENCE_DUST)
			.addInput('3', EEItems.HIGH_COVALENCE_DUST)
			.addInput('d', Item.diamond)
			.addInput('i', Item.ingotIron)
			.addInput('s', "minecraft:stones")
			.addInput('c', "minecraft:chests")
			.create("alchemical_chest", EEBlocks.ALCHEMICAL_CHEST.getDefaultStack());

		surroundAlternateRecipe(
			EEBlocks.ALCHEMICAL_CHEST, Block.obsidian, Item.diamond,
			EEBlocks.ENERGY_CONDENSER.getDefaultStack(),
			"energy_condenser"
		);

		// Collectors
		RecipeBuilderShaped collectorUpgradeRecipe = RecipeBuilder.Shaped("xmx", "xcx", "xxx")
			.addInput('x', Block.glowstone);

		RecipeBuilder.Shaped("xgx", "xdx", "xfx")
			.addInput('x', Block.glowstone)
			.addInput('g', Block.glass)
			.addInput('d', Block.blockDiamond)
			.addInput('f', Block.furnaceStoneIdle)
			.create("collector_mk1", EEBlocks.COLLECTOR_MK1.getDefaultStack());

		collectorUpgradeRecipe.addInput('m', EEItems.DARK_MATTER)
			.addInput('c', EEBlocks.COLLECTOR_MK1)
			.create("collector_mk2", EEBlocks.COLLECTOR_MK2.getDefaultStack());

		collectorUpgradeRecipe.addInput('m', EEItems.RED_MATTER)
			.addInput('c', EEBlocks.COLLECTOR_MK2)
			.create("collector_mk3", EEBlocks.COLLECTOR_MK3.getDefaultStack());
	}


	private static void blockRecipe2(IItemConvertible input, ItemStack output, String recipeId) {
		RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xx", "xx")
			.addInput('x', input)
			.create(recipeId, output);
	}

	private static void blockRecipe3(IItemConvertible input, ItemStack output, String recipeId) {
		RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xxx", "xxx", "xxx")
			.addInput('x', input)
			.create(recipeId, output);
	}

	private static void philoRecipe(IItemConvertible input, ItemStack output, String recipeId, int inputAmount) {
		RecipeBuilderShapeless recipe = RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID)
			.addInput(EEItems.PHILOSOPHER_STONE);

		for (int i = 0; i < inputAmount; i++) {
			recipe.addInput(input);
		}

		recipe.create("philo_" + inputAmount + recipeId, output);
	}

	private static void compactRecipe(IItemConvertible input, ItemStack output, String recipeId, int inputAmount) {
		RecipeBuilderShapeless recipe = RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID);

		for (int i = 0; i < inputAmount; i++) {
			recipe.addInput(input);
		}

		recipe.create(recipeId, output);
	}

	private static void surroundRecipe(IItemConvertible center, IItemConvertible item1, ItemStack output, String recipeId) {
		RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xxx", "xox", "xxx")
			.addInput('o', center)
			.addInput('x', item1)
			.create(recipeId + "_0", output);
	}

	private static void surroundAlternateRecipe(IItemConvertible center, IItemConvertible item1, IItemConvertible item2, ItemStack output, String recipeId) {
		RecipeBuilderShaped recipe = RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xyx", "yoy", "xyx")
			.addInput('o', center);

		recipe.addInput('x', item1)
			.addInput('y', item2)
			.create(recipeId + "_0", output);

		recipe.addInput('x', item2)
			.addInput('y', item1)
			.create(recipeId + "_1", output);
	}


}
