package com.cornucopia.tc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 16.ArrayBlockingQueue
 * 16.1 阻塞队列，放入数据超过指定数量，会阻塞等待取走数据;取数据少于或等于0，会阻塞等待放入数据。
 * 16.2 阻塞队列相关的take,put等方法，相互之间互斥;put完毕之后，
 * 多线程take可以实现高效率消费共享数据。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final BlockingQueue queue = new ArrayBlockingQueue(3);
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep((long) (Math.random() * 100));
                            System.out.println(Thread.currentThread()
                                    .getName() + "开始放数据");
                            queue.put(1);
                            System.out.println(Thread.currentThread().getName()
                                    +"已经放了数据,"+"队列目前有"+queue.size()+"个数据");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+"线程准备取数据");
                        queue.take();
                        System.out.println(Thread.currentThread().getName()+"已经取了数据,"+
                                "队列目前有"+queue.size()+"个数据");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
