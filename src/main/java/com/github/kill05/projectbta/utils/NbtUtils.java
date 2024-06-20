package com.github.kill05.projectbta.utils;

import com.github.kill05.projectbta.ProjectBTA;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;

public final class NbtUtils {

	public static CompoundTag getOrCreateCompound(CompoundTag root, String key) {
		CompoundTag tag = root.getCompoundOrDefault(key, null);
		if(tag != null) return tag;

		tag = new CompoundTag();
		root.putCompound(key, tag);
		return tag;
	}

	public static ListTag getOrCreateList(CompoundTag root, String key) {
		ListTag list = root.getListOrDefault(key, null);
		if(list != null) return list;

		list = new ListTag();
		root.putList(key, list);
		return list;
	}


	public static CompoundTag getMainCompound(CompoundTag tag, boolean createIfAbsent) {
		return createIfAbsent ? getOrCreateCompound(tag, ProjectBTA.MOD_ID) : tag.getCompound(ProjectBTA.MOD_ID);
	}

}
