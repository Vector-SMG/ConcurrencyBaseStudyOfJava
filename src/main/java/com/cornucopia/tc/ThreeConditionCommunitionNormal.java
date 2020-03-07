package com.cornucopia.tc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 10.condition线程间通信示例一
 *
 * 10.1 使用一个condition实现:三个线程互斥串行执行三段逻辑代码
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class ThreeConditionCommunitionNormal {

    public static void main(String[] args) {
        new ThreeConditionCommunitionNormal().init();
    }

    public void init() {
        final Business business = new Business();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    business.one(0);
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    business.two(1);
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    business.three(2);
                }
            }).start();
        }

    }


    class Business {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        private int status = 0;//0,执行第一个线程;1，执行第二个线程;2，执行第三个线程

        public void one(int i) {
            lock.lock();
            try {
                while (status == 1 || status == 2) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 1; j++) {
                    System.out.println("one thread sequece of " + i + ",loop of " + j);
                }
                status = 1;
                condition.signal();
            } finally {
                lock.unlock();
            }

        }

        public void two(int i) {
            lock.lock();
            try {
                while (status == 0 || status == 2) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 2; j++) {
                    System.out.println("two thread sequece of " + i + ",loop of " + j);
                }
                status = 2;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void three(int i) {
            lock.lock();
            try {
                while (status == 0 || status == 1) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 3; j++) {
                    System.out.println("three thread sequece of " + i + ",loop of " + j);
                }
                status = 0;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
