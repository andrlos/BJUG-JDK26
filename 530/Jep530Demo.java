public class Jep530Demo {
    public static void main(String[] args) {
        testInstanceof();
        testSwitch();
    }

    static void testInstanceof() {
        Object value = 77;
        // Primitive type pattern on Object
        if (value instanceof int i) {
            System.out.println("Matched primitive int: " + i);
        } else {
            System.out.println("No primitive byte match");
        }

        if (value instanceof Integer) {
            Integer i = (Integer) value;  // manual cast + unboxing
            System.out.println("Matched primitive byte: " + i);
        } else {
            System.out.println("No primitive byte match");
        }
    }

    static void testSwitch() {
        Object o = (byte)120;
   
        switch (o) {
            //case byte b -> System.out.println("b is byte of value = " + b);
	    case int i -> System.out.println("b is int of value = " + i);
            default -> System.out.println("Match not found!");
        }
    }
}
