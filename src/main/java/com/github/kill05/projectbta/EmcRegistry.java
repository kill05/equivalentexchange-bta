package com.github.kill05.projectbta;

import com.github.kill05.projectbta.config.ProjectConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.toml.Toml;

import java.util.*;

public class EmcRegistry {

	private static EmcRegistry INSTANCE;
	public static final Logger LOGGER = LoggerFactory.getLogger(EmcRegistry.class);

	public static EmcRegistry getInstance() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new EmcRegistry());
	}

	// the key is an ItemStack to string because itemstack doesn't have equals or hashcode
	private final Map<String, Long> itemEmcMap;
	private final Map<String, Long> itemGroupEmcMap;
	private final Map<String, ItemStack> itemLookupMap;
	private final List<ItemStack> sortedItems;
	private boolean initRegistries;

	private EmcRegistry() {
		this.itemEmcMap = new HashMap<>();
		this.itemGroupEmcMap = new HashMap<>();
		this.itemLookupMap = new HashMap<>();
		this.sortedItems = new ArrayList<>();
	}


	public void reloadConfig() {
		this.itemEmcMap.clear();
		this.itemGroupEmcMap.clear();

		Toml toml;
		try {
			toml = (Toml) FieldUtils.readField(ProjectConfig.CONFIG, "config", true);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		Toml emcValues = toml.get("." + ProjectConfig.EMC_CONFIG_KEY, Toml.class);

		int loaded = 0;
		for (String key : emcValues.getOrderedKeys()) {
			if(loadConfigEntry(key, emcValues.get(key))) loaded++;
		}

		// Sort values
		sortedItems.clear();
		itemEmcMap.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(entry -> sortedItems.add(itemLookupMap.get(entry.getKey())));

		LOGGER.info(String.format("Loaded and sorted %s EMC value(s).", loaded));
	}

	private boolean loadConfigEntry(String key, Object value) {
		long emcValue;

		try {
			emcValue = Long.parseLong(String.valueOf(value));
		} catch (NumberFormatException e) {
			LOGGER.warn(String.format("Invalid EMC value '%s' for key '%s' (must be a long).", value, key), e);
			return false;
		}

		int id, meta;

		String[] split = key.split(":");
		if (split.length > 2) return false;

		try {
			// try to load as an item
			id = Integer.parseInt(split[0]);
			meta = split.length == 2 ? Integer.parseInt(split[1]) : 0;
		} catch (NumberFormatException e) {
			// load as a group
			setEmcValue(key, emcValue);
			return true;
		}

		if (meta < 0 || id < 0 || id >= Item.itemsList.length) return false;
		Item item = Item.itemsList[id];
		if (item == null) return false;

		ItemStack itemStack = new ItemStack(item, 1, meta);
		setEmcValue(itemStack, emcValue);
		return true;
	}


	public void onRegistriesReady() {
		if (initRegistries) throw new IllegalStateException("Registries have already been initialized!");
		for (Map.Entry<String, Long> entry : itemGroupEmcMap.entrySet()) {
			if (entry.getValue() == null) continue;
			initGroupValue(entry.getKey(), entry.getValue());
		}
		initRegistries = true;
	}

	protected void initGroupValue(String group, long value) {
		List<ItemStack> items = Registries.ITEM_GROUPS.getItem(group);
		if (items == null) {
			LOGGER.warn(String.format("Failed to find group '%s' while loading EMC values! Skipping...", group));
			return;
		}

		for (ItemStack itemStack : items) {
			if (itemStack == null) continue;
			setEmcValue(itemStack, value);
		}
	}


	public Long getEmcValue(@NotNull ItemStack item) {
		return itemEmcMap.get(item.toString());
	}

	public void setEmcValue(@NotNull ItemStack item, long value) {
		String string = item.toString();
		itemEmcMap.put(string, value);
		itemLookupMap.put(string, item);
	}

	public void removeEmcValue(@NotNull ItemStack item) {
		String string = item.toString();
		itemEmcMap.remove(string);
		itemLookupMap.remove(string);
	}


	public Long getEmcValue(@NotNull IItemConvertible item) {
		return getEmcValue(item.getDefaultStack());
	}

	public void setEmcValue(@NotNull IItemConvertible item, long value) {
		setEmcValue(item.getDefaultStack(), value);
	}

	public void removeEmcValue(@NotNull IItemConvertible item) {
		removeEmcValue(item.getDefaultStack());
	}


	public Long getEmcValue(String group) {
		return itemGroupEmcMap.get(group);
	}

	public void setEmcValue(String group, long value) {
		itemGroupEmcMap.put(group, value);
		if (initRegistries) initGroupValue(group, value);
	}


	public boolean hasInitRegistries() {
		return initRegistries;
	}

	public List<ItemStack> getSortedItems() {
		return sortedItems;
	}

	@ApiStatus.Internal
	public Map<String, Long> getItemEmcMap() {
		return itemEmcMap;
	}

	@ApiStatus.Internal
	public Map<String, Long> getItemGroupEmcMap() {
		return itemGroupEmcMap;
	}

	@ApiStatus.Internal
	public static Toml addDefaults(Toml defaults) {
		defaults.addCategory("Add, remove or modify EMC values of items.", ProjectConfig.EMC_CONFIG_KEY);

		// Vanilla
		addEntry(defaults, "minecraft:dirt", 1);
		addEntry(defaults, "minecraft:grasses", 1);
		addEntry(defaults, "minecraft:cobblestones", 1);
		addEntry(defaults, "minecraft:stones", 1);
		addEntry(defaults, Block.sand, 1);

		addEntry(defaults, "minecraft:leaves", 4);
		addEntry(defaults, Block.gravel, 4);
		addEntry(defaults, Item.flint, 4);

		addEntry(defaults, "minecraft:logs", 32);

		addEntry(defaults, Item.dustRedstone, 64);
		addEntry(defaults, Item.coal, 128);
		addEntry(defaults, Item.ingotIron, 256);
		addEntry(defaults, Item.dye, 4, 864);
		addEntry(defaults, Item.ingotGold, 2048);
		addEntry(defaults, Item.diamond, 8192);

		// Modded
		addEntry(defaults, "common_ingots:tin", 128);
		addEntry(defaults, "common_ingots:copper", 256);

		return defaults;
	}

	private static void addEntry(Toml defaults, IItemConvertible item, long value) {
		defaults.addEntry(ProjectConfig.EMC_CONFIG_KEY + "." + item.asItem().id, value);
	}

	private static void addEntry(Toml defaults, IItemConvertible item, int meta, long value) {
		defaults.addEntry(ProjectConfig.EMC_CONFIG_KEY + "." + item.asItem().id + ":" + meta, value);
	}

	private static void addEntry(Toml defaults, String group, long value) {
		defaults.addEntry(ProjectConfig.EMC_CONFIG_KEY + "." + group, value);
	}

}
