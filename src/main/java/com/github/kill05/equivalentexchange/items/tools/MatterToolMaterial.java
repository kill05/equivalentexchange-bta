package com.github.kill05.equivalentexchange.items.tools;

import net.minecraft.core.item.material.ToolMaterial;

import java.util.HashMap;
import java.util.Map;

public class MatterToolMaterial extends ToolMaterial {

	// key is the item id, value is max charge
	private final Map<Class<? extends IMatterTool>, Integer> maxChargeMap;
	private int defaultMaxCharge;

	public static final MatterToolMaterial DARK_MATTER = new MatterToolMaterial()
		.setDefaultMaxCharge(2)
		.setMaxCharge(EEShovelItem.class, 1)
		.setEfficiency(15f, 50f)
		.setMiningLevel(4);


	public static final MatterToolMaterial RED_MATTER = new MatterToolMaterial()
		.setDefaultMaxCharge(3)
		.setEfficiency(25f, 75f)
		.setMiningLevel(5);


	public MatterToolMaterial() {
		maxChargeMap = new HashMap<>();
		setDurability(0);
	}


	public int getMaxCharge(IMatterTool item) {
		Integer maxCharge = maxChargeMap.get(item.getClass());
		return maxCharge != null ? maxCharge : defaultMaxCharge;
	}

	public MatterToolMaterial setDefaultMaxCharge(int maxCharge) {
		this.defaultMaxCharge = maxCharge;
		return this;
	}

	public MatterToolMaterial setMaxCharge(Class<? extends IMatterTool> clazz, int maxCharge) {
		maxChargeMap.put(clazz, maxCharge);
		return this;
	}

	@Override
	public MatterToolMaterial setDurability(int durability) {
		return (MatterToolMaterial) super.setDurability(durability);
	}

	@Override
	public MatterToolMaterial setDamage(int damage) {
		return (MatterToolMaterial) super.setDamage(damage);
	}

	@Override
	public MatterToolMaterial setEfficiency(float efficiency, float hasteEfficiency) {
		return (MatterToolMaterial) super.setEfficiency(efficiency, hasteEfficiency);
	}

	@Override
	public MatterToolMaterial setSilkTouch(boolean silkTouch) {
		return (MatterToolMaterial) super.setSilkTouch(silkTouch);
	}

	@Override
	public MatterToolMaterial setBlockHitDelay(int miningDelay) {
		return (MatterToolMaterial) super.setBlockHitDelay(miningDelay);
	}

	@Override
	public MatterToolMaterial setMiningLevel(int miningLevel) {
		return (MatterToolMaterial) super.setMiningLevel(miningLevel);
	}
}
