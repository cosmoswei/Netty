package com.wei.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingDecoder {
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingDecoder(UnmarshallerProvider unmarshallerProvider) {
        this.unmarshallerProvider = unmarshallerProvider;
    }

    protected Object decode(ByteBuf in) {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        return null;
    }
}
