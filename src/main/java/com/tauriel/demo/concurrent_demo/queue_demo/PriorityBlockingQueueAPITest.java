package com.tauriel.demo.concurrent_demo.queue_demo;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 如果从一个 PriorityBlockingQueue 获得一个 Iterator 的话，该 Iterator 并不 能保证它对元素的遍历是以优先级为序的
 */
public class PriorityBlockingQueueAPITest {

    /**
     * add(), put(), offer(), offer(e, timeout, unit)    添加
     * 每次添加时都需要与queue中最后一个元素及他的所有父节点进行比较
     */
    @Test
    public void offerTest(){
        //在没有比较器得情况下, 添加时默认按最小堆排序
        //优先队列初始大小为11，会不断扩容，所以不存在阻塞
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(9);
        queue.put(28);
        queue.offer(7);
        queue.offer(5);
        queue.offer(36);
        queue.offer(3);

        System.out.println(queue.toString());

    }

    /**
     * poll(), remove()     获取数据后，队列需要重新按照最大堆/最小堆的排序方式排序
     * poll(timeout, timeunit), take() -->  阻塞(其中poll超时解除阻塞)
     * @throws InterruptedException
     */
    @Test
    public void pollTest() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(9);
        queue.put(28);
        queue.offer(7);
        System.out.println("queue = " + queue);
        queue.offer(5);

        System.out.println(queue.toString());

        Integer int1 = queue.poll();
        System.out.println("int1 = " + int1 + " , queue = " + queue);
        Integer int2 = queue.poll(4, TimeUnit.NANOSECONDS);
        System.out.println("int2 = " + int2 + " , queue = " + queue);
        Integer int3 = queue.take();
        System.out.println("int3 = " + int3 + " , queue = " + queue);
        Integer int4 = queue.remove();
        System.out.println("int4 = " + int4 + " , queue = " + queue);

        Integer int6 = queue.poll(4, TimeUnit.NANOSECONDS);
        System.out.println("int6 = " + int6 + " , queue = " + queue);
        Integer int5 = queue.poll();
        System.out.println("int5 = " + int5 + " , queue = " + queue);
    }

    /**
     * peek() 查询第一个元素
     */
    @Test
    public void peekTest(){

        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        queue.add(9);
        queue.put(28);
        queue.offer(7);
        queue.offer(5);

        System.out.println("peek() -->" + queue.peek());
        System.out.println("peek() -->" + queue.peek());
        System.out.println("queue = " + queue);
    }

}
