package com.github.kill05.equivalentexchange.inventory.container;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.inventory.slot.TransmuteSlot;
import com.github.kill05.equivalentexchange.inventory.slot.UnlearnSlot;
import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
import com.github.kill05.equivalentexchange.inventory.slot.BurnSlot;
import com.github.kill05.equivalentexchange.inventory.slot.ChargeSlot;
import com.github.kill05.equivalentexchange.inventory.slot.DischargeSlot;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryBasic;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.Collections;
import java.util.List;

public class TransmutationTableContainer extends EEContainer {

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
		new int[]{42, 22},
		new int[]{69, 49},
		new int[]{42, 76},
		new int[]{15, 49},

		new int[]{33, 40},
		new int[]{51, 40},
		new int[]{51, 58},
		new int[]{33, 58},
	};

	private final EntityPlayer player;
	private final InventoryBasic inventory = new InventoryBasic(null, chargeSlots.length + 2);
	private final BurnSlot burnSlot;
	private int page;

	public TransmutationTableContainer(EntityPlayer player) {
		super(0, 0);
		this.player = player;

		// Slots with inventory
		for (int i = 0; i < transmuteSlots.length; i++) {
			int[] coords = transmuteSlots[i];
			addSlot(new TransmuteSlot(this, i, coords[0], coords[1]));
		}

		for (int i = 0; i < chargeSlots.length; i++) {
			int[] coords = chargeSlots[i];
			addSlot(new ChargeSlot(inventory, (IEmcHolder) player, i, coords[0], coords[1]));
		}

		addSlot(new DischargeSlot(inventory, (IEmcHolder) player, chargeSlots.length, 157, 49));
		addSlot(new UnlearnSlot(this, inventory.getSizeInventory() - 1, 89, 97));

		// Slots without inventory
		burnSlot = new BurnSlot((IEmcHolder) player, 107, 97);
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
				if (removeStack != null) held.stackSize += removeStack.stackSize;
				return null;
			}
		}

		int target = args.length > 1 ? args[1] : 0;
		if (action == InventoryAction.MOVE_STACK || action == InventoryAction.MOVE_SINGLE_ITEM) {
			List<Integer> targetSlots = getTargetSlots(action, slot, target, player);
			if (targetSlots == null || targetSlots.size() == 0) return null;

			ItemStack stackInSlot = slot.getInventory().getStackInSlot(0);
			int maxAmount = stackInSlot != null ? stackInSlot.getMaxStackSize() : 1;
			ItemStack item = slot.transmuteStack(action == InventoryAction.MOVE_STACK ? maxAmount : 1, false);
			if (item == null) return null;

			int transmuteAmount = item.stackSize;
			this.mergeItems(item, targetSlots);
			slot.removeEmc(transmuteAmount - item.stackSize);
			return null;
		}

		boolean moveSimilar = action == InventoryAction.MOVE_SIMILAR;
		if (moveSimilar || action == InventoryAction.MOVE_ALL) {
			ItemStack compareStack = slot.getStack();
			Long compareValue = null;
			if (moveSimilar) {
				if (compareStack == null || ((compareValue = EquivalentExchange.getEmcValue(compareStack)) == null)) return null;
			}

			for (int moveSlot : getMoveSlots(action, slot, target, player)) {
				Slot moveFromSlot = getSlot(moveSlot);
				ItemStack stack = moveFromSlot.getStack();
				if (stack == null) continue;
				if (moveSimilar && !compareStack.canStackWith(stack)) continue;

				Long emcValue = compareValue != null ? compareValue : EquivalentExchange.getEmcValue(stack);
				if(emcValue == null) continue;

				burnSlot.putStack(stack);
				moveFromSlot.putStack(null);
			}

		}

		return super.clickInventorySlot(action, args, player);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return getSlots(burnSlot.id + 1, 36, true);
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		if (slot.id > burnSlot.id) {
			return Collections.singletonList(burnSlot.id);
		}

		return getSlots(burnSlot.id + 1, 36, true);
	}


	public EntityPlayer getPlayer() {
		return player;
	}

	public InventoryBasic getInventory() {
		return inventory;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		int emcItems = ((IPlayerEmcHolder) player).getKnownItems().size();
		this.page = Math.max(Math.min(page, emcItems / transmuteSlots.length), 0);
	}

}
