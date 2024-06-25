package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.items.tools.IMatterPickaxe;
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
		at = @At("TAIL")
	)
	public void injectCheckBoundInputs(InputDevice device, CallbackInfoReturnable<Boolean> cir) {
		if(thePlayer == null) return;
		ItemStack itemStack = thePlayer.getCurrentEquippedItem();
		if(itemStack == null || !(itemStack.getItem() instanceof IMatterPickaxe pickaxe)) return;

		if(EquivalentExchange.CHARGE_KEY.isPressEvent(device)) {
			boolean shiftPressed = KeyUtils.isShiftPressed();
			boolean charged = shiftPressed ? pickaxe.uncharge(itemStack) : pickaxe.charge(itemStack);
			if(charged) {
				String sound = EquivalentExchange.MOD_ID + "." + (shiftPressed ? "uncharge" : "charge");
				theWorld.playSoundAtEntity(null, thePlayer,  sound, 0.7f, 1f);
			}
		}

		if(EquivalentExchange.CHANGE_MODE_KEY.isPressEvent(device)) {
			pickaxe.cycleMiningMode(itemStack);
		}
	}
}
