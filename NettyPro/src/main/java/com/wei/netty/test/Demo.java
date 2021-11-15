package com.wei.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class Demo {
    public static void main(String[] args) {
        int loop = 3000000;
        long startTime1 = System.currentTimeMillis();
        ByteBuf poolBuffer = null;
        ByteBuf CONTENT = null;
        for (int i = 0; i < loop; i++) {
            poolBuffer = PooledByteBufAllocator.DEFAULT.directBuffer(1024);
            poolBuffer.  writeBytes(CONTENT);
        }
        System.out.println("time difference value1: " + (System.currentTimeMillis() - startTime1));

        long startTime2 = System.currentTimeMillis();
        ByteBuf buf = null;
        for (int i = 0; i < loop; i++) {
            buf = Unpooled.directBuffer(1024);
            buf.writeBytes(CONTENT);
        }
        System.out.println("time difference value2: " + (System.currentTimeMillis() - startTime2));

    }
}
