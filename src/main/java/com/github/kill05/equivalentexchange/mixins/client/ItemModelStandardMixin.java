package com.github.kill05.equivalentexchange.mixins.client;

import com.github.kill05.equivalentexchange.items.ICustomDurabilityBar;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
	value = ItemModelStandard.class,
	remap = false
)
public abstract class ItemModelStandardMixin {

	@Redirect(
		method = "renderItemOverlayIntoGUI",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;isItemDamaged()Z")
	)
	public boolean redirectIsItemDamaged(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if(item instanceof ICustomDurabilityBar) return true;
		return itemStack.isItemDamaged();
	}

	@Redirect(
		method = "renderItemOverlayIntoGUI",
		at = @At(value = "INVOKE", target = "Ljava/lang/Math;round(D)J", ordinal = 0)
	)
	public long redirectBarWidth(double v, @Local(argsOnly = true) ItemStack itemStack) {
		if(itemStack.getItem() instanceof ICustomDurabilityBar holder) {
			return Math.round(holder.getDisplayDurability(itemStack) * 13.0 / holder.getMaxDisplayDurability(itemStack));
		}

		return Math.round(v);
	}

	@Redirect(
		method = "renderItemOverlayIntoGUI",
		at = @At(value = "INVOKE", target = "Ljava/lang/Math;round(D)J", ordinal = 1)
	)
	public long redirectProgress(double v, @Local(argsOnly = true) ItemStack itemStack) {
		if(itemStack.getItem() instanceof ICustomDurabilityBar holder) {
			return Math.round(holder.getDisplayDurability(itemStack) * 255.0 / holder.getMaxDisplayDurability(itemStack));
		}

		return Math.round(v);
	}
}
