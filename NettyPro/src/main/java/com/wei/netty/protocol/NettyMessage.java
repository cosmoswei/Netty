package com.wei.netty.protocol;


public class NettyMessage {
    private Header handler;
    private Object body;

    public final Header getHandler() {
        return handler;
    }

    public final void setHandler(Header handler) {
        this.handler = handler;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "handler=" + handler +
                ", body=" + body +
                '}';
    }
}
