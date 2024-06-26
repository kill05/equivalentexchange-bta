package com.github.kill05.equivalentexchange.items.tools;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class EEShovelItem extends ItemToolShovel implements IMatterTool {

	public EEShovelItem(String name, int id, MatterToolMaterial material) {
		super(name, id, material);
		setMaxDamage(0);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		if(world.isClientSide || getCharge(itemstack) == 0) {
			return super.onItemUse(itemstack, player, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
		}

		world.playSoundAtEntity(null, player, EquivalentExchange.MOD_ID + ".destroy", 0.5f, 1f);

		int charge = getCharge(itemstack);
		for(int x = -charge; x <= charge; x++) {
			for(int z = -charge; z <= charge; z++) {
				breakBlock(world, player, blockX + x, blockY, blockZ + z, BlockTags.MINEABLE_BY_SHOVEL);
			}
		}

		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {
		return super.getStrVsBlock(itemStack, block) * getDamageModifier(itemStack);
	}

	@Override
	public MatterToolMaterial getMatterMaterial() {
		return (MatterToolMaterial) super.getMaterial();
	}
}
