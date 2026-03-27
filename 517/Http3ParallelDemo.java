import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Http3ParallelDemo {

    private static final int REQUEST_COUNT = 20;
    private static final String URL = "https://cloudflare-quic.com"; // HTTP/3 capable

    public static void main(String[] args) throws Exception {

        HttpClient.Version version = HttpClient.Version.HTTP_3;
        //switch to HTTP_2 for comparison

        HttpClient client = HttpClient.newBuilder()
                .version(version)
		.connectTimeout(Duration.ofSeconds(10))
                .build();


        System.out.println("Running with: " + client.version());
        System.out.println("Sending " + REQUEST_COUNT + " parallel requests...\n");

        List<CompletableFuture<Long>> futures = new ArrayList<>();

        long globalStart = System.currentTimeMillis();

        for (int i = 0; i < REQUEST_COUNT; i++) {
            int requestId = i;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            long start = System.currentTimeMillis();

            CompletableFuture<Long> future =
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(response -> {
                                long end = System.currentTimeMillis();
                                long duration = end - start;

                                System.out.println("Request " + requestId +
                                        " → " + response.statusCode() +
                                        " in " + duration + " ms");

                                return duration;
                            });

            futures.add(future);
        }

        // wait for all
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long globalEnd = System.currentTimeMillis();

        // compute stats
        long total = 0;
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;

        for (CompletableFuture<Long> f : futures) {
            long val = f.get();
            total += val;
            max = Math.max(max, val);
            min = Math.min(min, val);
        }

        double avg = total / (double) REQUEST_COUNT;

        System.out.println("\n=== Summary ===");
        System.out.println("Total time: " + (globalEnd - globalStart) + " ms");
        System.out.println("Avg latency: " + avg + " ms");
        System.out.println("Min latency: " + min + " ms");
        System.out.println("Max latency: " + max + " ms");
    }
}
