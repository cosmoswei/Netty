package com.wei.netty.dubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class DubboClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;
    private String result;
    private String para;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive被调用");
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead被调用");
        result = msg.toString();
        System.out.println("channelRead.result" + result);
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call1被调用");
        context.writeAndFlush(para);
        wait();
        System.out.println("call2被调用");
        return result;
    }

    void setPara(String para) {
        System.out.println("setPara被调用");
        this.para = para;
    }
}
