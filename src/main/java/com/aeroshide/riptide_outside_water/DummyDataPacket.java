package com.aeroshide.riptide_outside_water;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class DummyDataPacket implements Packet<ClientPlayPacketListener> {

    public DummyDataPacket(int data) {
    }

    @Override
    public PacketType<? extends Packet<ClientPlayPacketListener>> getPacketType() {
        return null;
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {


    }
}