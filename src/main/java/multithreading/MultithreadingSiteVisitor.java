package multithreading;

import java.util.ArrayList;
import java.util.List;

public class MultithreadingSiteVisitor {
    private final SiteVisitCounter siteVisitCounter;
    private final List<Thread> threads = new ArrayList<>();
    private static long startTime;
    private static long endTime;

    public MultithreadingSiteVisitor(SiteVisitCounter siteVisitCounter) {
        this.siteVisitCounter = siteVisitCounter;
    }

    public void visitMultithread(int numOfThreads) {
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    siteVisitCounter.incrementVisitCount();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void waitUntilAllVisited() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
        endTime = System.currentTimeMillis();
    }

    public double getTotalTimeOfHandling() {
        return (endTime - startTime) / 1000.0;
    }

    public static void main(String[] args) throws InterruptedException {
        SiteVisitCounter[] counters = {
                new UnsynchronizedCounter(),
                new VolatileCounter(),
                new SynchronizedBlockCounter(),
                new AtomicIntegerCounter(),
                new ReentrantLockCounter()
        };

        for (SiteVisitCounter counter : counters) {
            MultithreadingSiteVisitor visitor = new MultithreadingSiteVisitor(counter);
            visitor.visitMultithread(100);
            visitor.waitUntilAllVisited();

            System.out.println("Counter: " + counter.getClass().getSimpleName());
            System.out.println("Total visits: " + counter.getVisitCount());
            System.out.println("Total time: " + visitor.getTotalTimeOfHandling());
            System.out.println("----------------------");
        }
    }
}
