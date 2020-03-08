package com.cornucopia.tc.interview.one;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 1.并发题1
 * 1.1 题目描述:
 * 模拟处理16个行日志，单线程处理需要16s;要求开启四个线程4s让16个对象4s处理完成。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class QuestionOneOfBlockQueue {

    static BlockingQueue<String> blockingQueue =
            new ArrayBlockingQueue<String>(16);

    public static void main(String[] args) {
        System.out.println("begin:" + System.currentTimeMillis() / 1000);

        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while(true){
                        try {
                            String log = blockingQueue.take();
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }


        for (int i = 0; i < 16; i++) {//不能改动
            final String log = "" + (i + 1);//不能改动
            try {
                blockingQueue.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    //parseLog方法内部不能改动
    public static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
