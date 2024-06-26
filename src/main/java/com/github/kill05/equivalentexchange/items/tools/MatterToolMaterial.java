package com.github.kill05.equivalentexchange.items.tools;

import net.minecraft.core.item.material.ToolMaterial;

public class MatterToolMaterial extends ToolMaterial {

	private int maxCharge;

	public static final MatterToolMaterial DARK_MATTER = new MatterToolMaterial()
		.setMaxCharge(2)
		.setDurability(0)
		.setEfficiency(15f, 45f)
		.setMiningLevel(4);


	public static final ToolMaterial RED_MATTER = new MatterToolMaterial()
		.setMaxCharge(3)
		.setDurability(0)
		.setEfficiency(20f, 45f)
		.setMiningLevel(5);

	public int getMaxCharge() {
		return maxCharge;
	}

	public MatterToolMaterial setMaxCharge(int maxCharge) {
		this.maxCharge = maxCharge;
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
