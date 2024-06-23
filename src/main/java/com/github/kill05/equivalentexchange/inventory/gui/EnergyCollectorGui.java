package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.inventory.container.EnergyCollectorContainer;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;

public class EnergyCollectorGui extends GuiContainer {

	public EnergyCollectorGui(EntityPlayer player, EnergyCollectorTile tile) {
		super(new EnergyCollectorContainer(player, tile));
		this.xSize = 200;
		this.ySize = 165;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.drawGui("gui/collector.png");
		EnergyCollectorTile tile = getContainer().getTile();

		RenderUtils.drawProgressBar(tile.getEmc(), tile.getMaxEmc(), 48, 80, 18, 0, 166, 10, false);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		EnergyCollectorTile tile = getContainer().getTile();

		// Accumulated EMC
		fontRenderer.drawString(tile.getFormattedEmc(), 78, 31, 0x404040);
	}

	public EnergyCollectorContainer getContainer() {
		return (EnergyCollectorContainer) inventorySlots;
	}
}
