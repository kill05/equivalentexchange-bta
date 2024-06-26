package com.github.kill05.equivalentexchange.mixins.client;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.items.tools.EEPickaxeItem;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiTooltip;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
	value = GuiTooltip.class,
	remap = false
)
public abstract class GuiTooltipMixin {

	@Inject(
		method = {"getTooltipText(Lnet/minecraft/core/item/ItemStack;ZLnet/minecraft/core/player/inventory/slot/Slot;)Ljava/lang/String;"},
		at = @At(
			value = "JUMP",
			opcode = 153,
			ordinal = 12
		)
	)
	public void injectGetTooltipText(ItemStack itemStack, boolean showDescription, Slot slot, CallbackInfoReturnable<String> cir, @Local StringBuilder text) {
		Long emc = EquivalentExchange.getEmcValue(itemStack);
		if (emc != null) {
			text.append("\n§4EMC:§r ").append(NumberUtils.formatNumber(emc));

			if (itemStack.getItem() instanceof IItemEmcHolder holder) {
				text.append("\n§4Stored EMC:§r ").append(NumberUtils.formatNumber(holder.getEmc(itemStack)));
			}

			if (itemStack.stackSize > 1) {
				text.append("\n§4Stack EMC:§r ").append(NumberUtils.formatNumber(emc * itemStack.stackSize));
			}
		}

		if(itemStack.getItem() instanceof EEPickaxeItem item) {
			text.append("\nMining mode: ").append(item.getMiningMode(itemStack).getTranslatedName());
		}
	}
}
