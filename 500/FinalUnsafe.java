import sun.misc.Unsafe;
import java.lang.reflect.Field;

class Test {
    final int x = 10;
}

public class FinalUnsafe {
    public static void main(String[] args) throws Exception {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        Test t = new Test();

        Field f = Test.class.getDeclaredField("x");
        long offset = unsafe.objectFieldOffset(f);

        unsafe.putInt(t, offset, 42); // 💥 bypasses final

        System.out.println(f.getInt(t));
    }
}
