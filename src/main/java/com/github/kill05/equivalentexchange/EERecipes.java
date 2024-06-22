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
			EquivalentExchange.ALCHEMICAL_COAL.getDefaultStack(),
			"coal_to_alchemical", 4
		);

		philoRecipe(
			EquivalentExchange.ALCHEMICAL_COAL,
			EquivalentExchange.MOBIUS_FUEL.getDefaultStack(),
			"alchemical_to_mobius", 4
		);

		philoRecipe(
			EquivalentExchange.MOBIUS_FUEL,
			EquivalentExchange.AETERNALIS_FUEL.getDefaultStack(),
			"mobius_to_aeternalis", 4
		);


		RecipeBuilderShaped surroundCenterRecipe = RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xxx", "xox", "xxx");
		RecipeBuilderShaped surroundCenterAlternateRecipe = RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xyx", "yoy", "xyx");
		RecipeBuilderShaped surroundLineRecipe = RecipeBuilder.Shaped(EquivalentExchange.MOD_ID, "xxx", "ooo", "xxx");

		// Philosopher's stone
		surroundCenterAlternateRecipe.addInput('x', Item.dustRedstone)
			.addInput('y', Item.dustGlowstone)
			.addInput('o', Item.diamond)
			.create("philosopher_stone_0", EquivalentExchange.PHILOSOPHER_STONE.getDefaultStack());

		surroundCenterAlternateRecipe.addInput('x', Item.dustGlowstone)
			.addInput('y', Item.dustRedstone)
			.addInput('o', Item.diamond)
			.create("philosopher_stone_1", EquivalentExchange.PHILOSOPHER_STONE.getDefaultStack());

		// Transmutation table
		surroundCenterAlternateRecipe.addInput('x', Block.obsidian)
			.addInput('y', Block.stone)
			.addInput('o', EquivalentExchange.PHILOSOPHER_STONE)
			.create("transmutation_table_0", EquivalentExchange.TRANSMUTATION_TABLE.getDefaultStack());

		surroundCenterAlternateRecipe.addInput('x', Block.stone)
			.addInput('y', Block.obsidian)
			.addInput('o', EquivalentExchange.PHILOSOPHER_STONE)
			.create("transmutation_table_1", EquivalentExchange.TRANSMUTATION_TABLE.getDefaultStack());

		// Matter
		surroundCenterRecipe.addInput('x', EquivalentExchange.AETERNALIS_FUEL)
			.addInput('o', Block.blockDiamond)
			.create("dark_matter", EquivalentExchange.DARK_MATTER.getDefaultStack());

		surroundLineRecipe.addInput('x', EquivalentExchange.AETERNALIS_FUEL)
			.addInput('o', EquivalentExchange.DARK_MATTER)
			.create("red_matter", EquivalentExchange.RED_MATTER.getDefaultStack());

		// Klein star
		surroundCenterRecipe.addInput('x', EquivalentExchange.MOBIUS_FUEL)
			.addInput('o', Item.diamond)
			.create("klein_star_ein", EquivalentExchange.KLEIN_STAR_EIN.getDefaultStack());

		compactRecipe(EquivalentExchange.KLEIN_STAR_EIN, EquivalentExchange.KLEIN_STAR_ZWEI.getDefaultStack(), "klein_star_zwei", 4);
		compactRecipe(EquivalentExchange.KLEIN_STAR_ZWEI, EquivalentExchange.KLEIN_STAR_DREI.getDefaultStack(), "klein_star_drei", 4);
		compactRecipe(EquivalentExchange.KLEIN_STAR_DREI, EquivalentExchange.KLEIN_STAR_VIER.getDefaultStack(), "klein_star_vier", 4);
		compactRecipe(EquivalentExchange.KLEIN_STAR_VIER, EquivalentExchange.KLEIN_STAR_SPHERE.getDefaultStack(), "klein_star_sphere", 4);
		compactRecipe(EquivalentExchange.KLEIN_STAR_SPHERE, EquivalentExchange.KLEIN_STAR_OMEGA.getDefaultStack(), "klein_star_omega", 4);
	}


	private static void philoRecipe(IItemConvertible input, ItemStack output, String recipeId, int inputAmount) {
		RecipeBuilderShapeless recipe = RecipeBuilder.Shapeless(EquivalentExchange.MOD_ID)
			.addInput(EquivalentExchange.PHILOSOPHER_STONE);

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


}
