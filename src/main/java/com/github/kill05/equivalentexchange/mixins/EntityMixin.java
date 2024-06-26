package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.EEItems;
import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.config.EEConfig;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.catalyst.core.util.Vec3f;

import java.util.List;

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
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entity, new AABB(entity.x, entity.y, entity.z, entity.x, entity.y, entity.z).expand(3, 3, 3));
		Vec3f entityPos = new Vec3f(entity.x, entity.y, entity.z);
		for (Entity other : entities) {
			Vec3f vec3f = new Vec3f(other.x, other.y, other.z).subtract(entityPos);

			// Normalize
			double dist = MathHelper.sqrt_double(vec3f.x * vec3f.x + vec3f.y * vec3f.y + vec3f.z * vec3f.z);
			vec3f.x /= dist;
			vec3f.y /= dist;
			vec3f.z /= dist;

			other.push(vec3f.x, vec3f.y, vec3f.z);
		}
	}

	@Unique
	private Entity getThis() {
		return (Entity) ((Object) this);
	}
}
