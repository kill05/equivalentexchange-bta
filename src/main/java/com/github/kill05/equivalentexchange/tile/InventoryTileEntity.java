package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryBasic;

public abstract class InventoryTileEntity extends TileEntity implements IInventory {

	private final IInventory inventory;

	public InventoryTileEntity(IInventory inventory) {
		this.inventory = inventory;
	}

	public InventoryTileEntity(String name, int slots) {
		this.inventory = new InventoryBasic(name, slots);
	}


	@Override
	public void readFromNBT(CompoundTag compound) {
		super.readFromNBT(compound);

		ListTag list = compound.getList("Items");
		InventoryUtils.loadInventory(inventory, list);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		tag.put("Items", InventoryUtils.writeInventory(inventory));
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return inventory.decrStackSize(i, j);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		inventory.setInventorySlotContents(i, itemStack);
	}

	@Override
	public String getInvName() {
		return inventory.getInvName();
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public void onInventoryChanged() {
		inventory.onInventoryChanged();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return inventory.canInteractWith(entityPlayer);
	}

	@Override
	public void sortInventory() {
		inventory.sortInventory();
	}


}
