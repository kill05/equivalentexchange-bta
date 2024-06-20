package com.github.kill05.projectbta.registry;

import com.github.kill05.projectbta.config.ProjectConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeEntryBase;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeRegistry;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.*;
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
	private final Map<EmcKey, Long> itemEmcMap;
	private final Map<String, Long> itemGroupEmcMap;
	private final List<EmcKey> sortedKeys;
	private boolean initRegistries;

	private EmcRegistry() {
		this.itemEmcMap = new HashMap<>();
		this.itemGroupEmcMap = new HashMap<>();
		this.sortedKeys = new ArrayList<>();
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
			if (loadConfigEntry(key, emcValues.get(key))) loaded++;
		}

		LOGGER.info(String.format("Loaded %s EMC value(s).", loaded));
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

	private void computeRecipeCosts() {
		Map<EmcKey, Long> originalValues = new HashMap<>(itemEmcMap);
		RecipeRegistry registry = Registries.RECIPES;

		LOGGER.info("Computing EMC values...");
		long millis = System.currentTimeMillis();

		int amount = 0;
		int registeredInIteration = 1;
		for (int i = 0; registeredInIteration > 0; i++) {
			registeredInIteration = 0;

			for (RecipeGroup<?> group : new RecipeGroup[]{registry.WORKBENCH, registry.FURNACE, registry.BLAST_FURNACE}) {
				for (RecipeEntryBase<?, ?, ?> recipe : group.getAllRecipes()) {
					if (!computeRecipeCostAndRegister(recipe, originalValues)) continue;
					registeredInIteration++;
				}
			}

			amount += registeredInIteration;
			LOGGER.info(String.format("Computed %s values in iteration %s.", registeredInIteration, i));
		}

		LOGGER.info(String.format("Computed %s EMC values. (took %sms)", amount, System.currentTimeMillis() - millis));
	}

	private boolean computeRecipeCostAndRegister(RecipeEntryBase<?, ?, ?> recipe, Map<EmcKey, Long> originalValues) {
		if (recipe.getInput() == null || recipe.getOutput() == null) return false;

		Iterable<RecipeSymbol> symbolIterable;
		ItemStack output;

		if (recipe instanceof RecipeEntryCraftingShaped shaped) {
			symbolIterable = Arrays.asList(shaped.getInput());
			output = shaped.getOutput();
		} else if (recipe instanceof RecipeEntryCraftingShapeless shapeless) {
			symbolIterable = shapeless.getInput();
			output = shapeless.getOutput();
		} else if (recipe instanceof RecipeEntryFurnace furnace) {
			symbolIterable = Collections.singleton(furnace.getInput());
			output = furnace.getOutput();
		} else if (recipe instanceof RecipeEntryBlastFurnace furnace) {
			symbolIterable = Collections.singleton(furnace.getInput());
			output = furnace.getOutput();
		} else {
			return false;
		}

		// Output value is hard set from configs, can't change based on the recipe
		if (originalValues.containsKey(new EmcKey(output))) {
			return false;
		}

		long totalCost = 0;

		for (RecipeSymbol symbol : symbolIterable) {
			if (symbol == null) continue;
			Long lowestSymbolCost = null;

			for (ItemStack stack : symbol.resolve()) {
				Long emcValue = getEmcValue(stack);
				if (emcValue == null) continue;

				if (lowestSymbolCost == null || emcValue < lowestSymbolCost) {
					lowestSymbolCost = emcValue;
				}
			}

			// No resolved values for symbol have emc value, can't assign value to output
			if (lowestSymbolCost == null) return false;
			totalCost += lowestSymbolCost;
		}

		totalCost /= output.stackSize;

		// Check if cost is valid
		if (totalCost <= 0) return false;

		// Check if the cost is equal or higher
		// (the recipe has already been added/there's no need to add because it costs more)
		Long previousCost = getEmcValue(output);
		if (previousCost != null && previousCost <= totalCost) return false;

		setEmcValue(output, totalCost);
		return true;
	}


	public void initValues() {
		if (initRegistries) throw new IllegalStateException("Registries have already been initialized!");
		for (Map.Entry<String, Long> entry : itemGroupEmcMap.entrySet()) {
			if (entry.getValue() == null) continue;
			initGroupValue(entry.getKey(), entry.getValue());
		}

		computeRecipeCosts();
		sortKeys();
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

	public void sortKeys() {
		sortedKeys.clear();
		itemEmcMap.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.forEachOrdered(entry -> sortedKeys.add(entry.getKey()));
	}


	public Long getEmcValue(@NotNull EmcKey key) {
		return itemEmcMap.get(key);
	}

	public void setEmcValue(@NotNull EmcKey key, long value) {
		itemEmcMap.put(key, value);
		if (initRegistries) sortKeys();
	}

	public void removeEmcValue(@NotNull EmcKey key) {
		itemEmcMap.remove(key);
	}


	public Long getEmcValue(@NotNull ItemStack itemStack) {
		return getEmcValue(new EmcKey(itemStack));
	}

	public void setEmcValue(@NotNull ItemStack itemStack, long value) {
		setEmcValue(new EmcKey(itemStack), value);
	}

	public void removeEmcValue(@NotNull ItemStack itemStack) {
		itemEmcMap.remove(new EmcKey(itemStack));
	}


	public Long getEmcValue(@NotNull IItemConvertible item) {
		return getEmcValue(new EmcKey(item.asItem().id));
	}

	public void setEmcValue(@NotNull IItemConvertible item, long value) {
		setEmcValue(new EmcKey(item.asItem().id), value);
	}

	public void removeEmcValue(@NotNull IItemConvertible item) {
		removeEmcValue(new EmcKey(item.asItem().id));
	}


	public Long getEmcValue(String group) {
		return itemGroupEmcMap.get(group);
	}

	public void setEmcValue(String group, long value) {
		itemGroupEmcMap.put(group, value);
		if (initRegistries) initGroupValue(group, value);
	}


	public List<EmcKey> getSortedKeys() {
		return sortedKeys;
	}

	public Map<EmcKey, Long> getItemEmcMap() {
		return itemEmcMap;
	}

	@ApiStatus.Internal
	public Map<String, Long> getItemGroupEmcMap() {
		return itemGroupEmcMap;
	}

	public boolean hasInitRegistries() {
		return initRegistries;
	}


	@ApiStatus.Internal
	public static Toml addDefaults(Toml defaults) {
		defaults.addCategory("Add, remove or modify EMC values of items.", ProjectConfig.EMC_CONFIG_KEY);

		// BTA
		addEntry(defaults, "minecraft:dirt", 1);
		addEntry(defaults, "minecraft:grasses", 1);
		addEntry(defaults, "minecraft:cobblestones", 1);
		addEntry(defaults, "minecraft:stones", 1);
		addEntry(defaults, "minecraft:moss_stones", 1);
		addEntry(defaults, Block.sand, 1);
		addEntry(defaults, Block.mud, 1);
		addEntry(defaults, Block.marble, 1);
		addEntry(defaults, Block.slate, 1);
		addEntry(defaults, Block.permafrost, 1);
		addEntry(defaults, Block.ice, 1);
		addEntry(defaults, Item.ammoSnowball, 1);
		addEntry(defaults, Item.ammoPebble, 1);
		addEntry(defaults, Block.gravel, 4);
		addEntry(defaults, Item.flint, 4);
		addEntry(defaults, Block.netherrack, 1);
		addEntry(defaults, Block.netherrackIgneous, 1);
		addEntry(defaults, Block.soulsand, 4);
		addEntry(defaults, Item.clay, 16);

		addEntry(defaults, "minecraft:leaves", 4);
		addEntry(defaults, "minecraft:logs", 32);
		addEntry(defaults, Block.tallgrass, 16);
		addEntry(defaults, Block.tallgrassFern, 16);
		addEntry(defaults, Block.deadbush, 16);
		addEntry(defaults, Block.spinifex, 16);
		addEntry(defaults, Block.algae, 16);
		addEntry(defaults, Block.flowerRed, 16);
		addEntry(defaults, Block.flowerOrange, 16);
		addEntry(defaults, Block.flowerYellow, 16);
		addEntry(defaults, Block.flowerLightBlue, 16);
		addEntry(defaults, Block.flowerPink, 16);
		addEntry(defaults, Block.flowerPurple, 16);
		addEntry(defaults, Block.mushroomBrown, 32);
		addEntry(defaults, Block.mushroomRed, 32);
		addEntry(defaults, Block.algae, 16);
		addEntry(defaults, Block.algae, 16);
		addEntry(defaults, Block.saplingOak, 32);
		addEntry(defaults, Block.saplingOakRetro, 32);
		addEntry(defaults, Block.saplingBirch, 32);
		addEntry(defaults, Block.saplingPine, 32);
		addEntry(defaults, Block.saplingCherry, 32);
		addEntry(defaults, Block.saplingEucalyptus, 32);
		addEntry(defaults, Block.saplingShrub, 32);
		addEntry(defaults, Block.saplingThorn, 32);
		addEntry(defaults, Block.saplingCacao, 32);
		addEntry(defaults, Block.saplingPalm, 32);
		addEntry(defaults, Item.seedsWheat, 32);
		addEntry(defaults, Item.wheat, 64);
		addEntry(defaults, Item.seedsPumpkin, 32);
		addEntry(defaults, Block.pumpkin, 64);
		addEntry(defaults, Block.pumpkinCarvedIdle, 64);
		addEntry(defaults, Item.sugarcane, 64);
		addEntry(defaults, Block.cactus, 64);
		addEntry(defaults, Item.dye, 3, 64); // Cocoa beans
		addEntry(defaults, Item.foodApple, 128);
		addEntry(defaults, Item.cherry, 64);

		addEntry(defaults, Item.leather, 64);
		addEntry(defaults, Item.eggChicken, 64);
		addEntry(defaults, Item.featherChicken, 64);
		addEntry(defaults, Item.foodPorkchopRaw, 256);
		addEntry(defaults, Item.foodFishRaw, 128);
		addEntry(defaults, Item.dye, 0, 32);
		addEntry(defaults, Item.cloth, 128);
		addEntry(defaults, Item.bone, 192);
		addEntry(defaults, Item.ammoArrow, 64);
		addEntry(defaults, Item.string, 64);
		addEntry(defaults, Item.sulphur, 128);
		addEntry(defaults, Item.slimeball, 128);

		addEntry(defaults, Item.dustRedstone, 64);
		addEntry(defaults, Item.coal, 128);
		addEntry(defaults, Item.ingotIron, 256);
		addEntry(defaults, Item.dye, 4, 864);
		addEntry(defaults, Item.ingotGold, 2048);
		addEntry(defaults, Item.diamond, 8192);
		addEntry(defaults, Item.nethercoal, 256);
		addEntry(defaults, Item.dustGlowstone, 384);

		addEntry(defaults, Item.bucketWater, 768);
		addEntry(defaults, Item.bucketMilk, 768);
		addEntry(defaults, Item.bucketLava, 800);
		addEntry(defaults, Item.saddle, 2048);

		addEntry(defaults, Item.record13, 512);
		addEntry(defaults, Item.recordCat, 512);
		addEntry(defaults, Item.recordChirp, 512);
		addEntry(defaults, Item.recordFar, 512);
		addEntry(defaults, Item.recordMall, 512);
		addEntry(defaults, Item.recordMellohi, 512);
		addEntry(defaults, Item.recordStal, 512);
		addEntry(defaults, Item.recordStrad, 512);
		addEntry(defaults, Item.recordWard, 512);
		addEntry(defaults, Item.recordWard, 512);
		addEntry(defaults, Item.recordDog, 512);

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
