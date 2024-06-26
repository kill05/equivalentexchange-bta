package com.github.kill05.equivalentexchange;

import com.github.kill05.equivalentexchange.config.EEConfig;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcRegistry;
import com.github.kill05.equivalentexchange.packet.PacketPlayerEmcInfo;
import com.github.kill05.equivalentexchange.tile.AlchemicalChestTile;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.NetworkHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public final class EquivalentExchange implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientStartEntrypoint {

    public static final String MOD_ID = "equivalentexchange";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final KeyBinding CHARGE_KEY = new KeyBinding(MOD_ID + ".key.charge").bindKeyboard(Keyboard.KEY_V);
	public static final KeyBinding CHANGE_MODE_KEY = new KeyBinding(MOD_ID + ".key.change_mode").bindKeyboard(Keyboard.KEY_B);
	public static final KeyBinding ABILITY_KEY = new KeyBinding(MOD_ID + ".key.ability").bindKeyboard(Keyboard.KEY_X);


	public static Long getEmcValue(EmcKey key) {
		return EmcRegistry.getInstance().getEmcValue(key);
	}

	public static Long getEmcValue(ItemStack itemStack) {
		return EmcRegistry.getInstance().getEmcValue(itemStack);
	}

	public static Long getEmcValue(IItemConvertible item) {
		return EmcRegistry.getInstance().getEmcValue(item);
	}


    @Override
    public void onInitialize() {
		// Load classes
		try {
			Class.forName(EEItems.class.getName());
			Class.forName(EEBlocks.class.getName());
			Class.forName(EEGuis.class.getName());
		} catch (ClassNotFoundException e) {
			throw new AssertionError(e);
		}

		EntityHelper.createTileEntity(AlchemicalChestTile.class, "alchemical_chest");
		EntityHelper.createTileEntity(EnergyCondenserTile.class, "energy_condenser");
		EntityHelper.createTileEntity(EnergyCollectorTile.class, "energy_collector");

		NetworkHelper.register(PacketPlayerEmcInfo.class, false, true);

		EmcRegistry.getInstance().reloadConfig();
        LOGGER.info("Equivalent Exchange (BTA!) initialized.");
    }

	@Override
	public void beforeGameStart() {
		SoundHelper.addSound(MOD_ID, "transmute.wav");
		SoundHelper.addSound(MOD_ID, "charge.wav");
		SoundHelper.addSound(MOD_ID, "uncharge.wav");
		SoundHelper.addSound(MOD_ID, "destroy.wav");

		if(EEConfig.isSecretSettingEnabled())
			SoundHelper.addSound(MOD_ID, "super_secret_sound.wav");
	}

	@Override
	public void afterGameStart() {
		EmcRegistry.getInstance().initValues();
	}

	@Override
	public void beforeClientStart() {

	}

	@Override
	public void afterClientStart() {
		OptionsPages.CONTROLS.withComponent(
			new OptionsCategory("gui.options.page." + MOD_ID + ".title")
				.withComponent(new KeyBindingComponent(CHANGE_MODE_KEY))
				.withComponent(new KeyBindingComponent(CHARGE_KEY))
		);
	}

	@Override
	public void initNamespaces() {
		Registries.RECIPES.register(MOD_ID, EERecipes.RECIPE_NAMESPACE);
		EERecipes.RECIPE_NAMESPACE.register("workbench", EERecipes.RECIPE_WORKBENCH);
	}

	@Override
	public void onRecipesReady() {
		EERecipes.registerRecipes();
	}

}
