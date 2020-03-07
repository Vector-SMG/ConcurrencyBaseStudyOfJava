package com.cornucopia.tc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3.lock实现线程互斥.
 * 3.1 lock比传统线程模型中的synchronized方式更加面向对象。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/4
 */
public class TraditionalThreadLockTest {

    Object object = new Object();

    public static void main(String[] args) {
        new TraditionalThreadLockTest().init();
    }

    public void init() {


        final Outputer outputer = new Outputer();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("abcdefg");
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("1234567");
                }
            }
        }).start();
    }

    /**
     * synchronized关键字
     * 1.synchronized修饰非静态方法，锁:实例对象
     * 2.synchronized修饰静态方法，锁:类字节码对象
     */
    static class Outputer {
        Lock lock = new ReentrantLock();

        public void output(String name) {
            lock.lock();
            try{
                int len = name.length();
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }finally {
                lock.unlock();
            }
        }

        public void output1(String name) {
            int len = name.length();
            synchronized (this) {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

        public synchronized void output2(String name) {
            int len = name.length();
            synchronized (this) {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

    }

}
