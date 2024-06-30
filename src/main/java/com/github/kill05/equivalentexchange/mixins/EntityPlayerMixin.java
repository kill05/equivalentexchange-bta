package com.github.kill05.equivalentexchange.mixins;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcRegistry;
import com.github.kill05.equivalentexchange.utils.NbtUtils;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(
	value = EntityPlayer.class,
	remap = false
)
public abstract class EntityPlayerMixin implements IPlayerEmcHolder {

	@Unique
	private long emc;

	@Unique
	private boolean hasTome;

	@Unique
	private final List<EmcKey> knownItems = new ArrayList<>();


	@Inject(
		method = "addAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectAddData(CompoundTag tag, CallbackInfo ci) {
		CompoundTag eeTag = NbtUtils.getMainCompound(tag, true);
		eeTag.putLong("emc", emc);

		ListTag list = NbtUtils.getOrCreateList(eeTag, "known_items");
		for (EmcKey emcKey : knownItems) {
			list.addTag(emcKey.serialize());
		}
	}

	@Inject(
		method = "readAdditionalSaveData",
		at = @At("TAIL")
	)
	public void injectReadData(CompoundTag tag, CallbackInfo ci) {
		CompoundTag eeTag = NbtUtils.getMainCompound(tag, false);
		this.emc = eeTag.getLong("emc");

		for (Tag<?> item : NbtUtils.getOrCreateList(eeTag, "known_items")) {
			if(!(item instanceof CompoundTag keyTag)) continue;
			EmcKey key = EmcKey.deserialize(keyTag);
			if(key == null) continue;

			knownItems.add(key);
		}
	}

	@Override
	public long getEmc() {
		return emc;
	}

	@Override
	public EmcTransaction setEmc(long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionSetEmc(emc, getMaxEmc(), ignoreLimit, aLong -> this.emc = aLong);
	}

	@Override
	public List<EmcKey> getKnownItems() {
		return hasTome ? EmcRegistry.getInstance().getSortedKeys() : knownItems;
	}

	@Override
	public List<EmcKey> getActualKnownItems() {
		return knownItems;
	}

	@Override
	public void learnItems(List<EmcKey> items) {
		this.knownItems.addAll(items);
		knownItems.sort(EmcRegistry.KEY_COMPARATOR);
	}

	@Override
	public boolean knowsItem(EmcKey key) {
		return knownItems.contains(key);
	}

	@Override
	public boolean learnItem(EmcKey key) {
		if(key.emcValue() == null || knownItems.contains(key)) return false;
		knownItems.add(key);
		knownItems.sort(EmcRegistry.KEY_COMPARATOR);

		//todo: tome check

		return true;
	}

	@Override
	public boolean unlearnItem(EmcKey key) {
		if(key.emcValue() == null || !knownItems.contains(key)) return false;
		knownItems.remove(key);

		//todo: tome check

		return true;
	}

	@Override
	public boolean hasTome() {
		return hasTome;
	}

	@Override
	public void setTome(boolean tome) {
		this.hasTome = tome;
	}
}
