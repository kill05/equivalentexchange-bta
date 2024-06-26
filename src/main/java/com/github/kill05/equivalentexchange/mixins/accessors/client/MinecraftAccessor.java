package com.github.kill05.equivalentexchange.mixins.accessors.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
	value = Minecraft.class,
	remap = false
)
public interface MinecraftAccessor {

	@Accessor(
		value = "ticksRan"
	)
	int getTicksRan();
}
