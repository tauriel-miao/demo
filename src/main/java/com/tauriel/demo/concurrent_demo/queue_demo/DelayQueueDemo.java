package com.tauriel.demo.concurrent_demo.queue_demo;


import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        int studentNumber = 20;
        CountDownLatch countDownLatch = new CountDownLatch(studentNumber+1);
        DelayQueue< Student> students = new DelayQueue<Student>();
        Random random = new Random();
        for (int i = 0; i < studentNumber; i++) {
            students.put(new Student("student"+(i+1), 30+random.nextInt(120),countDownLatch));
        }

        Thread teacherThread =new Thread(new Teacher(students));
        students.put(new EndExam(students, 120,countDownLatch,teacherThread));
        teacherThread.start();
        countDownLatch.await();
        System.out.println(" 考试时间到，全部交卷！");
    }

}


class Student implements Runnable,Delayed {

    private String name;
    private long workTime;
    private long submitTime;
    private boolean isForce = false;
    private CountDownLatch countDownLatch;

    public Student(){}

    public Student(String name, long workTime){
        this.name = name;
        this.workTime = workTime;
    }

    public Student(String name,long workTime,CountDownLatch countDownLatch){
        this.name = name;
        this.workTime = workTime;
        this.submitTime = TimeUnit.NANOSECONDS.convert(workTime, TimeUnit.NANOSECONDS)+System.nanoTime();
        this.countDownLatch = countDownLatch;
        System.out.println("Student ~~ " + Thread.currentThread().getName());
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == null || ! (o instanceof Student)) return 1;
        if(o == this) return 0;
        Student s = (Student)o;
        if (this.workTime > s.workTime) {
            return 1;
        }else if (this.workTime == s.workTime) {
            return 0;
        }else {
            return -1;
        }
    }

    /**
     * 这个方法返回到激活日期的剩余时间，时间单位由单位参数指定
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        // TODO Auto-generated method stub
        long convert = unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        System.out.println("convert = " + convert + " ,  studentName = " + name);
        return convert;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (isForce) {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 120分钟" );
        }else {
            System.out.println(name + " 交卷, 希望用时" + workTime + "分钟"+" ,实际用时 "+workTime +" 分钟");
        }
        countDownLatch.countDown();
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean isForce) {
        this.isForce = isForce;
    }

}

class EndExam extends Student{

    private DelayQueue<Student> students;
    private CountDownLatch countDownLatch;
    private Thread teacherThread;

    public EndExam(DelayQueue<Student> students, long workTime, CountDownLatch countDownLatch,Thread teacherThread) {
        super("强制收卷", workTime,countDownLatch);
        this.students = students;
        this.countDownLatch = countDownLatch;
        this.teacherThread = teacherThread;
        System.out.println("Student ~~ " + Thread.currentThread().getName() + " , 强制收卷");
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub

        teacherThread.interrupt();
        Student tmpStudent;
        for (Iterator<Student> iterator2 = students.iterator(); iterator2.hasNext();) {
            tmpStudent = iterator2.next();
            tmpStudent.setForce(true);
            tmpStudent.run();
        }
        countDownLatch.countDown();
    }

}

class Teacher implements Runnable{

    private DelayQueue<Student> students;
    public Teacher(DelayQueue<Student> students){
        this.students = students;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println(" test start");
            while(!Thread.interrupted()){
                System.out.println(students);
                students.take().run();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}