package com.aeroshide.riptide_outside_water;

import com.mojang.datafixers.TypeRewriteRule;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aeroshide.rose_bush.config.Config;

public class Riptide_outside_water implements ModInitializer {

    public static final Logger LOG = LogManager.getLogger("RiptideOutsideWater");
    public static int cooldownTime = 200;
    public static boolean refresh = true;
    public static Config config = new Config("config/RiptideOutsideWater.json");
    public static boolean serverAllowMod = false;

    @Override
    public void onInitialize() {

        if (config.getOption("appliedCooldown") == null)
        {
            config.setOption("appliedCooldown", 200.0);
        }
        if (config.getOption("refreshCooldownOnTouchingWaterOrRain") == null)
        {
            config.setOption("refreshCooldownOnTouchingWaterOrRain", true);
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
