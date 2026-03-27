
import java.lang.reflect.Field;

class Test {
    final int x = 10;
}

class Reader {
    static void read(Test t) throws Exception {
        Field f = Test.class.getDeclaredField("x");
        f.setAccessible(true);
        
        // Direct acces another class (still 10)
        System.out.println("Reader (direct): " + t.x);
        
        // Reflective access from another class (42)
        System.out.println("Reader (reflective): " + f.get(t));
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Test t = new Test();

        Field f = Test.class.getDeclaredField("x");
        f.setAccessible(true);

        f.set(t, 42);

        // Direct access (still 10)
        System.out.println("Main (direct): " + t.x);

        // Reflective access (actual memory value - 42)
        System.out.println("Main (reflective): " + f.get(t));

        // Call into another class
        Reader.read(t);
    }
}


