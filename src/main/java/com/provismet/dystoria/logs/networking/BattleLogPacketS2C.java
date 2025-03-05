package com.provismet.dystoria.logs.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.provismet.dystoria.logs.DystoriaBattleLogger;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record BattleLogPacketS2C (String json) implements CustomPayload {
    public static final Id<BattleLogPacketS2C> ID = new Id<>(DystoriaBattleLogger.identifier("battle_log"));
    public static final PacketCodec<PacketByteBuf, BattleLogPacketS2C> CODEC = PacketCodec.tuple(
        ExtendedCodecs.JSON,
        BattleLogPacketS2C::json,
        BattleLogPacketS2C::new
    );

    @Override
    public Id<? extends CustomPayload> getId () {
        return ID;
    }

    public static BattleLogPacketS2C fromJson (JsonElement element) {
        Gson gson = new GsonBuilder().create();
        return new BattleLogPacketS2C(gson.toJson(element));
    }
}
