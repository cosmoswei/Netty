package com.wei.netty2.chapter1;

import sun.misc.Signal;

import java.util.Locale;

public class SignalDemo {
    public static void main(String[] args) {
        Signal signal = new Signal("SIGINT");
        String win = System.getProperties().getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("win") ? "INT" : "TERM";
        System.out.println(win);
    }
}
