package com.aerohide.riptide_outside_water;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public record CustomDataPayload(int[] daData) implements CustomPayload {
    public static final PacketCodec<PacketByteBuf, CustomDataPayload> CODEC =
            CustomPayload.codecOf(CustomDataPayload::write, CustomDataPayload::new);

    public static final CustomPayload.Id<CustomDataPayload> ID =
            new CustomPayload.Id<>(Identifier.of("aerohide", "custom_data"));

    private CustomDataPayload(PacketByteBuf buf) {
        this(buf.readIntArray());
    }

    public CustomDataPayload(int[] daData) {
        this.daData = daData;
    }

    public void write(PacketByteBuf buf) {
        buf.writeIntArray(this.daData);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class ClientReceiver implements ClientPlayNetworking.PlayPayloadHandler<CustomDataPayload> {
        @Override
        public void receive(CustomDataPayload customDataPayload, ClientPlayNetworking.Context context) {
            int daData = customDataPayload.daData()[0];
            if (daData == 123)
            {
                Riptide_outside_water.legal = true;
                Riptide_outside_water.LOG.info("test pass!");
            }
        }
    }
}
