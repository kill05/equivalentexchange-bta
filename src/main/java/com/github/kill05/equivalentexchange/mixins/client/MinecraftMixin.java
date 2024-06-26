package com.github.kill05.equivalentexchange.mixins.client;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.items.IChargeableItem;
import com.github.kill05.equivalentexchange.items.tools.EEPickaxeItem;
import com.github.kill05.equivalentexchange.utils.KeyUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.option.InputDevice;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
	value = Minecraft.class,
	remap = false
)
public abstract class MinecraftMixin {

	@Shadow
	public EntityPlayerSP thePlayer;

	@Shadow
	public World theWorld;

	@Inject(
		method = "checkBoundInputs",
		at = @At(value = "TAIL")
	)
	public void injectCheckBoundInputs(InputDevice device, CallbackInfoReturnable<Boolean> cir) {
		if(thePlayer == null) return;
		ItemStack itemStack = thePlayer.getCurrentEquippedItem();

		if(EquivalentExchange.CHARGE_KEY.isPressEvent(device)) {
			if(itemStack == null || !(itemStack.getItem() instanceof IChargeableItem item)) return;

			boolean shiftPressed = KeyUtils.isShiftPressed();
			boolean charged = shiftPressed ? item.uncharge(itemStack) : item.charge(itemStack);
			if(charged) {
				String sound = EquivalentExchange.MOD_ID + "." + (shiftPressed ? "uncharge" : "charge");
				float pitch = (item.getCharge(itemStack)) / (float) (item.getMaxCharge(itemStack) + 1);
				if(shiftPressed) pitch = pitch / 2f + 0.5f;
				theWorld.playSoundAtEntity(null, thePlayer,  sound, 0.7f, pitch);
			}
		}

		if(EquivalentExchange.CHANGE_MODE_KEY.isPressEvent(device)) {
			if(itemStack == null || !(itemStack.getItem() instanceof EEPickaxeItem pickaxe)) return;
			pickaxe.cycleMiningMode(itemStack);
		}
	}
}
