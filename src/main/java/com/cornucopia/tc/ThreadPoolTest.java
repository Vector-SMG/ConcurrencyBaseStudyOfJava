package com.cornucopia.tc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 7.线程池
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/6
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
         //创建固定大小的线程池
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
         //创建缓存的线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool ();
         //创建一个线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor ();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " is loop of "+j
                                +" for task of "+ finalI);
                    }

                }
            });
        }
        //threadPool.shutdown();
        //threadPool.shutdownNow();//马上停止线程池
        //带定时执行的线程池
        Executors.newScheduledThreadPool(3).schedule(new Runnable() {
            public void run() {
                System.out.println("bombing!!!");
            }
        },10, TimeUnit.SECONDS);
    }
}
