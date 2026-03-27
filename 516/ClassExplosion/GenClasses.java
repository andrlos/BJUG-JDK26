import java.io.*;

public class GenClasses {
    public static void main(String[] args) throws Exception {
        File dir = new File("gen/demo/gen");
        dir.mkdirs();

        for (int i = 0; i < 10000; i++) {
            String className = "Class" + i;
            File f = new File(dir, className + ".java");

            try (PrintWriter out = new PrintWriter(f)) {
                out.println("package demo.gen;");
                out.println("public class " + className + " {");
                out.println("  public int value() { return " + i + "; }");
                out.println("}");
            }
        }
    }
}
