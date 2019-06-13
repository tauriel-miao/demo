package com.tauriel.demo.concurrent_demo.queue_demo;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingQueueAPITest {

    /**
     * LinkedBlockingDeque.take()
     *
     * 阻塞式获取值
     */
    @Test
    public  void testTake() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>();
        try {
            linkedBlockingDeque.put("haha");
            linkedBlockingDeque.put("enen");
            linkedBlockingDeque.put("oooo");
            linkedBlockingDeque.put("hehe");

            System.out.println("Before take... " + linkedBlockingDeque);
            String str1 = linkedBlockingDeque.take();
            System.out.println("str1:" + str1);

            String str2 = linkedBlockingDeque.take();
            System.out.println("str2:" + str2);

            String str3 = linkedBlockingDeque.take();
            System.out.println("str3:" + str3);

            String str4 = linkedBlockingDeque.take();
            System.out.println("str4:" + str4);

            System.out.println("After take..." + linkedBlockingDeque.take());
            System.out.println("enheng~~~");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * LinkedBlockingDeque.poll()
     *
     * 若拿不到返回 null
     */
    @Test
    public  void testPoll() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.offer("haha");
        linkedBlockingDeque.offer("hehe");
        linkedBlockingDeque.offer("xixi");

        System.out.println("Before offer... " + linkedBlockingDeque);
        String haha = linkedBlockingDeque.poll();
        String hehe = linkedBlockingDeque.poll();
        System.out.println("enheng~~~" + haha);
        System.out.println("enheng~~~" + hehe);
    }


    /**
     * LinkedBlockingDeque.put()
     *
     * 阻塞式放值
     */
    @Test
    public  void testPut() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);
        try {
            linkedBlockingDeque.put("haha");
            linkedBlockingDeque.put("enen");
            linkedBlockingDeque.put("oooo");

            System.out.println("Before put... " + linkedBlockingDeque);
            //阻塞
            //linkedBlockingDeque.put("hehe");
            //System.out.println("enheng~~~");

            Thread.sleep(1000);
            linkedBlockingDeque.take();
            System.out.println("After put... " + linkedBlockingDeque);

            linkedBlockingDeque.put("hehe");
            System.out.println("enheng~~~");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * LinkedBlockingDeque.offer()
     *
     * 返回ture, false
     */
    @Test
    public  void testOffer() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.offer("haha");
        linkedBlockingDeque.offer("enen");
        linkedBlockingDeque.offer("oooo");

        System.out.println("Before offer... " + linkedBlockingDeque);
        boolean haha = linkedBlockingDeque.offer("hehe");
        System.out.println("enheng~~~" + haha);
    }


    /**
     * LinkedBlockingDeque.push()
     *
     * push 相当于压栈，最后放进去的值在队列的第一位，队列满了报异常 --> IllegalStateException: Deque full
     */
    @Test
    public  void testPush() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.push("haha");
        linkedBlockingDeque.push("enen");
        linkedBlockingDeque.push("oooo");

        System.out.println("Before push... " + linkedBlockingDeque);
        linkedBlockingDeque.push("asfoooo");
        System.out.println("After push... " + linkedBlockingDeque);
    }

    /**
     * LinkedBlockingDeque.peek()
     *
     *	若队列中有数据则拿第一个，若没有数据返回null
     */
    @Test
    public  void testPeek() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.offer("haha");
        linkedBlockingDeque.offer("enen");

        System.out.println("Before offer... " + linkedBlockingDeque);

        String hehe = linkedBlockingDeque.peek();
        String ee= linkedBlockingDeque.peekFirst();
        String oo = linkedBlockingDeque.peekLast();

        System.out.println("enheng~~~" + hehe);
        System.out.println("enheng~~~" + ee);
        System.out.println("enheng~~~" + oo);
    }


    /**
     * LinkedBlockingDeque.add()
     *
     *	若队列中的容量已满后再add会抛异常 --> IllegalStateException: Deque full
     */
    @Test
    public  void testAdd() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.add("haha");
        linkedBlockingDeque.add("enen");
        linkedBlockingDeque.add("oo");

        System.out.println("Before add ... " + linkedBlockingDeque);

        String hehe = linkedBlockingDeque.peek();
        String ee= linkedBlockingDeque.peek();
        String oo = linkedBlockingDeque.peek();

        System.out.println("enheng~~~" + hehe);
        System.out.println("enheng~~~" + ee);
        System.out.println("enheng~~~" + oo);

        linkedBlockingDeque.add("again");
        System.out.println("After add ... " + linkedBlockingDeque);
    }


    /**
     * LinkedBlockingDeque.remove()  、 pop()
     * remove(a)  遍历remove元素
     *
     *	若队列中有数据则拿第一个，若没有数据抛异常 --> NoSuchElementException
     */
    @Test
    public  void testRemove() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.add("haha");
        linkedBlockingDeque.add("enen");
        linkedBlockingDeque.add("oo");

        System.out.println("Before add ... " + linkedBlockingDeque);

        String hehe = linkedBlockingDeque.poll();
        String ee= linkedBlockingDeque.remove();
        String oo = linkedBlockingDeque.remove();

        System.out.println("enheng~~~" + hehe);
        System.out.println("enheng~~~" + ee);
        System.out.println("enheng~~~" + oo);

        //String xixi= linkedBlockingDeque.remove();
        //System.out.println("enheng~~~" + xixi);
        System.out.println("After add ... " + linkedBlockingDeque);
    }


    /**
     * LinkedBlockingDeque.element()
     *
     *	若队列中有数据则拿第一个，若没有数据抛异常 --> NoSuchElementException
     */
    @Test
    public  void testElement() {

        LinkedBlockingDeque<String> linkedBlockingDeque = new LinkedBlockingDeque<String>(3);

        linkedBlockingDeque.add("haha");
        linkedBlockingDeque.add("enen");
        linkedBlockingDeque.add("oo");

        System.out.println("Before add ... " + linkedBlockingDeque);

        String hehe = linkedBlockingDeque.poll();

        System.out.println("enheng~~~" + hehe);

        String xixi= linkedBlockingDeque.element();
        System.out.println("enheng~~~" + xixi);
        System.out.println("After add ... " + linkedBlockingDeque);
    }
}
