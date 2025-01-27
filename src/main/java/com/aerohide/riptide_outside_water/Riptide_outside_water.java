package com.aerohide.riptide_outside_water;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aeroshide.rose_bush.config.Config;

public class Riptide_outside_water implements ModInitializer {

    public static final Logger LOG = LogManager.getLogger("RiptideOutsideWater");
    public static int cooldownTime = 200;
    public static boolean refresh = true;
    public static Config config = new Config("config/RiptideOutsideWater.json");
    public static boolean legal = false;

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


        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PacketByteBuf buffer = new PacketByteBuf(PacketByteBufs.create());
            CustomDataPayload payload = new CustomDataPayload(new int[]{123, 456});
            payload.write(buffer);

            sender.sendPacket(payload);
        });

    }
}
