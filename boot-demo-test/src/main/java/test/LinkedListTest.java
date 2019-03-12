package test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author ddmc
 * @Create 2019-03-11 18:16
 */
public class LinkedListTest {

    public static void main1(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
//        for (int i = 0; i < 100; i++) {
//            boolean result = false;
//            try {
//                result = queue.add(String.valueOf(i + 1));
//                System.out.println("add element result: " + result);
//            } catch (Exception e) {
//                System.out.println("add element result: " + result);
//            }
//        }

        for (int i = 0; i < 100; i++) {
            boolean result = false;
            try {
                result = queue.offer(String.valueOf(i + 1));
                System.out.println("offer element result: " + result);
            } catch (Exception e) {
                System.out.println("offer element result: " + result);
            }
        }


    }

    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(5);
        System.out.println(num.getAndIncrement());
        System.out.println(num.get());
        System.out.println(num.compareAndSet(6, 8));
        System.out.println(num.get());
    }
}