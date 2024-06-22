package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.inventory.container.AlchemicalChestContainer;
import com.github.kill05.equivalentexchange.tile.AlchemicalChestTile;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;

public class AlchemicalChestGui extends GuiContainer {

	public AlchemicalChestGui(EntityPlayer player, AlchemicalChestTile tile) {
		super(new AlchemicalChestContainer(player, tile));
		this.xSize = 256;
		this.ySize = 230;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.drawGui(this, "gui/alchemical_chest.png");
	}
}
