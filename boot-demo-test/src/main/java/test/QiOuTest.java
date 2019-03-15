package test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ddmc
 * @Create 2019-03-13 9:53
 */
public class QiOuTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicInteger count = new AtomicInteger(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (true) {

                        while (count.get() % 2 != 0) {
                            condition.await();
                        }
                        if (count.getAndIncrement() < 100) {
                            System.out.println(Thread.currentThread().getName() + "; " + (count.get() - 1));
                            condition.signalAll();
                        } else {
                            System.out.println("-----------------------------" + Thread.currentThread().getName());
                            condition.signalAll();
                            break;
                        }
//                        if (count.get() >= 100) break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    while (true) {

                        while (count.get() % 2 != 1) {
                            condition.await();
                        }
                        if (count.getAndIncrement() < 100) {
                            System.out.println(Thread.currentThread().getName() + "; " + (count.get() - 1));
                            condition.signalAll();
                        } else {
                            System.out.println("-----------------------------" + Thread.currentThread().getName());
                            condition.signalAll();
                            break;
                        }
//                        if (count.get() >= 100) break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }
}