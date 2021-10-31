package com.wei.netty.dubbo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DubboClient {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static DubboClientHandler clientHandler;
    private int count = 0;

    public Object getBean(final Class<?> serviceClass, final String providerName)
            throws ExecutionException, InterruptedException {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    System.out.println("(proxy, method, args)" + (++count) + "次");
                    if (clientHandler == null) {
                        initClient();
                    }
                    clientHandler.setPara(providerName + args[0]);
                    return executorService.submit(clientHandler).get();
                });
    }

    private static void initClient() {
        clientHandler = new DubboClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(clientHandler);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 8086).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
