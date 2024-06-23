package com.github.kill05.equivalentexchange.mixins.accessors;

import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
	value = World.class,
	remap = false
)
public interface WorldAccessor {

	@Accessor(
		value = "runtime"
	)
	long getRuntime();
}
