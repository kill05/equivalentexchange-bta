package com.github.kill05.projectbta.blocks.transtable;

import com.github.kill05.projectbta.ProjectGuis;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class TransmutationTableBlock extends Block {

	public TransmutationTableBlock(int id) {
		super("transmutation_table", id, Material.stone);
		setBlockBounds(0, 0, 0, 1, 0.25, 1);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if(player.isSneaking()) return false;
		ProjectGuis.TRANSMUTATION_TABLE.open(player);
		return true;
	}
}
