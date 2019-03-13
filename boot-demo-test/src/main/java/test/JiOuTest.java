package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ddmc
 * @Create 2019-03-13 9:53
 */
public class JiOuTest {
    public static void main(String[] args) {
        Demo demo = new Demo();
        Thread a = new Thread(demo, "A");
        Thread b = new Thread(demo, "B");
        a.start();
        b.start();

    }
}

class Demo implements Runnable {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    int count = 0;

    @Override
    public void run() {
        int div = Thread.currentThread().getName().equals("A") ? 0 : 1;
        lock.lock();
        try {
            while (count < 100) {

                while (count % 2 == div) {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    condition.signalAll();
                }
                if (count < 100) {
                    condition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

class Demo1 implements Runnable {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    int count = 0;

    @Override
    public void run() {
        int div = Thread.currentThread().getName().equals("A") ? 0 : 1;
        lock.lock();
        try {
            while (count < 100) {
                while (count % 2 != div) {
                    condition.await();
                }
                if (count < 100) {

                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    condition.signalAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}