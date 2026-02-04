package com.aeroshide.riptide_outside_water;

import com.mojang.datafixers.TypeRewriteRule;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aeroshide.rose_bush.config.Config;

import java.io.IOException;
import java.nio.file.Path;

public class Riptide_outside_water implements ModInitializer {

    public static final Logger LOG = LogManager.getLogger("RiptideOutsideWater");
    public static int cooldownTime = 200;
    public static boolean refresh = true;
    public static Config config;

    static {
        try {
            config = new Config(Path.of("config/RiptideOutsideWater.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean serverAllowMod = true;

    @Override
    public void onInitialize() {

        if (config.getOption("appliedCooldown") == null)
        {
            try {
                config.setOption("appliedCooldown", 200.0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (config.getOption("refreshCooldownOnTouchingWaterOrRain") == null)
        {
            try {
                config.setOption("refreshCooldownOnTouchingWaterOrRain", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        cooldownTime = ((Double) config.getOption("appliedCooldown")).intValue();
        refresh = ((boolean) config.getOption("refreshCooldownOnTouchingWaterOrRain"));


        PayloadTypeRegistry.playS2C().register(AllowModPayload.ID, AllowModPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(AllowModPayload.ID, AllowModPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(AllowModPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                serverAllowMod = payload.toggle();
                Riptide_outside_water.LOG.info("toggled AllowMod: " + serverAllowMod + "| Signal was: " + payload.toggle());
            });
        });
    }
}
