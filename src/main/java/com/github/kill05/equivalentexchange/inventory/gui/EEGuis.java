package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.inventory.container.AlchemicalChestContainer;
import com.github.kill05.equivalentexchange.inventory.container.EnergyCondenserContainer;
import com.github.kill05.equivalentexchange.inventory.container.TransmutationTableContainer;
import com.github.kill05.equivalentexchange.tile.AlchemicalChestTile;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.gui.GuiHelper;
import turniplabs.halplibe.helper.gui.factory.base.GuiFactory;
import turniplabs.halplibe.helper.gui.factory.block.TileGuiFactory;
import turniplabs.halplibe.helper.gui.registered.RegisteredGui;

public final class EEGuis {

	public static final RegisteredGui TRANSMUTATION_TABLE = GuiHelper.registerServerGui(EquivalentExchange.MOD_ID, "transmutation_table", new GuiFactory() {
		@Override
		public @NotNull GuiScreen createGui(@NotNull RegisteredGui gui, @NotNull EntityPlayerSP player) {
			return new TransmutationTableGui(player);
		}

		@Override
		public @NotNull Container createContainer(@NotNull RegisteredGui gui, @NotNull EntityPlayerMP player) {
			return new TransmutationTableContainer(player);
		}

		@Override
		public void onButtonClick(@NotNull RegisteredGui gui, @NotNull EntityPlayer player, @NotNull EntityPlayer clicker, int buttonId) {
			if(player != clicker) return;
			if(!(player.craftingInventory instanceof TransmutationTableContainer container)) return;

			container.setPage(container.getPage() + (buttonId == 0 ? -1 : 1));
		}
	});

	public static final RegisteredGui ALCHEMICAL_CHEST = GuiHelper.registerServerGui(EquivalentExchange.MOD_ID, "alchemical_chest", new TileGuiFactory<AlchemicalChestTile>() {
		@Override
		public @NotNull GuiScreen createGui(@NotNull RegisteredGui gui, @NotNull EntityPlayerSP player, @NotNull AlchemicalChestTile tile) {
			return new AlchemicalChestGui(player, tile);
		}

		@Override
		public @NotNull Container createContainer(@NotNull RegisteredGui gui, @NotNull EntityPlayerMP player, @NotNull AlchemicalChestTile tile) {
			return new AlchemicalChestContainer(player, tile);
		}
	});

	public static final RegisteredGui ENERGY_CONDENSER = GuiHelper.registerServerGui(EquivalentExchange.MOD_ID, "energy_condenser", new TileGuiFactory<EnergyCondenserTile>() {
		@Override
		public @NotNull GuiScreen createGui(@NotNull RegisteredGui gui, @NotNull EntityPlayerSP player, @NotNull EnergyCondenserTile tile) {
			return new EnergyCondenserGui(player, tile);
		}

		@Override
		public @NotNull Container createContainer(@NotNull RegisteredGui gui, @NotNull EntityPlayerMP player, @NotNull EnergyCondenserTile tile) {
			return new EnergyCondenserContainer(player, tile);
		}
	});

}
