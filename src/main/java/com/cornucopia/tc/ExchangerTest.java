package com.cornucopia.tc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 15.Exchanger
 * 15.1 解决不同线程之间的数据交换问题。
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger exchanger = new Exchanger();
        service.execute(new Runnable() {
            public void run() {
                try {
                    String data1 = "123";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正把数据:" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程"+Thread.currentThread().getName()+"换回的数据:"+data2);
                } catch (Exception e) {

                }
            }
        });
        service.execute(new Runnable() {
            public void run() {
                try {
                    String data1 = "abc";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正把数据:" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程"+Thread.currentThread().getName()+"换回的数据:"+data2);
                } catch (Exception e) {

                }
            }
        });
    }
}
