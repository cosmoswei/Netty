package com.wei.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder(MarshallingEncoder marshallingEncoder) {
        this.marshallingEncoder = marshallingEncoder;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          NettyMessage nettyMessage,
                          List<Object> list) throws Exception {
        if (nettyMessage == null || nettyMessage.getHandler() == null) {
            throw new Exception("The encode message = null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(nettyMessage.getHandler().getCrcCode());
        sendBuf.writeInt(nettyMessage.getHandler().getLength());
        sendBuf.writeLong(nettyMessage.getHandler().getSessionID());
        sendBuf.writeByte(nettyMessage.getHandler().getType());
        sendBuf.writeByte(nettyMessage.getHandler().getPriority());
        sendBuf.writeInt(nettyMessage.getHandler().getAttachment().size());
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : nettyMessage.getHandler().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes(StandardCharsets.UTF_8);
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
//            marshallingEncoder.encode(value, sendBuf);
        }
        key = null;
        keyArray = null;
        value = null;
        if (nettyMessage.getBody() != null) {
//            marshallingEncoder.encode(nettyMessage.getBody(), sendBuf);
        } else {
            sendBuf.writeInt(0);
        }
        sendBuf.setInt(4, sendBuf.readableBytes());
    }
}
