package com.cornucopia.tc.interview.two;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 2.面试题2
 * 2.1 题目描述:
 * 有10条数据，需要处理。增加10个线程，依次按顺序串行处理这10条数据。
 * 2.2 解题思路:
 * 使用阻塞队列容量为1先put,阻塞其他线程进入，等待执行完，再take，让其他线程进入。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/8
 */
public class QuestionTwo {

    static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(1);

    public static void main(String[] args) {
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        for (int i = 0; i < 10; i++) {//不能改动
            final String input = i + "";//不能改动
            new Thread(new Runnable() {
                public void run() {
                    try {
                        blockingQueue.put(input);
                        String output = TestDo.doSome(input);
                        System.out.println(Thread.currentThread().getName() + ":" + output);
                        blockingQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}

//不能改动TestDo类
class TestDo {

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


