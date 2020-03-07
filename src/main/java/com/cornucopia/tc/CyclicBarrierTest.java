package com.cornucopia.tc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 13.CyclicBarrier
 * 13.1 作用:指定一定数量线程，当线程都到达`await`才执行下一步。如此循环
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cb = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));

                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点1,当前已有" + (cb.getNumberWaiting() + 1));
                        cb.await();

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点2,当前已有" + (cb.getNumberWaiting() + 1));
                        cb.await();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程" + Thread.currentThread().getName()
                                + "即将到达集合地点3,当前已有" + (cb.getNumberWaiting() + 1));
                        cb.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            service.execute(runnable);
        }
    }
}
