package com.github.kill05.projectbta.block.transtable;

import com.github.kill05.projectbta.ProjectBTA;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableContainer;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableGui;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.jetbrains.annotations.NotNull;
import turniplabs.halplibe.helper.gui.GuiHelper;
import turniplabs.halplibe.helper.gui.factory.base.GuiFactory;
import turniplabs.halplibe.helper.gui.registered.RegisteredGui;

public class TransmutationTableBlock extends Block {

	public static final RegisteredGui GUI = GuiHelper.registerServerGui(ProjectBTA.MOD_ID, "transmutation_table", new GuiFactory() {
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

	public TransmutationTableBlock(int id) {
		super("transmutation_table", id, Material.stone);
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if(player.isSneaking()) return false;
		GUI.open(player);
		return true;
	}
}
