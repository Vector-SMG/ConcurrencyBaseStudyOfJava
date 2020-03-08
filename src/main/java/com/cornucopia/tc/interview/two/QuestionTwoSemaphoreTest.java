package com.cornucopia.tc.interview.two;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * 2.面试题2
 * 2.1 题目描述:
 * 有10条数据，需要处理。增加10个线程，依次按顺序串行处理这10条数据。
 * 2.2 解题思路：
 * 使用Semaphore信号灯，控制同时访问线程为1，并阻塞线程，当处理完毕，然后再release让其他线程进入。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/8
 */
public class QuestionTwoSemaphoreTest {
    static Semaphore semaphore = new Semaphore(1);
    static BlockingQueue<String> synchronousQueue = new SynchronousQueue<String>();

    public static void main(String[] args) {
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        String input = synchronousQueue.take();
                        String output = TestDoCopy.doSome(input);
                        System.out.println(Thread.currentThread().getName() + ":" + output);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {//不能改动
            final String input = i + "";//不能改动
            try {
                synchronousQueue.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}

//不能改动TestDo类
class TestDoCopy {

    public static String doSome(String input) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String output = input + ":" + (System.currentTimeMillis() / 1000);
        return output;
    }

}


