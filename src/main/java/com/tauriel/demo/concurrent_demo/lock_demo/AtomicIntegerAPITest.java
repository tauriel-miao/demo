package com.tauriel.demo.concurrent_demo.lock_demo;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试原子性
 * 需要：
 * 两个线程对一个变量i做加法运算（++），每个线程执行1万次。
 * @author ysq
 *
 */
public class AtomicIntegerAPITest {

    public static AtomicInteger num=new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        new HashMap<>();
        CountDownLatch cdl=new CountDownLatch(2);
        new Thread(new Add1Runner(cdl)).start();
        new Thread(new Add2Runner(cdl)).start();
        cdl.await();
        System.out.println(num);
    }
}

class Add1Runner implements Runnable{

    private CountDownLatch cdl;

    public Add1Runner(CountDownLatch cdl) {
        this.cdl=cdl;
    }

    public void run() {
        for(int i=0;i<100000;i++){
            AtomicIntegerAPITest.num.addAndGet(1);
        }
        cdl.countDown();
    }

}
class Add2Runner implements Runnable{

    private CountDownLatch cdl;

    public Add2Runner(CountDownLatch cdl) {
        this.cdl=cdl;
    }

    public void run() {
        for(int i=0;i<100000;i++){
            AtomicIntegerAPITest.num.addAndGet(1);
        }
        cdl.countDown();
    }

}

