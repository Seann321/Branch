package gfx;

import branch.Main;
import states.Handler;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUI {

    public static Font font, font35, font50, font100;
    public static GUI currentGUI = null;
    private final Handler handler;
    private static String path = "src\\gfx\\assets\\slkscr.ttf";
    public ArrayList<UIObject> text = new ArrayList<>();

    public static void init() {
        if (Main.screenSize.width <= 1600) {
            font = FontLoader.loadFont(path, 20/2);
            font35 = FontLoader.loadFont(path, 35/2);
            font50 = FontLoader.loadFont(path, 50/2);
            font100 = FontLoader.loadFont(path, 100/2);
        } else {
            font = FontLoader.loadFont(path, 20);
            font35 = FontLoader.loadFont(path, 35);
            font50 = FontLoader.loadFont(path, 50);
            font100 = FontLoader.loadFont(path, 100);
        }
    }

    public GUI(Handler handler) {
        this.handler = handler;
    }

    public void render(Graphics g) {
        for (UIObject e : text) {
            if(e != null)
            e.render(g);
        }
    }

    public void tick() {
        for (UIObject e : text) {
            e.tick();
        }
    }

    public void onMouseMove(MouseEvent e) {
        for (UIObject o : text) {
            o.onMouseMove(e);
        }
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : text) {
            o.onMouseReleased(e);
        }
    }

    public void addText(UIObject e) {
        text.add(e);
    }

    public void removeText(UIObject e) {
        text.remove(e);
    }

}