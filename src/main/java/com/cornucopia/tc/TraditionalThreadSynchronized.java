package com.cornucopia.tc;

/**
 * 3.传统线程互斥
 *   3.1.synchronized修饰非静态方法，锁:实例对象
 *   3.2.synchronized修饰静态方法，锁:类字节码对象
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/4
 */
public class TraditionalThreadSynchronized {

    Object object = new Object();

    public static void main(String[] args) {
        new TraditionalThreadSynchronized().init();
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

    static class Outputer {
        public synchronized void output(String name) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
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
