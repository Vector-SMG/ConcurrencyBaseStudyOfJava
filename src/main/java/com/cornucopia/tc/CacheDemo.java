package com.cornucopia.tc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 9.读写锁实现缓存示例
 * 9.1 读写锁规则:读锁之间不互斥，读写锁之间互斥，写锁和写锁之间互斥.
 * 9.2 缓存示例解释:先上读锁，然后上写锁，然后判断写入是否有效，如果有效则不再写入，最后解写锁。读的效率高
 * 9.3 官方示例:
 * class CachedData {
 *    Object data;
 *    volatile boolean cacheValid;
 *    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
 *
 *    void processCachedData() {
 *      rwl.readLock().lock();
 *      if (!cacheValid) {
 *        // Must release read lock before acquiring write lock
 *        rwl.readLock().unlock();
 *        rwl.writeLock().lock();
 *        try {
 *          // Recheck state because another thread might have
 *          // acquired write lock and changed state before we did.
 *          if (!cacheValid) {
 *            data = ...
 *            cacheValid = true;
 *          }
 *          // Downgrade by acquiring read lock before releasing write lock
 *          rwl.readLock().lock();
 *        } finally {
 *          rwl.writeLock().unlock(); // Unlock write, still hold read
 *        }
 *      }
 *
 *      try {
 *        use(data);
 *      } finally {
 *        rwl.readLock().unlock();
 *      }
 *    }
 *  }
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/6
 */
public class CacheDemo {

    private Map<String, Object> cache = new HashMap<String, Object>();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public Object getData(String key) {
        rwl.readLock().lock();
        Object value = null;
        try {
            value = cache.get(key);
            if (value == null) {//1.
                rwl.readLock().unlock();//2.
                rwl.writeLock().lock();
                try {
                    if (value == null) {//A线程执行3，B线程执行到2,这时候如果不做判断，
                        // 它可以继续进行写，实际不需要写第二次
                        value = "aaaa";
                    }
                } finally {
                    rwl.writeLock().unlock();//3.
                }
                rwl.readLock().lock();
            }
        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }

    public static void main(String[] args) {

    }

//    public synchronized Object getData(String key) {
//        Object value = cache.get(key);
//        if (value == null) {
//            value = "aaaa";//实际失去queryDB()
//        }
//        return value;
//    }


}
