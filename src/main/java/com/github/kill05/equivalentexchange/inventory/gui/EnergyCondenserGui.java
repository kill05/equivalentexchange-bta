package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.inventory.container.EnergyCondenserContainer;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserMK2Tile;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;

public class EnergyCondenserGui extends GuiContainer {

	public EnergyCondenserGui(EntityPlayer player, EnergyCondenserTile tile) {
		super(new EnergyCondenserContainer(player, tile));
		this.xSize = 256;
		this.ySize = 230;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		EnergyCondenserTile tile = getTile();
		RenderUtils.drawGui(tile instanceof EnergyCondenserMK2Tile ? "gui/condenser_mk2" : "gui/condenser.png");

		EmcKey output = tile.getOutput();
		if(output != null)
			RenderUtils.drawProgressBar(tile.getEmc(), output.emcValue(), 102, 33, 9, 0, 234, 10, false);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		fontRenderer.drawString(NumberUtils.formatNumber(getTile().getEmc()), 141, 10, 0x404040);
	}

	public EnergyCondenserTile getTile() {
		return ((EnergyCondenserContainer) inventorySlots).getTile();
	}
}
