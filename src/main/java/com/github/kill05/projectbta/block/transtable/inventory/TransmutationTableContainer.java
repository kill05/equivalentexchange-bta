package com.github.kill05.projectbta.block.transtable.inventory;

import com.github.kill05.projectbta.ProjectPlayer;
import com.github.kill05.projectbta.block.transtable.inventory.slot.BurnSlot;
import com.github.kill05.projectbta.block.transtable.inventory.slot.TransmuteSlot;
import com.github.kill05.projectbta.block.transtable.inventory.slot.UnlearnSlot;
import com.github.kill05.projectbta.inventory.ProjectContainer;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.Collections;
import java.util.List;

public class TransmutationTableContainer extends ProjectContainer {

	public static final int[][] transmuteSlots = new int[][]{
		new int[]{157, 8},
		new int[]{178, 14},
		new int[]{193, 29},
		new int[]{197, 49},

		new int[]{193, 69},
		new int[]{177, 83},
		new int[]{157, 90},
		new int[]{137, 83},

		new int[]{122, 69},
		new int[]{116, 49},
		new int[]{122, 29},
		new int[]{136, 14},

		new int[]{157, 30},
		new int[]{176, 49},
		new int[]{157, 68},
		new int[]{139, 49}
	};

	public static final int[][] chargeSlots = new int[][]{
		new int[]{},
	};

	private final EntityPlayer player;
	private final BurnSlot burnSlot;
	private int page;

	public TransmutationTableContainer(EntityPlayer player) {
		super(0, 0);
		this.player = player;

		for (int i = 0; i < transmuteSlots.length; i++) {
			int[] coords = transmuteSlots[i];
			addSlot(new TransmuteSlot(this, i, coords[0], coords[1]));
		}

		addSlot(new UnlearnSlot(this, 89, 97));
		burnSlot = new BurnSlot(this, 107, 97);
		addSlot(burnSlot);

		addPlayerInventory(player, 27, 117);
	}


	@Override
	public ItemStack clickInventorySlot(InventoryAction action, int[] args, EntityPlayer player) {
		if (args == null || args.length == 0 || !(this.getSlot(args[0]) instanceof TransmuteSlot slot))
			return super.clickInventorySlot(action, args, player);

		if (action == InventoryAction.CLICK_LEFT) {
			ItemStack held = player.inventory.getHeldItemStack();

			if (held == null) {
				player.inventory.setHeldItemStack(slot.transmuteStack(1, true));
				return null;
			}

			if (held.canStackWith(slot.getStack())) {
				ItemStack removeStack = slot.transmuteStack(1, true);
				if(removeStack != null) held.stackSize += removeStack.stackSize;
				return null;
			}
		}

		if(action == InventoryAction.MOVE_STACK || action == InventoryAction.MOVE_SINGLE_ITEM) {
			List<Integer> targetSlots = getTargetSlots(action, slot, args.length > 1 ? args[1] : 0, player);
			if(targetSlots == null || targetSlots.size() == 0) return null;

			ItemStack stackInSlot = slot.getInventory().getStackInSlot(0);
			int maxAmount = stackInSlot != null ? stackInSlot.getMaxStackSize() : 1;
			ItemStack item = slot.transmuteStack(action == InventoryAction.MOVE_STACK ? maxAmount : 1, false);
			if(item == null) return null;

			int transmuteAmount = item.stackSize;
			this.mergeItems(item, targetSlots);
			slot.removeEmc(transmuteAmount - item.stackSize);
			return null;
		}

		return super.clickInventorySlot(action, args, player);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		if(slot instanceof TransmuteSlot) {
			return getSlots(burnSlot.id + 1, 36, true);
		}

		if(slot.id > burnSlot.id) {
			return Collections.singletonList(burnSlot.id);
		}

		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return this.player == entityPlayer;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		int emcItems = ((ProjectPlayer) player).getKnownItems().size();
		this.page = Math.max(Math.min(page, emcItems / transmuteSlots.length), 0);
	}

}
