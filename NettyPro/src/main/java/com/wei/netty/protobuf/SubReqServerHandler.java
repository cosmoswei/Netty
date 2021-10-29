package com.wei.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("cosmoswei".equalsIgnoreCase(req.getUserName())) {
            System.out.println("Service accept client subscrite req : 【" + req + "】");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subRespID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subRespID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days later, sent to this designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
