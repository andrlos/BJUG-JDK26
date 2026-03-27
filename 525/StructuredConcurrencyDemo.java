import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrencyDemo {

    public static void main(String[] args) throws Exception {

        try (var scope = StructuredTaskScope.open()) {

            var task1 = scope.fork(() -> {
                Thread.sleep(1000);
                return "Task 1 done";
            });

            var task2 = scope.fork(() -> {
                Thread.sleep(1500);
                return "Task 2 done";
            });

            // Wait for all tasks in the scope
            scope.join();


            System.out.println(task1.get());
            System.out.println(task2.get());

            System.out.println("All done");
        }
    }
}
