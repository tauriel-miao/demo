package com.tauriel.demo.concurrent_demo.synchronized_static_demo;

/**
 * synchronized的原理是什么，一般用在什么地方(比如加在静态方法和非静态方法的区别，静态方法和非静态方法同时执行的时候会有影响吗
 */
public class SynchronizedStatic {
    synchronized public static void printA(){
        try {
            System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 进入pringA()");
            Thread.sleep(3000);
            System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 离开pringA()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static void printB(){
        try {
            System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 进入printB()");

            System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 离开printB()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//  synchronized public  void printC(){
//      try {
//          System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 进入printC()");
//
//          System.out.println("线程名称为:"+Thread.currentThread().getName()+"在  "+System.currentTimeMillis()+" 离开printC()");
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }


    public static void main(String[] args) {
        SynchronizedStatic s=new SynchronizedStatic();
        ThreadA a=new ThreadA(s);
        a.setName("A");
        a.start();

        ThreadB b=new ThreadB(s);
        b.setName("B");
        b.start();
//
//      ThreadC c=new ThreadC(s);
//      c.setName("C");
//      c.start();

    }
}

class ThreadA extends Thread{
    private SynchronizedStatic s;

    public ThreadA(SynchronizedStatic s) {
        super();
        this.s = s;
    }
    @Override
    public void run() {
        s.printA();
    }
}


class ThreadB extends Thread{
    private SynchronizedStatic s;

    public ThreadB(SynchronizedStatic s) {
        super();
        this.s = s;
    }
    @Override
    public void run() {
        s.printB();
    }
}

