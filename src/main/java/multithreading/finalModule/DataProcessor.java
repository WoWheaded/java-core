package multithreading.finalModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {
    private final ExecutorService executor;
    private final AtomicInteger taskCounter = new AtomicInteger();
    private final AtomicInteger activeTaskCount = new AtomicInteger();
    private final Map<String, Integer> results = new HashMap<>();

    public DataProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    public String submitTask(List<Integer> list) {
        String taskName = "task" + taskCounter.incrementAndGet();
        CalculateSumTask calculateSumTask = new CalculateSumTask(list, taskName);

        CompletableFuture<Integer> taskFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return calculateSumTask.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executor);

        activeTaskCount.incrementAndGet();
//            System.out.println("Активных задач: " + dataProcessor.getActiveTaskCount());
        taskFuture.thenAcceptAsync(res -> {
            synchronized (results) {
                results.put(taskName, res);
                activeTaskCount.decrementAndGet();
            }
        });
        return taskName;
    }

    public int getActiveTaskCount() {
        return activeTaskCount.get();
    }

    public Optional<Integer> getResult(String taskName) {
        synchronized (results) {
            return Optional.ofNullable(results.get(taskName));
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            boolean isTerminated = executor.awaitTermination(10, TimeUnit.MILLISECONDS);
            if (!isTerminated) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            throw new RuntimeException(e);
        }
    }

}
