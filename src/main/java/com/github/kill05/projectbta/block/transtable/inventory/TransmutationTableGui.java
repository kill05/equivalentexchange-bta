package com.github.kill05.projectbta.block.transtable.inventory;

import com.github.kill05.projectbta.utils.RenderUtils;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class TransmutationTableGui extends GuiContainer {

	public TransmutationTableGui(EntityPlayer player) {
		super(new TransmutationTableContainer(player));
		this.xSize = 176;
		this.ySize = 222;
	}


	@Override
	public void init() {
		super.init();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		RenderUtils.bindTexture("gui/transmute.png");
		GL11.glColor4f(1f, 1f, 1f, 1f);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
