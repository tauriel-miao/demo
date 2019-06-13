package cn.tarena.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试栅栏的
 * 赛马案例：
 * 两匹赛马（两个线程），要等到所有赛马都到达栅栏前，才能一起跑
 *
 * @author ysq
 *
 */
public class CountDownLatchAPITest {

	public static void main(String[] args) {
		CyclicBarrier cb=new CyclicBarrier(2);

		new Thread(new Horse1Runner(cb)).start();
		new Thread(new Horse2Runner(cb)).start();
	}
}
class Horse1Runner implements Runnable{

	private CyclicBarrier cb;

	public Horse1Runner(CyclicBarrier cb) {
		this.cb=cb;
	}

	public void run() {
		System.out.println("赛马1来到栅栏前");
		try {
			//await()会产生阻塞，阻塞放开的条件是初始计数器减为0。
			//此外，每调一次await()方法，计数器-1
			cb.await();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (BrokenBarrierException e) {

			e.printStackTrace();
		}
		System.out.println("赛马1开始跑");

	}

}
class Horse2Runner implements Runnable{

	private CyclicBarrier cb;
	public Horse2Runner(CyclicBarrier cb) {
		this.cb=cb;
	}

	public void run() {
		System.out.println("赛马2正在拉肚子");
		try {
			Thread.sleep(3000);
			System.out.println("赛马2来到栅栏前");
			cb.await();
			System.out.println("赛马2开始跑");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
