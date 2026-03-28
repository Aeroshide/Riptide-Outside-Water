package com.aeroshide.riptide_outside_water.mixins;


import com.aeroshide.riptide_outside_water.AllowModPayload;
import com.aeroshide.riptide_outside_water.Riptide_outside_water;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
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


@Mixin(PlayerList.class)
public abstract class PlayerManagerMixin {


    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    private void onPlayerConnect(Connection connection, ServerPlayer player, CommonListenerCookie clientData, CallbackInfo ci) {
        ServerPlayNetworking.send(player, new AllowModPayload(true)); // Send in ALL cases because... idk
        Riptide_outside_water.LOG.info("server tries to toggle Allowmod");
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onPlayerDisconnect(ServerPlayer player, CallbackInfo ci) {
        ServerPlayNetworking.send(player, new AllowModPayload(false));
        Riptide_outside_water.LOG.info("server tries to toggle Allowmod");
    }









}



