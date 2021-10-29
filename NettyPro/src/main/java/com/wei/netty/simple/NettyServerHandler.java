package com.wei.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.internal.ChannelUtils;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @author cosmoswei
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器线程数："+Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端地址为：" + ctx.channel().remoteAddress());
        System.out.println("客户端发送了：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("======message limit line======");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端！", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

}
