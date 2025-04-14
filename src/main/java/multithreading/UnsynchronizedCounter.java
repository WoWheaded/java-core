package multithreading;

public class UnsynchronizedCounter implements SiteVisitCounter {
    private int counter = 0;

    @Override
    public void incrementVisitCount() throws InterruptedException {
        counter++;
        Thread.sleep(100);
    }

    @Override
    public int getVisitCount() {
        return counter;
    }
}
