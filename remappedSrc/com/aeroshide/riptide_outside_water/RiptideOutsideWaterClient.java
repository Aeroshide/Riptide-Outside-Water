package com.aeroshide.riptide_outside_water;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;

public class RiptideOutsideWaterClient implements ClientModInitializer {
    public static boolean clientAllowMod = false;
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(AllowModPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                clientAllowMod = payload.toggle();
                Riptide_outside_water.LOG.info("toggled AllowMod: " + clientAllowMod + "| Signal was: " + payload.toggle());
            });
        });

    }
}
