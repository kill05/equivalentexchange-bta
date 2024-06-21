package com.github.kill05.projectbta;

import com.github.kill05.projectbta.blocks.transtable.TransmutationTableBlock;
import com.github.kill05.projectbta.config.ProjectConfig;
import com.github.kill05.projectbta.emc.EmcRegistry;
import com.github.kill05.projectbta.items.PhilosopherStoneItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.BlockSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ProjectBTA implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "projectbta";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static final Item PHILOSOPHER_STONE = new ItemBuilder(MOD_ID)
		.setIcon(MOD_ID + ":item/philosopher_stone")
		.build(new PhilosopherStoneItem(ProjectConfig.ITEM_ID++));

	public static final Block TRANSMUTATION_TABLE = new BlockBuilder(MOD_ID)
		.setTopTexture(MOD_ID + ":block/transmutation_table/top")
		.setSideTextures(MOD_ID + ":block/transmutation_table/side")
		.setBottomTexture(MOD_ID + ":block/transmutation_table/bottom")
		.setHardness(2.5F)
		.setResistance(5.0F)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new TransmutationTableBlock(ProjectConfig.BLOCK_ID++));


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

	}

	@Override
	public void onRecipesReady() {
		RecipeBuilderShaped philosopherRecipe = RecipeBuilder.Shaped(MOD_ID, "rgr", "gdg", "rgr");

		philosopherRecipe.addInput('r', Item.dustRedstone)
			.addInput('g', Item.dustGlowstone)
			.addInput('d', Item.diamond)
			.create("philosopher_stone_0", PHILOSOPHER_STONE.getDefaultStack());

		philosopherRecipe.addInput('r', Item.dustGlowstone)
			.addInput('g', Item.dustRedstone)
			.addInput('d', Item.diamond)
			.create("philosopher_stone_1", PHILOSOPHER_STONE.getDefaultStack());
	}


}
