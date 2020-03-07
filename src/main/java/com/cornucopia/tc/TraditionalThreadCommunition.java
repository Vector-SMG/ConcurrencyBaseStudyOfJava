package com.cornucopia.tc;

/**
 * 4.线程间通信.
 * 4.1 将需要进行不同线程间通信的逻辑代码，尽量封装到一个类中管理，这些逻辑代码天然拥有类的实例作为锁，使得管理更加方便
 * ，这也体现了高类聚的原则。
 * 4.2 线程在`wait()`过程中，允许发生`虚假唤醒`,为了防止这种情况，while比if更加适用。
 *     synchronized(obj){
 *         while(<Condition does not hold>){
 *           obj.wait();
 *         }
 *     }
 * 4.3 调用`wait`和`notify`的对象必须时锁对象.
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class TraditionalThreadCommunition {

    public static void main(String[] args) {
        new TraditionalThreadCommunition().init();
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
        private boolean bShouldSub = true;

        public synchronized void sub(int i) {
            while (!bShouldSub) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 10; j++) {
                System.out.println("sub thread sequece of " + j + ",loop of " + j);
            }
            bShouldSub=false;
            this.notify();
        }

        public synchronized void main(int i) {
            while(bShouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 1; j <= 100; j++) {
                System.out.println("main thread sequece of " + j + ",loop of " + i);
            }
            bShouldSub=true;
            this.notify();
        }
    }

}
