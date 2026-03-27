import java.util.*;
import java.util.concurrent.*;

public class G1RealisticBenchmark {

    //static final int THREADS = Runtime.getRuntime().availableProcessors();
    static final int THREADS = 8;
    static final int OPERATIONS = 2_000_000;
    static final int LIVE_SET_SIZE = 10_000;

    static final List<Node> shared = Collections.synchronizedList(new ArrayList<>());

    static class Node {
        byte[] payload = new byte[1024]; // force region usage
        Node ref;
    }

    public static void main(String[] args) throws Exception {
        //warmup
        mainLoop(false);
        //actual benchmark
        mainLoop(true);
    }

    private static void mainLoop(boolean print) throws Exception {    

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);

        long start = System.nanoTime();

        for (int t = 0; t < THREADS; t++) {
            pool.submit(() -> {
                Random rnd = new Random();
                List<Node> local = new ArrayList<>();

                for (int i = 0; i < OPERATIONS; i++) {

                    Node n = new Node();

                    // create cross references (important!)
                    if (!shared.isEmpty()) {
                        n.ref = shared.get(rnd.nextInt(shared.size()));
                    }

                    local.add(n);

                    // keep working set bounded
                    if (local.size() > LIVE_SET_SIZE) {
                        local.remove(0);
                    }

                    // occasionally publish to shared structure
                    if (i % 100 == 0) {
                        shared.add(n);
                        if (shared.size() > LIVE_SET_SIZE) {
                            shared.remove(0);
                        }
                    }
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);

        long end = System.nanoTime();
        long elapsedMs = (end - start) / 1_000_000;

        long totalOps = (long) THREADS * OPERATIONS;
        double jOPS = totalOps / (elapsedMs / 1000.0);
        if (print){
            System.out.printf("Threads: %d, Ops: %d, Time: %d ms, Throughput: %.2f jOPS%n",
                    THREADS, totalOps, elapsedMs, jOPS);
        }
    }
}
