package com.github.kill05.equivalentexchange.inventory.container;

import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.entity.player.EntityPlayer;

public class EnergyCondenserContainer extends AlchemicalChestContainer {

	private final EnergyCondenserTile tile;

	public EnergyCondenserContainer(EntityPlayer player, EnergyCondenserTile tile) {
		super(player, tile.getInputInventory(), 13, 4, 26);
		this.tile = tile;
	}

	public EnergyCondenserTile getTile() {
		return tile;
	}


}
