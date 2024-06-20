package com.github.kill05.projectbta;

import com.github.kill05.projectbta.block.transtable.TransmutationTableBlock;
import com.github.kill05.projectbta.config.ProjectConfig;
import com.github.kill05.projectbta.registry.EmcRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.BlockSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ProjectBTA implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "projectbta";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static final Block TRANSMUTATION_TABLE = new BlockBuilder(MOD_ID)
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

	}


}
