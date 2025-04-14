package multithreading;

public interface SiteVisitCounter {
    void incrementVisitCount() throws InterruptedException;
    int  getVisitCount() throws InterruptedException;
}
