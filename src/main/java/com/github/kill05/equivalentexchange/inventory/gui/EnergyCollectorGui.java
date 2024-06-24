package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.inventory.container.EnergyCollectorContainer;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public class EnergyCollectorGui extends GuiContainer {

	public EnergyCollectorGui(EntityPlayer player, EnergyCollectorTile tile) {
		super(new EnergyCollectorContainer(player, tile));
		this.xSize = 200;
		this.ySize = 165;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.drawGui("gui/collector.png");
		EnergyCollectorTile tile = getTile();

		// Collector EMC bar
		RenderUtils.drawProgressBar(tile.getEmc(), tile.getMaxEmc(), 48, 80, 18, 0, 166, 10, false);

		// Input EMC Bar
		ItemStack itemStack = tile.getStackInSlot(0);
		if(itemStack != null && itemStack.getItem() instanceof IItemEmcHolder emcHolder) {
			RenderUtils.drawProgressBar(
				emcHolder.getEmc(itemStack),
				emcHolder.getMaxEmc(itemStack),
				48,
				80, 58,
				0, 166,
				10, false
			);
		}

		// Conversion progress bar
		EmcKey key;
		EmcKey conversion;
		if(itemStack != null && (conversion = EnergyCollectorTile.CONVERSION_MAP.get(key = new EmcKey(itemStack))) != null) {
			Long keyValue = key.emcValue();
			Long convValue = conversion.emcValue();

			if(keyValue != null && convValue != null) RenderUtils.drawProgressBar(
				tile.getEmc(), convValue - keyValue,
				-25, 164, 56,
				211, 39, -10, true
			);
		}

		// Sunlight bar
		int lightLevel = tile.getLightLevel();
		RenderUtils.drawProgressBar(
			lightLevel, 15,
			-14, 155, 50,
			215, 14, -14, true
		);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		EnergyCollectorTile tile = getTile();
		// Collector emc
		fontRenderer.drawString(tile.getFormattedEmc(), 78, 32, 0x404040);

		// Item emc
		ItemStack itemStack = tile.getStackInSlot(0);
		if(itemStack != null && itemStack.getItem() instanceof IItemEmcHolder emcHolder)
			fontRenderer.drawString(emcHolder.getFormattedEmc(itemStack), 78, 47, 0x404040);
	}

	public EnergyCollectorTile getTile() {
		return getContainer().getTile();
	}


	public EnergyCollectorContainer getContainer() {
		return (EnergyCollectorContainer) inventorySlots;
	}
}
