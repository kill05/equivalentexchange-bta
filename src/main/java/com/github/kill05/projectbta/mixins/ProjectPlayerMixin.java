package com.github.kill05.projectbta.mixins;

import com.github.kill05.projectbta.ProjectPlayer;
import com.github.kill05.projectbta.registry.EmcKey;
import com.github.kill05.projectbta.utils.NbtUtils;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(
	value = EntityPlayer.class,
	remap = false
)
public abstract class ProjectPlayerMixin implements ProjectPlayer {

	@Unique
	private long emc;

	@Unique
	private final Collection<EmcKey> learntItems = new HashSet<>();


	@Inject(
		method = "addAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectAddData(CompoundTag tag, CallbackInfo ci) {
		CompoundTag projectTag = NbtUtils.getMainCompound(tag, true);
		projectTag.putLong("emc", emc);

		ListTag list = NbtUtils.getOrCreateList(projectTag, "learntItems");
		for (EmcKey emcKey : learntItems) {
			list.addTag(emcKey.serialize());
		}
	}

	@Inject(
		method = "readAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectReadData(CompoundTag tag, CallbackInfo ci) {
		CompoundTag projectTag = NbtUtils.getMainCompound(tag, false);
		this.emc = projectTag.getLong("emc");

		for (Tag<?> item : NbtUtils.getOrCreateList(projectTag, "learntItems")) {
			if(!(item instanceof CompoundTag keyTag)) continue;
			EmcKey key = EmcKey.deserialize(keyTag);
			if(key == null) continue;

			learntItems.add(key);
		}
	}

	@Override
	public long getEmc() {
		return emc;
	}

	@Override
	public void setEmc(long emc) {
		this.emc = emc;
	}

	@Override
	public boolean learnItem(EmcKey key) {
		if(key.emcValue() == null) return false;
		return learntItems.add(key);
	}

	@Override
	public boolean unlearnItem(EmcKey key) {
		if(key.emcValue() == null) return false;
		return learntItems.remove(key);
	}
}
