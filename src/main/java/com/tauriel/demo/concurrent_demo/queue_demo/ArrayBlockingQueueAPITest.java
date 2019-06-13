package com.tauriel.demo.concurrent_demo.queue_demo;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueAPITest {

    /**
     * add(), offer()   添加
     * put(), offer(a,timeout,unit) --> 超时false  阻塞添加
     */
    @Test
    public void offerTest(){
        try {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
            queue.add(1);
            queue.offer(3);
            queue.put(2);
            System.out.println(queue);
            queue.offer(4);// false
            System.out.println(queue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * poll(a,timeout,unit) --> 超时为null, take()  阻塞的获取队列的值
     * poll(), remove()
     */
    @Test
    public void pollTest(){
        try {
            ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
            queue.add(1);
            queue.offer(3);
            queue.put(2);
            System.out.println(queue);
            Integer res1 = queue.poll();
            Integer res2 = queue.take();
            Integer res3 = queue.poll();
            Integer res4 = queue.poll(100, TimeUnit.NANOSECONDS);
            System.out.println(res1);
            System.out.println(res2);
            System.out.println(res3);
            System.out.println(res4);
            System.out.println(queue.take());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
