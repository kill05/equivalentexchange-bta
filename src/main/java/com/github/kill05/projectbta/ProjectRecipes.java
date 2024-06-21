package com.github.kill05.projectbta;

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

public final class ProjectRecipes {

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
			ProjectBTA.ALCHEMICAL_COAL.getDefaultStack(),
			"coal_to_alchemical", 4
		);

		philoRecipe(
			ProjectBTA.ALCHEMICAL_COAL,
			ProjectBTA.MOBIUS_FUEL.getDefaultStack(),
			"alchemical_to_mobius", 4
		);

		philoRecipe(
			ProjectBTA.MOBIUS_FUEL,
			ProjectBTA.AETERNALIS_FUEL.getDefaultStack(),
			"mobius_to_aeternalis", 4
		);


		RecipeBuilderShaped surroundCenterRecipe = RecipeBuilder.Shaped(ProjectBTA.MOD_ID, "xxx", "xox", "xxx");
		RecipeBuilderShaped surroundCenterAlternateRecipe = RecipeBuilder.Shaped(ProjectBTA.MOD_ID, "xyx", "yoy", "xyx");
		RecipeBuilderShaped surroundLineRecipe = RecipeBuilder.Shaped(ProjectBTA.MOD_ID, "xxx", "ooo", "xxx");

		// Philosopher's stone
		surroundCenterAlternateRecipe.addInput('x', Item.dustRedstone)
			.addInput('y', Item.dustGlowstone)
			.addInput('o', Item.diamond)
			.create("philosopher_stone_0", ProjectBTA.PHILOSOPHER_STONE.getDefaultStack());

		surroundCenterAlternateRecipe.addInput('x', Item.dustGlowstone)
			.addInput('y', Item.dustRedstone)
			.addInput('o', Item.diamond)
			.create("philosopher_stone_1", ProjectBTA.PHILOSOPHER_STONE.getDefaultStack());

		// Transmutation table
		surroundCenterAlternateRecipe.addInput('x', Block.obsidian)
			.addInput('y', Block.stone)
			.addInput('o', ProjectBTA.PHILOSOPHER_STONE)
			.create("transmutation_table_0", ProjectBTA.TRANSMUTATION_TABLE.getDefaultStack());

		surroundCenterAlternateRecipe.addInput('x', Block.stone)
			.addInput('y', Block.obsidian)
			.addInput('o', ProjectBTA.PHILOSOPHER_STONE)
			.create("transmutation_table_1", ProjectBTA.TRANSMUTATION_TABLE.getDefaultStack());

		// Matter
		surroundCenterRecipe.addInput('x', ProjectBTA.AETERNALIS_FUEL)
			.addInput('o', Block.blockDiamond)
			.create("dark_matter", ProjectBTA.DARK_MATTER.getDefaultStack());

		surroundLineRecipe.addInput('x', ProjectBTA.AETERNALIS_FUEL)
			.addInput('o', ProjectBTA.DARK_MATTER)
			.create("red_matter", ProjectBTA.RED_MATTER.getDefaultStack());
	}


	private static void philoRecipe(IItemConvertible input, ItemStack output, String recipeId, int inputAmount) {
		RecipeBuilderShapeless recipe = RecipeBuilder.Shapeless(ProjectBTA.MOD_ID)
			.addInput(ProjectBTA.PHILOSOPHER_STONE);

		for (int i = 0; i < inputAmount; i++) {
			recipe.addInput(input);
		}

		recipe.create("philo_" + inputAmount + recipeId, output);
	}


}
