package com.wei.netty2.chapter1;

import java.lang.management.MemoryUsage;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.DAYS.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Daemon-T");
        thread.setDaemon(true);
        thread.start();
        TimeUnit.SECONDS.sleep(15);
        System.out.println("系统退出，程序执行" + (System.nanoTime() - startTime) / 1000 / 1000 / 1000 + "s");
    }
}
