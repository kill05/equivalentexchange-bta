package com.github.kill05.projectbta.mixins;

import com.github.kill05.projectbta.ProjectBTA;
import com.github.kill05.projectbta.utils.NumberUtils;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
	value = GuiTooltip.class,
	remap = false
)
public abstract class GuiTooltipMixin {

	@Inject(
		method = "getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/StringBuilder;append(Ljava/lang/String;)Ljava/lang/StringBuilder;",
			ordinal = 0,
			shift = At.Shift.AFTER
		),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void injectGetTooltipText(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, I18n trans, StringBuilder text) {
		Long emc = ProjectBTA.getEmcValue(itemStack);
		if(emc == null) return;

		boolean shiftPressed = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
		int stackSize = itemStack.stackSize;

		text.append("\n§4EMC:§r ").append(NumberUtils.formatNumber(emc));
		if(shiftPressed && stackSize > 1) {
			text.append("\n§4Stack EMC:§r ").append(NumberUtils.formatNumber(emc * stackSize));
		}
	}
}
