package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCThread implements Runnable {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    int size = 3;
    int group = 10;
    int count = 0;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        lock.lock();

        try {

            for (int i = 0; i < group; i++) {
                if ("A".equals(name)) {
                    while (count % size != 0) {
                        condition.await();
                    }
                } else if ("B".equals(name)) {
                    while (count % size != 1) {
                        condition.await();
                    }
                } else {
                    while (count % size != 2) {
                        condition.await();
                    }
                }
                count++;
                System.out.print(name);
                condition.signalAll();// 通知正在等待的线程，此时有可能已经满足条件
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 记得要释放锁
        }
    }

    public static void main(String[] args) {
        PrintABCThread abcRunnable = new PrintABCThread();
        new Thread(abcRunnable, "A").start();
        new Thread(abcRunnable, "B").start();
        new Thread(abcRunnable, "C").start();
    }
}