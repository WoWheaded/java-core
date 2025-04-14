package multithreading.finalModule;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        DataProcessor dataProcessor = new DataProcessor(executor);

        for (int i = 0; i < 102; i++) {
            List<Integer> numbers = List.of(1, i + 2);
            dataProcessor.submitTask(numbers);
        }

        while (dataProcessor.getActiveTaskCount() > 0) {
            System.out.println("Активных задач: " + dataProcessor.getActiveTaskCount());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("------------------");
        for (int i = 1; i <= 100; i++) {
            String taskName = "task" + i;
            Optional<Integer> result = dataProcessor.getResult(taskName);
            result.ifPresentOrElse(
                    sum -> System.out.println(taskName + ": " + sum),
                    () -> System.out.println(taskName + ": нет суммы вычислений")
            );
        }
        dataProcessor.shutdown();
    }
}
