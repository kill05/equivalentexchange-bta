package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.inventory.slot.select.SelectItemSlot;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CondenserFilterSlot extends SelectItemSlot {

	private final EnergyCondenserTile tile;

	public CondenserFilterSlot(EnergyCondenserTile tile, int x, int y) {
		super(x, y);
		this.tile = tile;
	}

	@Override
	public void selectItem(@Nullable ItemStack itemStack) {
		tile.setOutput(itemStack != null ? new EmcKey(itemStack) : null);
	}

	@Override
	public @Nullable ItemStack getSelectedItem() {
		EmcKey output = tile.getOutput();
		return output != null ? output.itemStack() : null;
	}

}
