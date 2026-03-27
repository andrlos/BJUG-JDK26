public class TraditionalThreadsDemo {

    static class Result {
        String value;
    }

    public static void main(String[] args) throws InterruptedException {

        Result r1 = new Result();
        Result r2 = new Result();

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                r1.value = "Task 1 done";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1500);
                r2.value = "Task 2 done";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        t2.start();

        // manual lifecycle management
        t1.join();
        t2.join();

        System.out.println(r1.value);
        System.out.println(r2.value);
        System.out.println("All done");
    }
}
