package com.javatiaocao.myblog.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author IT枫斗者
 * @ClassName FaSong.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public class FaSong {



        public static void main(String[] args) throws IOException {

            DatagramSocket ds = new DatagramSocket();


            byte[] bys = "hello,udp".getBytes();

            int length = bys.length;

            InetAddress address = InetAddress.getByName("192.168.124.64");
            int port = 50086;
            DatagramPacket dp = new DatagramPacket(bys, length, address, port);

            ds.send(dp);

            ds.close();

    }
}
