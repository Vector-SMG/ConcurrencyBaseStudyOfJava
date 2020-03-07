package com.cornucopia.tc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 12.信号灯
 * 12.1 可以控制同时访问资源的个数，并提供同步机制。
 * 12.2 Semaphore类的fair，表示是否公平。公平，先进来，先拿到。
 * 12.3 lock是自己释放灯，Semaphore可以让别人释放灯。
 * 12.4 单个信号量的Semaphore对象实现互斥锁的功能，并且可以由一个线程获得锁，
 * 再由另外一个线程释放锁，可以应用于死锁恢复的一些场合。
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService service=Executors.newCachedThreadPool();
        final Semaphore sp=new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            Runnable runnable=new Runnable() {
                public void run() {
                    try {
                        sp.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()
                    +"进入，当前已有"+(3-sp.availablePermits())+"个线程");
                    try {
                        Thread.sleep((long)(Math.random()*10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()
                            +"即将离开");
                    sp.release();
                    System.out.println("线程"+Thread.currentThread().getName()
                            +"已离开，当前已有"+(3-sp.availablePermits())+"个并发");
                }
            };
            service.execute(runnable);
        }

    }
}
