package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.items.tools.IMatterPickaxe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.core.HitResult;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
	value = RenderGlobal.class,
	remap = false
)
public abstract class RenderGlobalMixin {

	@Shadow
	public abstract void drawOutlinedBoundingBox(AABB aabb);

	@Inject(
		method = "drawSelectionBox",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/core/util/phys/AABB;)V",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void injectDrawBox(ICamera camera, HitResult hitResult, float partialTick, CallbackInfo ci) {
		EntityPlayerSP player = Minecraft.getMinecraft(RenderGlobalMixin.class).thePlayer;
		ItemStack itemStack = player.getCurrentEquippedItem();
		if(itemStack == null || !(itemStack.getItem() instanceof IMatterPickaxe pickaxe)) return;

		double offsetX = camera.getX(partialTick);
		double offsetY = camera.getY(partialTick);
		double offsetZ = camera.getZ(partialTick);

		drawOutlinedBoundingBox(pickaxe.getMiningBox(itemStack, player, hitResult.side, hitResult.x, hitResult.y, hitResult.z)
			.getOffsetBoundingBox(-offsetX, -offsetY, -offsetZ)
			.expand(0.005, 0.005, 0.005));

		GL11.glDepthMask(true);
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		ci.cancel();
	}
}
