package multithreading;

public class SynchronizedBlockCounter implements SiteVisitCounter {
    private Integer counter = 0;

    @Override
    public void incrementVisitCount() throws InterruptedException {
        synchronized (this) {
            counter++;
            Thread.sleep(100);
        }
    }

    @Override
    public int getVisitCount() {
        synchronized (this) {
            return counter;
        }
    }
}
