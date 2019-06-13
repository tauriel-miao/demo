package com.tauriel.demo.concurrent_demo.lock_demo;

import java.util.concurrent.Exchanger;

/**
 * 测试交换机,注意使用条件是两个线程之间的数据交换
 * 两个间谍（两个线程）通过交换机交换暗号
 *
 * 间谍1的暗号：回眸一笑
 * 间谍2的暗号：寸草不生
 *
 *
 * @author ysq
 *
 */
public class ExchangerAPITest {
    public static void main(String[] args) {
        Exchanger<String> ex=new Exchanger<String>();

        new Thread(new Spy1Runner(ex)).start();
        new Thread(new Spy2Runner(ex)).start();
    }

}

class Spy1Runner implements Runnable{
    private Exchanger<String> ex;

    public Spy1Runner(Exchanger<String> ex) {
        this.ex=ex;
    }

    public void run() {
        try {
            String info="回眸一笑";
            //exchange() 会将数据发送给对方线程
            //
            String spy2Info=ex.exchange(info);
            System.out.println("间谍1收到间谍2的暗号："+spy2Info);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

}

class Spy2Runner implements Runnable{
    private Exchanger<String> ex;

    public Spy2Runner(Exchanger<String> ex) {
        this.ex=ex;
    }

    public void run() {
        try {
            String info="寸草不生";
            String spy1Info=ex.exchange(info);
            System.out.println("间谍2收到间谍1的暗号："+spy1Info);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

}
