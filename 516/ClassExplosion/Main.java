public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            Class.forName("demo.gen.Class" + i);
        }

        long end = System.currentTimeMillis();
        System.out.println("Loaded 10k classes in: " + (end - start) + " ms");
    }
}
