import java.applet.Applet;
import java.awt.Frame;
import java.awt.Graphics;

public class MyApplet extends Applet {

    @Override
    public void init() {
        System.out.println("Applet initialized");
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Hello from Applet", 50, 50);
    }

    public static void main(String[] args) {
        Frame frame = new Frame("Applet Demo");
        MyApplet applet = new MyApplet();

        applet.init();
        applet.start();

        frame.add(applet);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
