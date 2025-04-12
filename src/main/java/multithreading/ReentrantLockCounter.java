package multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCounter implements SiteVisitCounter {
    private int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    @Override
    public void incrementVisitCount() throws InterruptedException {
        lock.lock();
        try {
            counter++;
            Thread.sleep(100);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getVisitCount() {
        lock.lock();
        try {
            return counter;
        } finally {
            lock.unlock();
        }
    }
}
