package com.github.kill05.equivalentexchange.utils;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InventoryUtils {

	public static ListTag writeInventory(IInventory inv) {
		return writeInventory(inv, new ListTag());
	}

	public static ListTag writeInventory(IInventory inv, ListTag list) {
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack item = inv.getStackInSlot(i);
			if (item == null) continue;

			CompoundTag itemTag = new CompoundTag();
			itemTag.putByte("Slot", (byte) i);
			item.writeToNBT(itemTag);

			list.addTag(itemTag);
		}

		return list;
	}

	@SuppressWarnings("UnusedReturnValue")
	public static IInventory loadInventory(IInventory inv, ListTag list) {
		for (Tag<?> tag : list) {
			CompoundTag itemTag = (CompoundTag) tag;
			int slot = itemTag.getByte("Slot") & 0xFF;
			if (slot >= inv.getSizeInventory()) continue;
			inv.setInventorySlotContents(slot, ItemStack.readItemStackFromNbt(itemTag));
		}

		return inv;
	}


	public static void dropInventoryContents(IInventory inventory, World world, int x, int y, int z) {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack != null) {
				EntityItem item = world.dropItem(x, y, z, itemStack);
				item.xd *= 0.5;
				item.yd *= 0.5;
				item.zd *= 0.5;
				item.delayBeforeCanPickup = 0;
			}
		}
	}

	@Nullable
	public static ItemStack addItem(@NotNull IInventory inventory, @Nullable ItemStack stack) {
		if (stack == null || inventory.getSizeInventory() == 0) return stack;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			stack = addItem(inventory, stack, i);
			if (stack == null) break;
		}

		return stack;
	}

	@Nullable
	public static ItemStack addItem(@NotNull IInventory inventory, @Nullable ItemStack putStack, int slot) {
		if (putStack == null || putStack.stackSize <= 0) return null;
		if (slot < 0 || slot >= inventory.getSizeInventory())
			return putStack;

		ItemStack slotStack = inventory.getStackInSlot(slot);
		int maxStack = putStack.getMaxStackSize(inventory);

		if (slotStack == null) {
			int putAmount = Math.min(putStack.stackSize, maxStack);
			inventory.setInventorySlotContents(slot, putStack.splitStack(putAmount));
		} else if (putStack.canStackWith(slotStack)) {
			int putAmount = Math.max(Math.min(maxStack - slotStack.stackSize, putStack.stackSize), 0);
			slotStack.stackSize += putAmount;
			putStack.stackSize -= putAmount;
		}

		return putStack.stackSize > 0 ? putStack : null;
	}

	public static void removeItem(@NotNull IInventory inventory, int amount, int slot) {
		if (slot < 0 || slot >= inventory.getSizeInventory()) return;

		ItemStack itemStack = inventory.getStackInSlot(slot);
		itemStack.stackSize -= amount;
		if(itemStack.stackSize <= 0) inventory.setInventorySlotContents(slot, null);
	}

}
