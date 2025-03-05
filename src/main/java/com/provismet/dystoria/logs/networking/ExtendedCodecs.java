package com.provismet.dystoria.logs.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.encoding.StringEncoding;

/**
 * The vanilla string codec might be too short if some goober pulls out a stall team. Exceeding the max length can crash the client.
 */
public interface ExtendedCodecs {
    PacketCodec<ByteBuf, String> JSON = new PacketCodec<>() {
        public String decode (ByteBuf byteBuf) {
            return StringEncoding.decode(byteBuf, 131072);
        }

        public void encode (ByteBuf byteBuf, String string) {
            StringEncoding.encode(byteBuf, string, 131072);
        }
    };
}
