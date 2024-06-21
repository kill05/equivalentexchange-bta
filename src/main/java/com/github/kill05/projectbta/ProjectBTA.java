package com.github.kill05.projectbta;

import com.github.kill05.projectbta.blocks.transtable.TransmutationTableBlock;
import com.github.kill05.projectbta.config.ProjectConfig;
import com.github.kill05.projectbta.emc.EmcKey;
import com.github.kill05.projectbta.emc.EmcRegistry;
import com.github.kill05.projectbta.items.EmcItem;
import com.github.kill05.projectbta.items.PhilosopherStoneItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.BlockSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ProjectBTA implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "projectbta";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static final Item ALCHEMICAL_COAL = simpleItem("alchemical_coal", "fuels/alchemical_coal");
	public static final Item MOBIUS_FUEL = simpleItem("mobius_fuel", "fuels/mobius");
	public static final Item AETERNALIS_FUEL = simpleItem("aeternalis_fuel", "fuels/aeternalis");

	public static final Item DARK_MATTER = simpleItem("dark_matter", "matter/dark");
	public static final Item RED_MATTER = simpleItem("red_matter", "matter/red");

	public static final Item PHILOSOPHER_STONE = simpleItem("philosopher_stone", new PhilosopherStoneItem(ProjectConfig.ITEM_ID++));

	public static final Item KLEIN_STAR_EIN = simpleItem("stars/ein", new EmcItem("klein_star_ein", ProjectConfig.ITEM_ID++, 50_000));
	public static final Item KLEIN_STAR_DREI = simpleItem("stars/drei", new EmcItem("klein_star_drei", ProjectConfig.ITEM_ID++, 200_000));
	public static final Item KLEIN_STAR_ZWEI = simpleItem("stars/zwei", new EmcItem("klein_star_zwei", ProjectConfig.ITEM_ID++, 800_000));
	public static final Item KLEIN_STAR_VIER = simpleItem("stars/vier", new EmcItem("klein_star_vier", ProjectConfig.ITEM_ID++, 3_200_000));
	public static final Item KLEIN_STAR_SPHERE = simpleItem("stars/sphere", new EmcItem("klein_star_sphere", ProjectConfig.ITEM_ID++, 12_800_000));
	public static final Item KLEIN_STAR_OMEGA = simpleItem("stars/omega", new EmcItem("klein_star_omega", ProjectConfig.ITEM_ID++, 51_200_000));


	public static final Block TRANSMUTATION_TABLE = new BlockBuilder(MOD_ID)
		.setTopTexture(MOD_ID + ":block/transmutation_table/top")
		.setSideTextures(MOD_ID + ":block/transmutation_table/side")
		.setBottomTexture(MOD_ID + ":block/transmutation_table/bottom")
		.setHardness(2.5F)
		.setResistance(5.0F)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new TransmutationTableBlock(ProjectConfig.BLOCK_ID++));


	private static Item simpleItem(String name, String texture) {
		return simpleItem(texture, new Item(name, ProjectConfig.ITEM_ID++));
	}

	private static <T extends Item> T simpleItem(String texture, T item) {
		return new ItemBuilder(MOD_ID)
			.setIcon(MOD_ID + ":item/" + texture)
			.build(item);
	}


	public static Long getEmcValue(EmcKey key) {
		return EmcRegistry.getInstance().getEmcValue(key);
	}

	public static Long getEmcValue(ItemStack itemStack) {
		return EmcRegistry.getInstance().getEmcValue(itemStack);
	}

	public static Long getEmcValue(IItemConvertible item) {
		return EmcRegistry.getInstance().getEmcValue(item);
	}


    @Override
    public void onInitialize() {
		EmcRegistry.getInstance().reloadConfig();
        LOGGER.info("Project BTA initialized.");
    }

	@Override
	public void beforeGameStart() {
		SoundHelper.addSound(MOD_ID, "transmute.wav");
		//SoundHelper.addSound(MOD_ID, "charge.wav");
		//SoundHelper.addSound(MOD_ID, "uncharge.wav");
	}

	@Override
	public void afterGameStart() {
		EmcRegistry.getInstance().initValues();
	}

	@Override
	public void initNamespaces() {
		Registries.RECIPES.register(MOD_ID, ProjectRecipes.RECIPE_NAMESPACE);
		ProjectRecipes.RECIPE_NAMESPACE.register("workbench", ProjectRecipes.RECIPE_WORKBENCH);
	}

	@Override
	public void onRecipesReady() {
		ProjectRecipes.registerRecipes();
	}

}
