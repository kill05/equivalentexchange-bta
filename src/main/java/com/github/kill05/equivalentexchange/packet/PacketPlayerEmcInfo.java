package com.github.kill05.equivalentexchange.packet;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketPlayerEmcInfo extends Packet {

	public long emc;
	public boolean hasTome;
	public List<EmcKey> knownItems;

	public PacketPlayerEmcInfo(IPlayerEmcHolder player) {
		this.emc = player.getEmc();
		this.hasTome = player.hasTome();
		this.knownItems = player.getKnownItems();
	}

	public PacketPlayerEmcInfo(EntityPlayer player) {
		this((IPlayerEmcHolder) player);
	}

	public PacketPlayerEmcInfo() {

	}

	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		emc = input.readLong();
		hasTome = input.readBoolean();

		int length = input.readInt();
		if(length <= 0 || length > Item.itemsList.length) {
			EquivalentExchange.LOGGER.warn("Received bad known items list size:" + length);
			throw new IOException("Received bad EMC info packet.");
		}

		knownItems = new ArrayList<>();
		for(int i = 0; i < length; i++) {
			int id = input.readInt();
			int meta = input.readInt();

			try {
				EmcKey item = new EmcKey(id, meta);
				knownItems.add(item);
			} catch (IllegalArgumentException e) {
				EquivalentExchange.LOGGER.warn(String.format("Received bad EMC key (id: %s, meta: %s). Skipping...", id, meta));
			}
		}
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeLong(emc);
		output.writeBoolean(hasTome);

		if(knownItems == null || knownItems.size() == 0) {
			output.writeInt(0);
			return;
		}

		output.writeInt(knownItems.size());
		for (EmcKey item : knownItems) {
			output.writeInt(item.itemId());
			output.writeInt(item.meta());
		}
	}

	@Override
	public void processPacket(NetHandler netHandler) {
		IPlayerEmcHolder player = (IPlayerEmcHolder) Minecraft.getMinecraft(this).thePlayer;
		player.setEmc(emc);
		player.setTome(hasTome);
		if(knownItems != null) player.learnItems(knownItems);
	}

	@Override
	public int getPacketSize() {
		return 8 + 1 + 4 + knownItems.size() * 8;
	}
}
