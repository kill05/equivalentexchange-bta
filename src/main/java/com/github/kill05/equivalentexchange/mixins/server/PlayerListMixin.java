package com.github.kill05.equivalentexchange.mixins.server;

import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
import com.github.kill05.equivalentexchange.packet.PacketPlayerEmcInfo;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

	@Inject(
		method = "recreatePlayerEntity",
		at = @At("TAIL"),
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void injectRecreatePlayer(EntityPlayerMP previousPlayer, int i, CallbackInfoReturnable<EntityPlayerMP> cir, ChunkCoordinates chunkcoordinates, EntityPlayerMP newPlayer, WorldServer worldserver) {
		((IPlayerEmcHolder) previousPlayer).transferDataToNewPlayer(newPlayer);
	}
}
