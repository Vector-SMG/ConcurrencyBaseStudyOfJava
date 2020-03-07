package com.cornucopia.tc;

import java.util.Random;

/**
 * 6.基于ThreadLocal的线程内数据共享.
 * 6.1 数据结构:ThreadLocal是基于键值为当前线程的map数据.
 * 6.2 使用技巧:线程内数据共享，可以使用ThreadLocal将数据和当前线程绑定，另外，
 * 我们可以将ThreadLocal的使用封装到数据类中，对外隐藏了技术细节，优化了使用体验。
 * 6.3 优化策略:线程结束后，应该移除线程中ThreadLocal中的remove方法。
 * 6.4 todo 如何拿到线程结束的通知?
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/5
 */
public class ThreadLocalTest {

    static ThreadLocal<Integer> x = new ThreadLocal<Integer>();

    public static void main(String[] args) {
//        for (int i = 0; i < 2; i++) {
//            new Thread(new Runnable() {
//                public void run() {
//                    int data = new Random().nextInt();
//                    x.set(data);
//                    System.out.println(Thread.currentThread().getName() + " has put data:" + data);
//                    new ThreadLocalTest.A().get();
//                    new ThreadLocalTest.B().get();
//                }
//            }).start();
//        }

        for (int j = 0; j < 2; j++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    Person.getThreadLocalInstance().setAge(data);
                    Person.getThreadLocalInstance().setName(""+data);

                    new ThreadLocalTest.C().get();
                    new ThreadLocalTest.D().get();
                }
            }).start();
        }


    }

    static class A {
        public void get() {
            int data = x.get();
            System.out.println("A from " + Thread.currentThread()
                    .getName() + " get data :" + data);
        }
    }

    static class B {
        public void get() {
            int data = x.get();
            System.out.println("B from " + Thread.currentThread()
                    .getName() + " get data :" + data);
        }
    }

    static class C {
        public void get() {
            Person instance=Person.getThreadLocalInstance();
            System.out.println("A from " + Thread.currentThread()
                    .getName() + " get name:" + instance.getName()
            +" get age:"+instance.getAge());
        }
    }

    static class D {
        public void get() {
            Person instance=Person.getThreadLocalInstance();
            System.out.println("B from " + Thread.currentThread()
                    .getName() + " get name:" + instance.getName()
                    +" get age:"+instance.getAge());
        }
    }

    static class Person {
        private static Person instance = null;
        //ThreadLocal，可以保证每个线程取自己线程的数据，所以不需要synchorized
        private static ThreadLocal<Person> map = new ThreadLocal();
        private String name;
        private int age;

        private Person() {
        }

        public static Person getThreadLocalInstance() {
            Person instance = map.get();
            if (instance == null) {
                instance = new Person();
                map.set(instance);
            }
            return instance;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
