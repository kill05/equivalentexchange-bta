package com.github.kill05.projectbta.items;

import com.github.kill05.projectbta.ProjectBTA;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.HashMap;
import java.util.Map;

public class PhilosopherStoneItem extends Item {

	private static final Map<Block, Block> TRANSMUTATION_MAP = new HashMap<>();

	public PhilosopherStoneItem(int id) {
		super("philosopher_stone", id);
		setMaxStackSize(1);
		setContainerItem(this);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		Block block = world.getBlock(blockX, blockY, blockZ);
		Block newBlock = TRANSMUTATION_MAP.get(block);
		if(block == null) return false;

		boolean transmutableMeta = block == Block.planksOakPainted || block == Block.wool || block == Block.lampIdle || block == Block.lampActive;
		if(newBlock == null && !transmutableMeta) return true;
		if(world.isClientSide) return true;

		world.playSoundAtEntity(null, player, ProjectBTA.MOD_ID + ".transmute", 0.7f, 1.0f);

		if(newBlock != null) {
			world.setBlockWithNotify(blockX, blockY, blockZ, newBlock.id);
		}

		if(transmutableMeta) {
			int meta = world.getBlockMetadata(blockX, blockY, blockZ);
			world.setBlockMetadataWithNotify(blockX, blockY, blockZ, ++meta < 15 ? meta : 0);
		}

		return true;
	}

	static {
		// Grass
		TRANSMUTATION_MAP.put(Block.grass, Block.grassScorched);
		TRANSMUTATION_MAP.put(Block.grassScorched, Block.grass);

		// Soil
		TRANSMUTATION_MAP.put(Block.dirt, Block.dirtScorched);
		TRANSMUTATION_MAP.put(Block.dirtScorched, Block.mudBaked);
		TRANSMUTATION_MAP.put(Block.mudBaked, Block.dirt);

		// Gravity
		TRANSMUTATION_MAP.put(Block.sand, Block.gravel);
		TRANSMUTATION_MAP.put(Block.gravel, Block.mud);
		TRANSMUTATION_MAP.put(Block.mud, Block.sand);

		// Stone
		TRANSMUTATION_MAP.put(Block.stone, Block.basalt);
		TRANSMUTATION_MAP.put(Block.basalt, Block.limestone);
		TRANSMUTATION_MAP.put(Block.limestone, Block.granite);
		TRANSMUTATION_MAP.put(Block.granite, Block.permafrost);
		TRANSMUTATION_MAP.put(Block.permafrost, Block.stone);

		// Cobblestone
		TRANSMUTATION_MAP.put(Block.cobbleStone, Block.cobbleBasalt);
		TRANSMUTATION_MAP.put(Block.cobbleBasalt, Block.cobbleLimestone);
		TRANSMUTATION_MAP.put(Block.cobbleLimestone, Block.cobbleGranite);
		TRANSMUTATION_MAP.put(Block.cobbleGranite, Block.cobblePermafrost);
		TRANSMUTATION_MAP.put(Block.cobblePermafrost, Block.cobbleStone);

		// Logs
		TRANSMUTATION_MAP.put(Block.logOak, Block.logBirch);
		TRANSMUTATION_MAP.put(Block.logBirch, Block.logPine);
		TRANSMUTATION_MAP.put(Block.logPine, Block.logCherry);
		TRANSMUTATION_MAP.put(Block.logCherry, Block.logEucalyptus);
		TRANSMUTATION_MAP.put(Block.logEucalyptus, Block.logOak);

		// Leaves
		TRANSMUTATION_MAP.put(Block.leavesOak, Block.leavesBirch);
		TRANSMUTATION_MAP.put(Block.leavesBirch, Block.leavesPine);
		TRANSMUTATION_MAP.put(Block.leavesPine, Block.leavesCherry);
		TRANSMUTATION_MAP.put(Block.leavesCherry, Block.leavesEucalyptus);
		TRANSMUTATION_MAP.put(Block.leavesEucalyptus, Block.leavesShrub);
		TRANSMUTATION_MAP.put(Block.leavesShrub, Block.leavesCacao);
		TRANSMUTATION_MAP.put(Block.leavesCacao, Block.leavesOak);

		// Sapling
		TRANSMUTATION_MAP.put(Block.saplingOak, Block.saplingBirch);
		TRANSMUTATION_MAP.put(Block.saplingBirch, Block.saplingPine);
		TRANSMUTATION_MAP.put(Block.saplingPine, Block.saplingCherry);
		TRANSMUTATION_MAP.put(Block.saplingCherry, Block.saplingEucalyptus);
		TRANSMUTATION_MAP.put(Block.saplingEucalyptus, Block.saplingShrub);
		TRANSMUTATION_MAP.put(Block.saplingShrub, Block.saplingCacao);
		TRANSMUTATION_MAP.put(Block.saplingCacao, Block.saplingOak);

		// Tall grass
		TRANSMUTATION_MAP.put(Block.tallgrass, Block.tallgrassFern);
		TRANSMUTATION_MAP.put(Block.tallgrassFern, Block.spinifex);
		TRANSMUTATION_MAP.put(Block.spinifex, Block.tallgrass);

		// Flowers
		TRANSMUTATION_MAP.put(Block.flowerRed, Block.flowerYellow);
		TRANSMUTATION_MAP.put(Block.flowerYellow, Block.flowerRed);

		// Mushrooms
		TRANSMUTATION_MAP.put(Block.mushroomBrown, Block.mushroomRed);
		TRANSMUTATION_MAP.put(Block.mushroomRed, Block.mushroomBrown);
	}
}
