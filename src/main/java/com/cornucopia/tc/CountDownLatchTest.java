package com.cornucopia.tc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 13.CountDownLatch
 * 13.1 计数器，当计数器countDown()到达零，则计数器await的代码被唤醒执行。
 * 13.2 代码模拟了下述应用场景:长跑比赛中，裁判吹口哨，多名运动员开始起跑，所有运动员都到达目的地后
 * ，裁判一一记录跑步成绩。裁判目的完成。
 * 13.3 可以实现多个人通知一个人，也可以一个人通知多个人
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder=new CountDownLatch(1);
        final CountDownLatch cdAnswer=new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()
                        +"正准备接受命令");
                        cdOrder.await();//计数器归零，则被唤醒
                        System.out.println("线程"+Thread.currentThread().getName()
                        +"已接受命令");
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()
                        +"回应命令处理结果");
                        cdAnswer.countDown();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            Thread.sleep((long)(Math.random()*10000));
            System.out.println("线程"+Thread.currentThread().getName()
                    +"即将发布命令");
            cdOrder.countDown();
            System.out.println("线程"+Thread.currentThread().getName()
                    +"已发送命令，正在等待结果");
            cdAnswer.await();
            System.out.println("线程"+Thread.currentThread().getName()
                    +"已收到所有响应结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
