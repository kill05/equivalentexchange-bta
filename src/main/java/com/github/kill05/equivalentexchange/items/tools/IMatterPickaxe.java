package com.github.kill05.equivalentexchange.items.tools;

import com.github.kill05.equivalentexchange.items.IChargeableItem;
import com.github.kill05.equivalentexchange.utils.NbtUtils;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;

public interface IMatterPickaxe extends IChargeableItem {

	default MiningMode getMiningMode(ItemStack itemStack) {
		MiningMode[] values = MiningMode.values();
		int mode = NbtUtils.getMainCompound(itemStack.getData(), false).getInteger("mode");
		return mode < 0 || mode >= values.length ? MiningMode.NONE : values[mode];
	}

	default void setMiningMode(ItemStack itemStack, MiningMode mode) {
		NbtUtils.getMainCompound(itemStack.getData(), true).putInt("mode", mode.ordinal());
	}

	default void cycleMiningMode(ItemStack itemStack) {
		MiningMode[] values = MiningMode.values();
		MiningMode next = values[(getMiningMode(itemStack).ordinal() + 1) % values.length];
		setMiningMode(itemStack, next);
	}

	default AABB getMiningBox(ItemStack itemStack, EntityLiving entity, Side side, int x, int y, int z) {
		MiningMode mode = getMiningMode(itemStack);

		Direction left = Direction.getHorizontalDirection(entity.yRot - 90f);
		Direction bottom = side.isHorizontal() ? Direction.DOWN : Direction.getHorizontalDirection(entity.yRot + 180f);
		Direction backwards = side.getDirection();

		int x1 = left.getOffsetX() * mode.getLeft() + bottom.getOffsetX() * mode.getDown() + backwards.getOffsetX() * mode.getBackwards();
		int y1 = left.getOffsetY() * mode.getLeft() + bottom.getOffsetY() * mode.getDown() + backwards.getOffsetY() * mode.getBackwards();
		int z1 = left.getOffsetZ() * mode.getLeft() + bottom.getOffsetZ() * mode.getDown() + backwards.getOffsetZ() * mode.getBackwards();
		int x2 = -(left.getOffsetX() * mode.getRight() + bottom.getOffsetX() * mode.getUp() + backwards.getOffsetX() * mode.getForwards());
		int y2 = -(left.getOffsetY() * mode.getRight() + bottom.getOffsetY() * mode.getUp() + backwards.getOffsetY() * mode.getForwards());
		int z2 = -(left.getOffsetZ() * mode.getRight() + bottom.getOffsetZ() * mode.getUp() + backwards.getOffsetZ() * mode.getForwards());

		int xMin = Math.min(x1, x2);
		int yMin = Math.min(y1, y2);
		int zMin = Math.min(z1, z2);
		int xMax = Math.max(x1, x2);
		int yMax = Math.max(y1, y2);
		int zMax = Math.max(z1, z2);

		return new AABB(
			x + xMin, y + yMin, z + zMin,
			x + xMax + 1, y + yMax + 1, z + zMax + 1
		);
	}

}
