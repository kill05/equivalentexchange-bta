package com.github.kill05.equivalentexchange.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Unique;

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

	public static void drawGui(String texture) {
		GuiContainer gui = getGui();
		RenderUtils.bindTexture(texture);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		gui.drawTexturedModalRect(startX(), startY(), 0, 0,gui.xSize, gui.ySize);
	}

	public static void drawProgressBar(long value, long maxValue, int barLength, int x, int y, int u, int v, int barWidth, boolean vertical) {
		GuiContainer gui = getGui();
		int size = (int) Math.ceil(((float) Math.min(value, maxValue) / maxValue) * barLength);

		gui.drawTexturedModalRect(
			RenderUtils.startX() + x,
			RenderUtils.startY() + y,
			u, v,
			vertical ? barWidth : size,
			vertical ? size : barWidth
		);
	}


	public static int startX() {
		GuiContainer gui = getGui();
		return (gui.width - gui.xSize) / 2;
	}

	public static int startY() {
		GuiContainer gui = getGui();
		return (gui.height - gui.ySize) / 2;
	}

	private static GuiContainer getGui() {
		GuiScreen screen = Minecraft.getMinecraft(RenderUtils.class).currentScreen;
		if(!(screen instanceof GuiContainer gui))
			throw new IllegalStateException("Current gui must be a gui container!");

		return gui;
	}


	@Unique
	private void drawSolidBox(AABB aabb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);

		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);

		tessellator.addVertex(aabb.minX, aabb.minY, aabb.minZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.minZ);

		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.minZ);

		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);

		tessellator.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
		tessellator.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
		tessellator.addVertex(aabb.minX, aabb.minY, aabb.maxZ);

		tessellator.draw();
	}

}
