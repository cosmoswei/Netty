package com.wei.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @author cosmoswei
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client：" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server :GOGOGO!", CharsetUtil.UTF_8));
        while (true) {
            Scanner scan = new Scanner(System.in);
            String str = scan.nextLine();
            ctx.writeAndFlush(Unpooled.copiedBuffer(str, CharsetUtil.UTF_8));
            if(str.equals("-1")){
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
        System.out.println("服务器回复的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().closeFuture();
    }
}
