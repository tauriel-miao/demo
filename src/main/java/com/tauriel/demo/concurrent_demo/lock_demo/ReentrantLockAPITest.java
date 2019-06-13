package com.tauriel.demo.concurrent_demo.lock_demo;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试同步代码块的类
 * @author ysq
 *
 * 使用重入锁，一定注意锁释放的问题，在：finally {
lock.unlock();
}
 * 同步代码块锁释放是由JVM来做的
 */
public class ReentrantLockAPITest {
    public  static String name="李雷";
    public  static String gender="男";

    public static void main(String[] args) {

        //true表示是公平锁策略
        //false表示非公平锁策略，默认是false
        ReentrantLock lock=new ReentrantLock();

        new Thread(new sych1Runner(lock)).start();
        new Thread(new sych2Runner(lock)).start();
    }

}

class sych1Runner implements Runnable{
    private ReentrantLock lock;

    public sych1Runner(ReentrantLock lock) {
        this.lock=lock;
    }

    public void run() {
        while(true){
            lock.lock();
            if(ReentrantLockAPITest.name.equals("李雷")){
                ReentrantLockAPITest.name="韩梅梅";
                ReentrantLockAPITest.gender="女";
            }else {
                ReentrantLockAPITest.name="李雷";
                ReentrantLockAPITest.gender="男";
            }
            lock.unlock();
        }

    }

}

class sych2Runner implements Runnable{
    private ReentrantLock lock;

    public sych2Runner(ReentrantLock lock) {
        this.lock=lock;
    }

    public void run() {
        while(true){
            lock.lock();
            System.out.println(ReentrantLockAPITest.name+":"+ ReentrantLockAPITest.gender);
            lock.unlock();


        }

    }

}
