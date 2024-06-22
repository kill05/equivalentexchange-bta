package com.github.kill05.equivalentexchange.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

public final class RenderUtils {

	public static void bindTexture(String path) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		RenderEngine renderEngine = mc.renderEngine;
		renderEngine.bindTexture(renderEngine.getTexture(TextureUtils.TEXTURE_PATH + path));
	}

	public static ItemModel getItemModel(IItemConvertible item) {
		return ItemModelDispatcher.getInstance().getDispatch(item.asItem());
	}

	public static ItemModel getItemModel(ItemStack item) {
		return ItemModelDispatcher.getInstance().getDispatch(item);
	}

}
