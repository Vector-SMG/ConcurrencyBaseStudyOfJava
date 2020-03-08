package com.cornucopia.tc.interview.three;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 3.面试题3
 * 3.1 题目描述:
 * 当每个线程中指定的key相等，这些相等的key应该隔1s依次输出时间值,key不同的则并行执行。
 * 3.2 题目重点
 *    3.2.1 String a="1"+"";
 *          String b="1"+"";
 *          由于编译器优化，a和b是一个对象，所以a和b能互斥
 *
 *    3.2.2 String key="";
 *          String a="1"+key;
 *          String b="1"+key;
 *          a和b由变量组成，编译器不优化，所以a和b不是一个对象，所以a和b不互斥
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/8
 */
//不能改动QuestionThree
public class QuestionThree extends Thread {

    private TestDo testDo;
    private String key;
    private String value;

    public QuestionThree(String key, String key2, String value) {
        this.testDo = TestDo.getInstance();
        this.key = key + key2;
        this.value = value;
    }

    public static void main(String[] args) {
        QuestionThree a = new QuestionThree("4", "", "1");
        QuestionThree b = new QuestionThree("1", "", "2");
        QuestionThree c = new QuestionThree("3", "", "3");
        QuestionThree d = new QuestionThree("1", "", "4");
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        a.start();
        b.start();
        c.start();
        d.start();
    }

    @Override
    public void run() {
        testDo.doSome(key, value);
    }

}

class TestDo {

    private BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<Object>(4);

    private TestDo() {
    }

    private static TestDo instance = new TestDo();


    public static TestDo getInstance() {
        return instance;
    }


    public void doSome(Object key, String value) {
        if (blockingQueue.contains(key))
        //不能改动下述代码块
        {
            try {
                Thread.sleep(1000);
                System.out.println(key + ":" + value + ":" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(key + ":" + value + ":" + System.currentTimeMillis());
        }
        try {
            blockingQueue.put(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
