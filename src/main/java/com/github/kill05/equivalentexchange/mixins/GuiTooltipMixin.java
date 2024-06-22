package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.slot.Slot;
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
		Long emc = EquivalentExchange.getEmcValue(itemStack);
		if (emc == null) return;

		text.append("\n§4EMC:§r ").append(NumberUtils.formatNumber(emc));

		if (itemStack.getItem() instanceof IItemEmcHolder holder) {
			text.append("\n§4Stored EMC:§r ").append(NumberUtils.formatNumber(holder.getEmc(itemStack)));
		}

		if (itemStack.stackSize > 1) {
			text.append("\n§4Stack EMC:§r ").append(NumberUtils.formatNumber(emc * itemStack.stackSize));
		}
	}
}
