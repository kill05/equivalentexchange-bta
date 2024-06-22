package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.inventory.container.EnergyCondenserContainer;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;

public class EnergyCondenserGui extends GuiContainer {

	public EnergyCondenserGui(EntityPlayer player, EnergyCondenserTile tile) {
		super(new EnergyCondenserContainer(player, tile));
		this.xSize = 256;
		this.ySize = 230;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.drawGui(this, "gui/condenser.png");
	}
}
