package com.wei.nio;

import java.nio.IntBuffer;

/**
 * @author cosmoswei
 */
public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(4);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 4);
        }
        // 读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
