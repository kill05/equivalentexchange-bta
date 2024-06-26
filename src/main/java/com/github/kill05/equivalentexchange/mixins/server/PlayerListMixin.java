package com.github.kill05.equivalentexchange.mixins.server;

import com.github.kill05.equivalentexchange.packet.PacketPlayerEmcInfo;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
	value = PlayerList.class,
	remap = false
)
public abstract class PlayerListMixin {

	@Inject(
		method = "playerLoggedIn",
		at = @At("TAIL")
	)
	public void injectPlayerLogin(EntityPlayerMP player, CallbackInfo ci) {
		player.playerNetServerHandler.sendPacket(new PacketPlayerEmcInfo(player));
	}
}
