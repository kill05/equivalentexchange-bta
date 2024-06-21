package com.github.kill05.projectbta.blocks.transtable.inventory;

import com.github.kill05.projectbta.emc.ProjectPlayer;
import com.github.kill05.projectbta.utils.NumberUtils;
import com.github.kill05.projectbta.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class TransmutationTableGui extends GuiContainer {

	private String middleString;
	private long middleStringExpire;

	public TransmutationTableGui(EntityPlayer player) {
		super(new TransmutationTableContainer(player));
		this.xSize = 229;
		this.ySize = 197;
	}

	@Override
	public void init() {
		super.init();

		add(new TransmutationPageButton(0, 125, 98));
		add(new TransmutationPageButton(1, 190, 97));
	}

	@Override
	public <E extends GuiButton> E add(E button) {
		button.id = controlList.size();
		button.xPosition = (width -  xSize) / 2 + button.xPosition;
		button.yPosition = (height - ySize) / 2 + button.yPosition;
		return super.add(button);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.bindTexture("gui/transmute.png");
		GL11.glColor4f(1f, 1f, 1f, 1f);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		ProjectPlayer player = ((ProjectPlayer) getContainer().getPlayer());

		fontRenderer.drawString("Transmutation", 6, 7, 0x404040);
		fontRenderer.drawString("EMC:", 8, 92, 0x404040);
		fontRenderer.drawString(NumberUtils.formatNumber(player.getEmc()), 8, 93 + fontRenderer.fontHeight, 0x404040);

		if(middleString != null) {
			int x = 101 - middleString.length() / 2;
			int y = 56  - middleString.length() / 2 * (fontRenderer.fontHeight - 1);

			for (char c : middleString.toCharArray()) {
				int offset = (6 - fontRenderer.getCharWidth(c)) / 2;
				fontRenderer.drawString(String.valueOf(c), x + offset, y, 0x404040);

				x += 1;
				y += fontRenderer.fontHeight - 1;
			}

			if(System.currentTimeMillis() > middleStringExpire) {
				middleString = null;
				middleStringExpire = 0L;
			}
		}
	}

	public void displayMiddleString(String middleString) {
		this.middleString = middleString;
		this.middleStringExpire = System.currentTimeMillis() + 2500L;
	}

	public TransmutationTableContainer getContainer() {
		return (TransmutationTableContainer) inventorySlots;
	}


	public static void displayGuiMessage(TransmutationTableContainer container, String message) {
		if(!(container.getPlayer() instanceof EntityPlayerSP)) return;
		GuiScreen screen = Minecraft.getMinecraft(TransmutationTableGui.class).currentScreen;
		if(screen instanceof TransmutationTableGui gui) gui.displayMiddleString(message);
	}
}
