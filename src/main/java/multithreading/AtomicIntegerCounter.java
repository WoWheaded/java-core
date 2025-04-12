package multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCounter implements SiteVisitCounter {
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void incrementVisitCount() throws InterruptedException {
        counter.incrementAndGet();
        Thread.sleep(100);
    }

    @Override
    public int getVisitCount() {
        return counter.get();
    }
}
