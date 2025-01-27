package com.aeroshide.riptide_outside_water;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public record AllowModPayload(boolean toggle) implements CustomPayload {
    public static final CustomPayload.Id<AllowModPayload> ID = new CustomPayload.Id<>(Identifier.of("aeroshide", "custom_data"));
    public static final PacketCodec<PacketByteBuf, AllowModPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOLEAN, AllowModPayload::toggle, AllowModPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
