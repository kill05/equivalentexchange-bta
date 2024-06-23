package com.github.kill05.equivalentexchange;

import com.github.kill05.equivalentexchange.blocks.AlchemicalChestBlock;
import com.github.kill05.equivalentexchange.blocks.EnergyCollectorBlock;
import com.github.kill05.equivalentexchange.blocks.EnergyCondenserBlock;
import com.github.kill05.equivalentexchange.blocks.TransmutationTableBlock;
import com.github.kill05.equivalentexchange.config.EEConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

public final class EEBlocks {

	// Simple blocks
	public static final Block ALCHEMICAL_COAL_BLOCK = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(1f)
		.setResistance(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("alchemical_coal_block", EEConfig.BLOCK_ID++, Material.stone));

	public static final Block MOBIUS_FUEL_BLOCK = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(1f)
		.setResistance(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("mobius_fuel_block", EEConfig.BLOCK_ID++, Material.stone));

	public static final Block AETERNALIS_FUEL_BLOCK = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(1f)
		.setResistance(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("aeternalis_fuel_block", EEConfig.BLOCK_ID++, Material.stone));


	public static final Block DARK_MATTER_BLOCK = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(10f)
		.setResistance(50.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("dark_matter_block", EEConfig.BLOCK_ID++, Material.stone));

	public static final Block RED_MATTER_BLOCK = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(15f)
		.setResistance(100.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new Block("red_matter_block", EEConfig.BLOCK_ID++, Material.stone));


	// Tile blocks
	public static final Block TRANSMUTATION_TABLE = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setTopTexture(EquivalentExchange.MOD_ID + ":block/transmutation_table/top")
		.setSideTextures(EquivalentExchange.MOD_ID + ":block/transmutation_table/side")
		.setBottomTexture(EquivalentExchange.MOD_ID + ":block/transmutation_table/bottom")
		.setHardness(2.5f)
		.setResistance(10.0f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new TransmutationTableBlock(EEConfig.BLOCK_ID++));

	public static final Block ALCHEMICAL_CHEST = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(2.5f)
		.setResistance(10f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new AlchemicalChestBlock(EEConfig.BLOCK_ID++));

	public static final Block ENERGY_CONDENSER = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setHardness(2.5f)
		.setResistance(10f)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new EnergyCondenserBlock("energy_condenser", EEConfig.BLOCK_ID++));


	public static final Block COLLECTOR_MK1 = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setTextures(EquivalentExchange.MOD_ID + ":block/collectors/other")
		.setNorthTexture(EquivalentExchange.MOD_ID + ":block/collectors/front")
		.setTopTexture(EquivalentExchange.MOD_ID + ":block/collectors/top_1")
		.setHardness(5f)
		.setResistance(15f)
		.setLuminance(5)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new EnergyCollectorBlock(1, 10000, 4, EEConfig.BLOCK_ID++));

	public static final Block COLLECTOR_MK2 = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setTextures(EquivalentExchange.MOD_ID + ":block/collectors/other")
		.setNorthTexture(EquivalentExchange.MOD_ID + ":block/collectors/front")
		.setTopTexture(EquivalentExchange.MOD_ID + ":block/collectors/top_2")
		.setHardness(5f)
		.setResistance(17.5f)
		.setLuminance(10)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new EnergyCollectorBlock(2, 30000, 12, EEConfig.BLOCK_ID++));

	public static final Block COLLECTOR_MK3 = new BlockBuilder(EquivalentExchange.MOD_ID)
		.setTextures(EquivalentExchange.MOD_ID + ":block/collectors/other")
		.setNorthTexture(EquivalentExchange.MOD_ID + ":block/collectors/front")
		.setTopTexture(EquivalentExchange.MOD_ID + ":block/collectors/top_3")
		.setHardness(5f)
		.setResistance(20f)
		.setLuminance(15)
		.setBlockSound(BlockSounds.STONE)
		.setTags(BlockTags.MINEABLE_BY_PICKAXE)
		.build(new EnergyCollectorBlock(3, 60000, 40, EEConfig.BLOCK_ID++));
}
