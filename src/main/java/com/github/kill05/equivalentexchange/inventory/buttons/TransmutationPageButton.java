package com.github.kill05.equivalentexchange.inventory.buttons;

import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class TransmutationPageButton extends GuiButton {

	public TransmutationPageButton(int id, int xPosition, int yPosition) {
		super(id, xPosition, yPosition, 16, 16, null);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		RenderUtils.bindTexture("gui/transmute.png");
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		drawTexturedModalRect(xPosition, yPosition, 240, id * 16, width, height);
	}
}
