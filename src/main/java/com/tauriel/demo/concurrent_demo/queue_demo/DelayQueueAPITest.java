package com.tauriel.demo.concurrent_demo.queue_demo;


import org.junit.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 数据结构
 *
 * ReentrantLock lock  可重入锁
 * PriorityQueue<E> q 存储元素的优先级队列
 * Thread leader /获取数据 等待线程标识
 * Condition available 条件控制，表示是否可以从队列中取数据
 */
public class DelayQueueAPITest{

    /**
     * add(a), offer(a), put(a), offer(a, timeout, timeunit)   底层代码调用一致
     */
    @Test
    public void addTest(){
        DelayQueue<Student> queue = new DelayQueue<Student>();
        Integer random = (int) (Math.random() * 100);
        for (int i = 0; i < 3; i++) {
            Student student = new Student("Student" + i, random);
            queue.put(student);
        }
        System.out.println(queue.size());
    }

    /**
     *  poll(a) 检索并删除，若当前队列无数据或无满足到期时间的数据，则返回null
     *  poll(timeout, unit) 获取并移除此队列的头元素，可以在指定的等待时间前等待可用的元素，timeout表明放弃之前要等待的时间长度，用 unit 的时间单位表示，如果在元素可用前超过了指定的等待时间，则返回null，当等待时可以被中断
     *  take(a) 若当前队列无数据或无满足到期时间的数据，则阻塞
     *  remove(a) true(成功),false（失败）  for循环找位置后队列数据前后移动
     *  removeEQ(a) iter循环删除  返回值void
     */
    @Test
    public void pollTest(){
        DelayQueue<Student> queue = new DelayQueue<Student>();
        for (int i = 0; i < 3; i++) {
            Integer random = (int) (Math.random() * 10000);
            System.out.println(random);
            Student student = new Student("Student" + i, random);
            queue.put(student);
        }
        System.out.println(queue.poll());
        try {
            System.out.println(queue.poll(40, TimeUnit.NANOSECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.poll());
        try {
            System.out.println(queue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * peek(a)  检索
     */
    @Test
    public void peekTest(){
        DelayQueue<Student> queue = new DelayQueue<Student>();
        for (int i = 0; i < 3; i++) {
            Integer random = (int) (Math.random() * 10000);
            System.out.println(random);
            Student student = new Student("Student" + i, random);
            queue.put(student);
        }
        System.out.println(queue.peek().getName());
        System.out.println(queue.size());
    }

}
