package com.wei.netty.dubbo;

import java.util.concurrent.ExecutionException;

public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DubboClient client = new DubboClient();
        HelloService service = (HelloService) client.getBean(HelloService.class, providerName);
        for (int i = 1; i < 100; i++) {
            Thread.sleep(2 * 1000);
            String res = service.hello("hello dubbo~");
            System.out.println("result= " + res);
        }
    }
}
