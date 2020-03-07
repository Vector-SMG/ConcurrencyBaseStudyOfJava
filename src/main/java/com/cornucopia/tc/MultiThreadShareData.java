package com.cornucopia.tc;

/**
 * 7.线程间共享数据
 * 7.1 如果线程执行的代码相同，可以使用同一个Runnable对象。
 * 7.2 如果线程执行的代码不同，将共享数据封装在另外一个对象中，将这个对象逐一传递给
 * 每个Runnable对象。每个线程对共享数据的操作就分配到了那个对象上完成。这样容易对该数据
 * 进行的各个操作进行互斥和通信。
 * 7.3 如果线程执行的代码不同，将Runnable作为外部类的内部类，将共享数据作为外部类的成员变量。
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/6
 */
public class MultiThreadShareData {

    public static void main(String[] args) {
        //1.
        final ShareData1 data1=new ShareData1();

        new Thread(data1).start();
        new Thread(data1).start();

        //2.将
        final ShareData2 data2=new ShareData2();
        new Thread(new MyRunnable1(data2)).start();
        new Thread(new MyRunnable2(data2)).start();

        //3.
        final ShareData2 shareData2=new ShareData2();
        new Thread(new Runnable() {
            public void run() {
                shareData2.increment();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                shareData2.decrement();
            }
        }).start();
    }


}

class ShareData1 implements Runnable{
    private int count=100;

    public void run() {
        while(true){
            count--;
        }
    }
}

class ShareData2{

    private int j = 0;

    public synchronized void increment() {
        j++;
    }

    public synchronized void decrement() {
        j--;
    }

}

class MyRunnable1 implements Runnable{
    private ShareData2 shareData2;

    public MyRunnable1(ShareData2 shareData2){
        this.shareData2=shareData2;
    }

    public void run() {
        this.shareData2.increment();
    }
}

class MyRunnable2 implements Runnable{
    private ShareData2 shareData2;

    public MyRunnable2(ShareData2 shareData2){
        this.shareData2=shareData2;
    }

    public void run() {
        this.shareData2.decrement();
    }
}
