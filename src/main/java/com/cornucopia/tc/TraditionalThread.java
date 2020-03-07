package com.cornucopia.tc;

/**
 * 1. 传统线程回顾
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/3
 */
public class TraditionalThread {

    public static void main(String[] args) {

        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    System.out.println(Thread.currentThread().getName());
                }
            }
        };
        thread.start();

        Thread thread2=new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    System.out.println(Thread.currentThread().getName());
                }
            }
        });
        thread2.start();

    }
}
