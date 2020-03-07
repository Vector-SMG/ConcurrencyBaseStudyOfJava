package com.cornucopia.tc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 10.Condition实现线程间通信.
 * 10.1 阻塞队列，当队列长度为0，不允许取；当队列长度等于指定长度，不允许增。示例代码如下:
 *  class BoundedBuffer {
 *    final Lock lock = new ReentrantLock();
 *    final Condition notFull  = lock.newCondition();
 *    final Condition notEmpty = lock.newCondition();
 *
 *    final Object[] items = new Object[100];
 *    int putptr, takeptr, count;
 *
 *    public void put(Object x) throws InterruptedException {
 *      lock.lock();
 *      try {
 *        while (count == items.length)
 *          notFull.await();
 *        items[putptr] = x;
 *        if (++putptr == items.length) putptr = 0;
 *        ++count;
 *        notEmpty.signal();
 *      } finally {
 *        lock.unlock();
 *      }
 *    }
 *
 *    public Object take() throws InterruptedException {
 *      lock.lock();
 *      try {
 *        while (count == 0)
 *          notEmpty.await();
 *        Object x = items[takeptr];
 *        if (++takeptr == items.length) takeptr = 0;
 *        --count;
 *        notFull.signal();
 *        return x;
 *      } finally {
 *        lock.unlock();
 *      }
 *    }
 *  }
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class ConditionCommunition {

    public static void main(String[] args) {
        new ConditionCommunition().init();
    }

    public void init() {
        final Business business = new Business();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    business.sub(i);
                }
            }
        }).start();
        for (int i = 1; i <= 50; i++) {
            business.main(i);
        }
    }


    class Business {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        private boolean bShouldSub = true;

        public void sub(int i) {
            lock.lock();
            try {
                while (!bShouldSub) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 10; j++) {
                    System.out.println("sub thread sequece of " + j + ",loop of " + j);
                }
                bShouldSub = false;
                condition.signal();
            } finally {
                lock.unlock();
            }

        }

        public void main(int i) {
            lock.lock();
            try {
                while (bShouldSub) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 1; j <= 100; j++) {
                    System.out.println("main thread sequece of " + j + ",loop of " + i);
                }
                bShouldSub = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
