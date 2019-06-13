package com.tauriel.demo.concurrent_demo.lock_demo;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁
 * @author ysq
 *
 */
public class ReadWriteLockAPITest {
    public  static String name="李雷";
    public  static String gender="男";

    public static void main(String[] args) {
        ReadWriteLock lock=new ReentrantReadWriteLock();

        new Thread(new sych3Runner(lock)).start();
        new Thread(new sych4Runner(lock)).start();
    }
}

class sych3Runner implements Runnable{
    private ReadWriteLock lock;

    public sych3Runner(ReadWriteLock lock) {
        this.lock=lock;
    }

    public void run() {
        while(true){
            lock.writeLock().lock();
            if(ReadWriteLockAPITest.name.equals("李雷")){
                ReadWriteLockAPITest.name="韩梅梅";
                ReadWriteLockAPITest.gender="女";
            }else {
                ReadWriteLockAPITest.name="李雷";
                ReadWriteLockAPITest.gender="男";
            }
            //finally
            lock.writeLock().unlock();
        }

    }

}

class sych4Runner implements Runnable{

    private ReadWriteLock lock;

    public sych4Runner(ReadWriteLock lock) {
        this.lock=lock;
    }

    public void run() {
        while(true){
            lock.readLock().lock();
            System.out.println(ReadWriteLockAPITest.name+":"+ReadWriteLockAPITest.gender);
            lock.readLock().unlock();



        }

    }

}
