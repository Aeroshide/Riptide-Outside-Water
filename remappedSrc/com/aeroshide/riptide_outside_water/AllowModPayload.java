package com.aeroshide.riptide_outside_water;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;


public record AllowModPayload(boolean toggle) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AllowModPayload> ID = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("aeroshide", "custom_data"));
    public static final StreamCodec<FriendlyByteBuf, AllowModPayload> CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, AllowModPayload::toggle, AllowModPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
