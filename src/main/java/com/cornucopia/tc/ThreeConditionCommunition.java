package com.cornucopia.tc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 11.condition线程间通信示例二
 *
 * 11.1 使用三个condition实现:三个线程互斥串行执行三段代码.
 * 11.2 实现总结:
 *      11.2.1 将线程间通信的相关数据封装在一个对象的成员变量中，将相关操作封装在方法中。这样
 *             利用实例对象作为天然的锁，达到容易理解，高类聚的目的。
 *      11.2.2 在需要互斥的逻辑代码块加入互斥。
 *      11.2.3 不同线程执行不同的代码逻辑，实现互斥，通信:
 *             11.2.3.1 不满足当前代码块的执行条件，则等待。
 *             11.2.3.2 执行代码。
 *             11.2.3.3 修改条件判断的变量，进入其他代码逻辑，并唤醒。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class ThreeConditionCommunition {

    public static void main(String[] args) {
        new ThreeConditionCommunition().init();
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
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        private int status = 1;//1,执行第一个线程;2，执行第二个线程;3，执行第三个线程

        public void one(int i) {
            lock.lock();
            try {
                while (status != 1) {
                    try {
                        condition1.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 1; j++) {
                    System.out.println("one thread sequece of " + i + ",loop of " + j);
                }
                status = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }

        }

        public void two(int i) {
            lock.lock();
            try {
                while (status != 2) {
                    try {
                        condition2.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 2; j++) {
                    System.out.println("two thread sequece of " + i + ",loop of " + j);
                }
                status = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }

        public void three(int i) {
            lock.lock();
            try {
                while (status!=3) {
                    try {
                        condition3.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 3; j++) {
                    System.out.println("three thread sequece of " + i + ",loop of " + j);
                }
                status = 1;
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
