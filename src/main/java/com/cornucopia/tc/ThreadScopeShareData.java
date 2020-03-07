package com.cornucopia.tc;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 5.线程内数据共享
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class ThreadScopeShareData {
    private static Map<Thread, Integer> map = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    map.put(Thread.currentThread(), data);
                    System.out.println(Thread.currentThread().getName() + " has put data:" + data);
                    new A().get();
                    new B().get();
                }
            }).start();
        }

    }

    static class A {
        public void get() {
            int data = map.get(Thread.currentThread());
            System.out.println("A from " + Thread.currentThread()
                    .getName() + " get data :" + data);
        }
    }

    static class B {
        public void get() {
            int data = map.get(Thread.currentThread());
            System.out.println("B from " + Thread.currentThread()
                    .getName() + " get data :" + data);
        }
    }
}
