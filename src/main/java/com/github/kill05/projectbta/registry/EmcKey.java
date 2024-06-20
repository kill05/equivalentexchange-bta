package com.github.kill05.projectbta.registry;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record EmcKey(int itemId, int meta) {

	public EmcKey(int itemId) {
		this(itemId, 0);
	}

	public EmcKey(ItemStack item) {
		this(item.itemID, item.getMetadata());
	}

	@Nullable
	public static EmcKey deserialize(@NotNull CompoundTag tag) {
		int itemId = tag.getIntegerOrDefault("item_id", -1);
		int meta = tag.getInteger("meta");

		if(itemId <= 0 || itemId >= Item.itemsList.length || Item.itemsList[itemId] == null) return null;
		if(meta < 0) return null;

		return new EmcKey(itemId, meta);
	}


	public ItemStack item(int stackSize) {
		if(stackSize <= 0) return null;
		return new ItemStack(itemId, stackSize, meta);
	}

	public ItemStack item() {
		return item(1);
	}

	public Long emcValue() {
		return EmcRegistry.getInstance().getEmcValue(this);
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
		return itemId == entry.itemId && meta == entry.meta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, meta);
	}
}
