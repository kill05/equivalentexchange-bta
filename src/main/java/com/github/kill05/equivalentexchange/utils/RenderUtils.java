package com.github.kill05.equivalentexchange.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

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

	public static void drawGui(GuiContainer gui, String texture) {
		RenderUtils.bindTexture(texture);
		GL11.glColor4f(1f, 1f, 1f, 1f);

		int x = (gui.width - gui.xSize) / 2;
		int y = (gui.height - gui.ySize) / 2;
		gui.drawTexturedModalRect(x, y, 0, 0,gui.xSize, gui.ySize);
	}

}
