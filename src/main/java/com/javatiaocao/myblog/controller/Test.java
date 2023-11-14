package com.javatiaocao.myblog.controller;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Message p = new Message("张三", "李四", "12344", "2023年5月14日");

        ObjectOutputStream oop = new ObjectOutputStream(new FileOutputStream("demo01\\testIO\\message.dat"));
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("demo01\\testIO\\message.dat"));


        oop.writeObject(p);
        Object o = ois.readObject();

        System.out.println(o);
        ois.close();
        oop.close();


    }
}
