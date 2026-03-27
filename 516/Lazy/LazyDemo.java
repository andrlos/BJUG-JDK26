import java.lang.LazyConstant;

public class LazyDemo {

    public static void main(String[] args) throws Exception {

        
	long start, end;

        // ------------------------
        // LAZY: force class load
        // ------------------------
        start = System.nanoTime();
        Class.forName("Lazy");
        end = System.nanoTime();
        System.out.println("Lazy init (no computation): " + (end - start)/1_000_000 + " ms");


        // ------------------------
        // EAGER: force class init
        // ------------------------
        start = System.nanoTime();
        Class.forName("Eager");
        end = System.nanoTime();
        System.out.println("Eager init (10 constants): " + (end - start)/1_000_000 + " ms");


        // ------------------------
        // Access ONLY TWO constants
        // ------------------------
        start = System.nanoTime();
        int value1 = Lazy.V1.get();
        int value2 = Lazy.V2.get();
        end = System.nanoTime();
        System.out.println("Lazy first access (2 constants): " + (end - start)/1_000_000 + " ms");

        System.out.println("Value: " + value1); // prevent optimization
    }
}

class Lazy {

    static final LazyConstant<Integer> V1  = LazyConstant.of(() -> Heavy.compute(1));
    static final LazyConstant<Integer> V2  = LazyConstant.of(() -> Heavy.compute(2));
    static final LazyConstant<Integer> V3  = LazyConstant.of(() -> Heavy.compute(3));
    static final LazyConstant<Integer> V4  = LazyConstant.of(() -> Heavy.compute(4));
    static final LazyConstant<Integer> V5  = LazyConstant.of(() -> Heavy.compute(5));
    static final LazyConstant<Integer> V6  = LazyConstant.of(() -> Heavy.compute(6));
    static final LazyConstant<Integer> V7  = LazyConstant.of(() -> Heavy.compute(7));
    static final LazyConstant<Integer> V8  = LazyConstant.of(() -> Heavy.compute(8));
    static final LazyConstant<Integer> V9  = LazyConstant.of(() -> Heavy.compute(9));
    static final LazyConstant<Integer> V10 = LazyConstant.of(() -> Heavy.compute(10));
}

class Eager {

    static final int V1  = Heavy.compute(1);
    static final int V2  = Heavy.compute(2);
    static final int V3  = Heavy.compute(3);
    static final int V4  = Heavy.compute(4);
    static final int V5  = Heavy.compute(5);
    static final int V6  = Heavy.compute(6);
    static final int V7  = Heavy.compute(7);
    static final int V8  = Heavy.compute(8);
    static final int V9  = Heavy.compute(9);
    static final int V10 = Heavy.compute(10);
}

class Heavy {

    static int compute(int id) {
        long sum = 0;
        for (int i = 0; i < 200_000_000; i++) {
            sum += (i ^ id);
        }
        return (int) sum;
    }
}
