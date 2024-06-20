package com.github.kill05.projectbta.config;

import com.github.kill05.projectbta.registry.EmcRegistry;
import com.github.kill05.projectbta.ProjectBTA;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

public final class ProjectConfig {

	public static final TomlConfigHandler CONFIG;

	public static final String EMC_CONFIG_KEY = "EMC Values";

	public static int ITEM_ID;
	public static int BLOCK_ID;

	static {
		Toml toml = new Toml();

		// Id
		toml.addCategory("Set from which ID each category of blocks/items should start registering.", "IDs");
		toml.addEntry("IDs.Items", 24000);
		toml.addEntry("IDs.Blocks", 1600);

		CONFIG = new TomlConfigHandler(ProjectBTA.MOD_ID, EmcRegistry.addDefaults(toml));

		ITEM_ID = toml.get("IDs.Items", Integer.class);
		BLOCK_ID = toml.get("IDs.Blocks", Integer.class);
	}

	private ProjectConfig() {

	}
}
