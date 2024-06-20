package com.github.kill05.projectbta.utils;

import com.github.kill05.projectbta.ProjectBTA;
import net.minecraft.client.render.stitcher.TextureRegistry;

public final class TextureUtils {

	public static final String TEXTURE_PATH = "/assets/" + ProjectBTA.MOD_ID + "/textures/";

	public static boolean textureFileExists(String path) {
		return RenderUtils.class.getResource(TEXTURE_PATH + path) != null;
	}

	public static boolean registerTextureIfPresent(String path) {
		if(!textureFileExists(path + ".png")) return false;
		TextureRegistry.getTexture(ProjectBTA.MOD_ID + ":" + path);
		return true;
	}

}
