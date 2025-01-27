package com.aeroshide.riptide_outside_water.mixins;


import com.aeroshide.riptide_outside_water.AllowModPayload;
import com.aeroshide.riptide_outside_water.Riptide_outside_water;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.aeroshide.riptide_outside_water.Riptide_outside_water.LOG;


@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {


    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        if (!player.getServer().isSingleplayer()) {
            ServerPlayNetworking.send(player, new AllowModPayload(true));
            Riptide_outside_water.LOG.info("server tries to toggle Allowmod");
        }
        else
            LOG.info("Playing singleplayer, will not try to authenticate.");
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onPlayerDisconnect(ServerPlayerEntity player, CallbackInfo ci) {
        if (!player.getServer().isSingleplayer())
        {
            ServerPlayNetworking.send(player, new AllowModPayload(false));
            Riptide_outside_water.LOG.info("server tries to toggle Allowmod");
        }
        else
            LOG.info("Playing singleplayer, will not try to deauthenticate.");
    }









}



