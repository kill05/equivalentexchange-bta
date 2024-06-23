package com.github.kill05.equivalentexchange.emc;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record EmcKey(int itemId, int meta) {

	public EmcKey {
		if (meta < 0) throw new IllegalArgumentException("invalid meta: " + meta);
		if (itemId < 0 || itemId >= Item.itemsList.length || Item.itemsList[itemId] == null)
			throw new IllegalArgumentException("Invalid id: " + itemId);
	}

	public EmcKey(int itemId) {
		this(itemId, 0);
	}

	public EmcKey(@NotNull ItemStack item) {
		this(item.itemID, item.getMetadata());
	}

	public EmcKey(@NotNull IItemConvertible item, int meta) {
		this(item.asItem().id, meta);
	}

	public EmcKey(@NotNull IItemConvertible item) {
		this(item.asItem().id, 0);
	}


	public static @Nullable EmcKey deserialize(@Nullable CompoundTag tag) {
		if(tag == null) return null;
		int itemId = tag.getIntegerOrDefault("item_id", -1);
		int meta = tag.getInteger("meta");

		if (itemId <= 0 || itemId >= Item.itemsList.length || Item.itemsList[itemId] == null) return null;
		if (meta < 0) return null;

		return new EmcKey(itemId, meta);
	}


	public boolean matches(@Nullable ItemStack itemStack) {
		if(itemStack == null) return false;
		return itemStack.itemID == itemId && itemStack.getMetadata() == meta;
	}


	public ItemStack itemStack(int stackSize) {
		if (stackSize <= 0) return null;
		return new ItemStack(itemId, stackSize, meta);
	}

	public ItemStack itemStack() {
		return itemStack(1);
	}

	public Item item() {
		return Item.itemsList[itemId];
	}

	public Long emcValue() {
		return EmcRegistry.getInstance().getEmcValue(this);
	}

	public boolean isItemDamageable() {
		return item().getMaxDamage() > 0;
	}

	public CompoundTag serialize() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("item_id", itemId);
		tag.putInt("meta", meta);
		return tag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmcKey entry = (EmcKey) o;

		return itemId == entry.itemId && (meta == entry.meta || isItemDamageable());
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}
}
