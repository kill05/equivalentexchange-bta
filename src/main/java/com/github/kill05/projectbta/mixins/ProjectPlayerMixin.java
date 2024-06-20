package com.github.kill05.projectbta.mixins;

import com.github.kill05.projectbta.accessors.ProjectPlayerAccessor;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
	value = EntityPlayer.class,
	remap = false
)
public abstract class ProjectPlayerMixin implements ProjectPlayerAccessor {

	@Unique
	private long emc;

	@Inject(
		method = "addAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectAddData(CompoundTag tag, CallbackInfo ci) {

	}

	@Inject(
		method = "readAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectReadData(CompoundTag tag, CallbackInfo ci) {

	}

	@Override
	public long getEmc() {
		return emc;
	}

	@Override
	public void setEmc(long emc) {
		this.emc = emc;
	}
}
