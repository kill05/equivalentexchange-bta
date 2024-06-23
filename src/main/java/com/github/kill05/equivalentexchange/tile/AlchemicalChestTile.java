package com.github.kill05.equivalentexchange.tile;

import net.minecraft.core.player.inventory.InventoryBasic;

public class AlchemicalChestTile extends InventoryTileEntity {

	public AlchemicalChestTile(int slots) {
		super(new InventoryBasic(null, slots));
	}

	public AlchemicalChestTile() {
		this(13 * 8);
	}


}
