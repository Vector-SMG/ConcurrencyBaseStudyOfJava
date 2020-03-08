package com.cornucopia.tc;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 18.并发集合库
 *
 * @author cornucopia
 * @version 1.0
 * @since 2020/3/7
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
//      List<String> list = new ArrayList<String>();
        List<String> list = new CopyOnWriteArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if(value.equals("1")){
                //当ArrayList边遍历边remove时，
                //可能出现`java.util.ConcurrentModificationException`
                list.remove(value);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("遍历值:"+list.get(i));
        }
    }
}
