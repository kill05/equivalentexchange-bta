package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.EEItems;
import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.config.EEConfig;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
	value = Entity.class,
	remap = false
)
public abstract class EntityMixin {

	@Shadow
	public World world;

	@Inject(
		method = "causeFallDamage",
		at = @At("TAIL")
	)
	public void injectFallDamage(float f, CallbackInfo ci) {
		if(!(getThis() instanceof EntityItem entity) || !EEConfig.isSecretSettingEnabled()) return;
		if(entity.item == null) return;

		Item item = entity.item.getItem();
		if(item == null || item != EEItems.SUPER_SECRET_ITEM) return;

		world.playSoundAtEntity(null, entity, EquivalentExchange.MOD_ID + ".super_secret_sound", 1.0f + f, 1.0f);
	}

	@Unique
	private Entity getThis() {
		return (Entity) ((Object) this);
	}
}
