package branch;

import java.awt.*;
import javax.swing.JFrame;

public class Display {

    private static JFrame frame;
    public static Canvas Canvas;

    private final String title;
    private int width, height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
        setBorderless(true);
    }

    public static void minimize(){
        frame.setState(Frame.ICONIFIED);
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Canvas = new Canvas();
        Canvas.setPreferredSize(new Dimension(width, height));
        Canvas.setMaximumSize(new Dimension(width, height));
        Canvas.setMinimumSize(new Dimension(width, height));
        Canvas.setFocusable(false);
        Canvas.setBackground(Color.black);

        frame.add(Canvas);
        frame.pack();

    }

    public void setBorderless(boolean x) {
        frame.dispose();
        frame.setUndecorated(x);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }

    public void changeSize(int x, int y) {
        width = x;
        height = y;

        frame.dispose();
        frame.setUndecorated(true);
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Canvas.setPreferredSize(new Dimension(x, y));
        Canvas.setMaximumSize(new Dimension(x, y));
        Canvas.setMinimumSize(new Dimension(x, y));
        Canvas.setFocusable(false);
        Canvas.setBackground(Color.black);

        frame.add(Canvas);
        frame.pack();
    }

    public void setColor(Color color) {
        Canvas.setBackground(color);
    }

    public Canvas getCanvas() {
        return Canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getX() {
        return frame.getX();
    }

    public int getY() {
        return frame.getY();
    }

    public void setLocation(int x, int y) {
        frame.setLocation(x, y);
    }

}