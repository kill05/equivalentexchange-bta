package com.github.kill05.equivalentexchange.config;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.emc.EmcRegistry;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public final class EEConfig {

	public static final TomlConfigHandler CONFIG;

	public static final String EMC_CONFIG_KEY = "EMC Values";
	public static final String SECRET_SETTING_KEY = "super-duper-secret-setting";

	public static int ITEM_ID;
	public static int BLOCK_ID;

	static {
		Toml toml = new Toml();

		// Id
		toml.addCategory("Set from which ID each category of blocks/items should start registering.", "IDs");
		toml.addEntry("IDs.Items", 24000);
		toml.addEntry("IDs.Blocks", 1600);

		// EMC values
		EmcRegistry.addDefaults(toml);

		// Secret setting
		toml.addCategory("I wonder what could happen if I were to enable any of these...", "Secret Settings");
		toml.addEntry("Secret Settings." + SECRET_SETTING_KEY, false);

		CONFIG = new TomlConfigHandler(EquivalentExchange.MOD_ID, toml);

		ITEM_ID = toml.get("IDs.Items", Integer.class);
		BLOCK_ID = toml.get("IDs.Blocks", Integer.class);
	}

	public static boolean isSecretSettingEnabled() {
		return CONFIG.getBoolean("Secret Settings." + SECRET_SETTING_KEY);
	}

	private EEConfig() {

	}
}
