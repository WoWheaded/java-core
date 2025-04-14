package multithreading.finalModule;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private List<Integer> numbers;
    private String taskName;

    public CalculateSumTask(List<Integer> list, String taskName) {
        this.numbers = list;
        this.taskName = taskName;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Имя задачи " + taskName + " имя потока " + Thread.currentThread().getName());
        Thread.sleep(200);
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
}
