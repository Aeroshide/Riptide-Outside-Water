package com.aeroshide.riptide_outside_water;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class DummyDataPacket implements Packet<@org.jetbrains.annotations.NotNull ClientGamePacketListener> {

    public DummyDataPacket(int data) {
    }

    @Override
    public PacketType<? extends Packet<ClientGamePacketListener>> type() {
        return null;
    }

    @Override
    public void handle(ClientGamePacketListener packetListener) {

    }
}