package com.cornucopia.tc;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 2.定时器
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/4
 */
public class TraditionalTimer {

    public static void main(String[] args) {
        //延迟1s，然后每3s执行
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("bombing!!!");
            }
        },1000,3000);

//        while(true){
//            System.out.println(new Date().getSeconds());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
