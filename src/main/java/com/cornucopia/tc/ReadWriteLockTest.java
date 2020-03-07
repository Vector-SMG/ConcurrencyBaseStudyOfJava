package com.cornucopia.tc;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 8.读写锁
 * 8.1 读写锁，分为读锁和写锁，多个读锁不互斥，读锁与写锁互斥，写锁与写锁互斥。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/6
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        final Queue3 q3 = new Queue3();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        q3.get();
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    q3.put(new Random().nextInt(10000));
                }
            }.start();
        }
    }
}


class Queue3 {
    private Object data = null;
    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public void get() {
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to read data!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " has get data:" + data);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public void put(Object data) {
        rwl.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " be ready to write data!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " hava write data!");
        }finally {
            rwl.writeLock().unlock();
        }
     }
}