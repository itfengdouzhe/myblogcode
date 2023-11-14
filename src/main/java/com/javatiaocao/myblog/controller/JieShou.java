package com.javatiaocao.myblog.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author IT枫斗者
 * @ClassName JieShou.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public class JieShou {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket(50086);


        byte[] bys = new byte[1024];
        int length = bys.length;
        DatagramPacket dp = new DatagramPacket(bys, length);


        ds.receive(dp);


        InetAddress address = dp.getAddress();
        String ip = address.getHostAddress();

        byte[] bys2 = dp.getData();
        int len = dp.getLength();
        String s = new String(bys2, 0, len);
        System.out.println(ip + "发送是:" + s);


        ds.close();
    }
}
