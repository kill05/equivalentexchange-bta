package com.github.kill05.equivalentexchange.inventory.gui;

import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
import com.github.kill05.equivalentexchange.inventory.buttons.TransmutationPageButton;
import com.github.kill05.equivalentexchange.inventory.container.TransmutationTableContainer;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import com.github.kill05.equivalentexchange.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import turniplabs.halplibe.HalpLibe;

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
		RenderUtils.drawGui(this, "gui/transmute.png");
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		IPlayerEmcHolder player = ((IPlayerEmcHolder) getContainer().getPlayer());

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


	public static void displayGuiMessage(String message) {
		if(!HalpLibe.isClient) return;
		GuiScreen screen = Minecraft.getMinecraft(TransmutationTableGui.class).currentScreen;
		if(screen instanceof TransmutationTableGui gui) gui.displayMiddleString(message);
	}
}
