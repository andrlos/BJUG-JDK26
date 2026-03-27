import jdk.incubator.vector.*;

public class VectorBenchmark {

    static final int SIZE = 10_000_000;

    static float[] a = new float[SIZE];
    static float[] b = new float[SIZE];
    static float[] c = new float[SIZE];

    static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    public static void main(String[] args) {
        for (int i=0; i < 5; i++){
            mainloop(false);
        }
        mainloop(true);
    }

    private static void mainloop(boolean actualRun){
        init();

        // Warmup
        for (int i = 0; i < 5; i++) {
            scalar();
            vector();
        }

        // Measure scalar
        long start1 = System.nanoTime();
        scalar();
        long end1 = System.nanoTime();

        // Measure vector
        long start2 = System.nanoTime();
        vector();
        long end2 = System.nanoTime();
	if (actualRun){
            System.out.println("Scalar time: " + (end1 - start1) / 100_000 + " ms");
            System.out.println("Vector time: " + (end2 - start2) / 100_000 + " ms");
        }
    }

    static void init() {
        for (int i = 0; i < SIZE; i++) {
            a[i] = i;
            b[i] = SIZE - i;
        }
    }

    static void scalar() {
        for (int i = 0; i < SIZE; i++) {
            float x = a[i];
            float y = b[i];

            //float r = x * y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            c[i] = x * y + x * y - x + y + x * y - x + y + x * y - x + y;
        }
    }

    static void vector() {
        int i = 0;
        int upperBound = SPECIES.loopBound(SIZE);

        for (; i < upperBound; i += SPECIES.length()) {
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            var vc = va.mul(vb);     // x * y
            vc = vc.add(va);         // + x
            vc = vc.mul(vb);         // * y
            vc = vc.sub(va);         // - x
            vc = vc.add(vb);         // + y
            vc = vc.add(va);         // + x
            vc = vc.mul(vb);         // * y
            vc = vc.sub(va);         // - x
            vc = vc.add(vb);         // + y
            vc = vc.add(va);         // + x
            vc = vc.mul(vb);         // * y
            vc = vc.sub(va);         // - x
            vc = vc.add(vb);         // + y
            vc.intoArray(c, i);
	}

        // tail loop
        for (; i < SIZE; i++) {
            float x = a[i];
            float y = b[i];

            //float r = x * y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            //r = r + x;
            //r = r * y;
            //r = r - x;
            //r = r + y;
            //c[i] = r;
            c[i] = x * y + x * y - x + y + x * y - x + y + x * y - x + y;

        }
    }
}
