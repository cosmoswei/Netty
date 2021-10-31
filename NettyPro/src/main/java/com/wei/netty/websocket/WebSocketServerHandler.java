package com.wei.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead0");
        //这句有点牛，打印HTTP信息
        System.out.println(msg);
        System.out.println(ctx);
        ctx.channel().writeAndFlush("time: " + LocalDateTime.now());

        if (msg instanceof FullHttpRequest) {
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
        }
        if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }

    }

    private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

//        if (!req.getDecoderResult().isSuccess() || ("websocket").equals(req.headers().get("Upgrade"))) {
//            //function sendHttpResponse
//            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8083/websocket", null, false);
        handshaker = factory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String
                    .format("%s frame type not supported", frame.getClass().getName()));
        }
        String request = ((TextWebSocketFrame) frame).text();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }
        ctx.channel().write(new TextWebSocketFrame("欢迎使用Netty WebSocket服务!\n" + "message:" + request + "\n现在时刻："
                + new Date()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            //function setContentLength
        }
        ChannelFuture future = ctx.channel().writeAndFlush(response);
//        if (!request.)
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("WebSocketServerHandler.handlerRemoved");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("WebSocketServerHandler.handlerAdded");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
