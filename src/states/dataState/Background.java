package states.dataState;

import branch.Branch;
import gfx.UIObject;
import states.DataState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Background {

    public static boolean active = true;
    private static ArrayList<Dot> dots = new ArrayList<>();
    private static ArrayList<Dot> newDots = new ArrayList<>();
    Handler handler;

    public Background(Handler handler) {
        this.handler = handler;
    }

    public void tick() {
        if (active)
            newDots.add(new Dot());
        dots.clear();
        dots.addAll(newDots);
        for (Dot d : dots) {
            d.tick();
            if (!d.active) {
                newDots.remove(d);
            }
        }
    }

    public void render(Graphics g) {
        for (Dot d : dots) {
            d.render(g);
        }
    }

}

class Dot {

    Random random = new Random();
    private int x, y;
    Color color = new Color(random.nextInt(254), random.nextInt(254), random.nextInt(254));
    public boolean active = true;
    private int speed;

    public Dot() {
        x = random.nextInt(Branch.WIDTH);
        y = random.nextInt(Branch.HEIGHT);
        speed = (random.nextInt(10) - 5);
        if (speed == 0) {
            speed = 1;
        }
    }

    public void tick() {
        x += speed;
        y -= speed;
        if (random.nextInt(20) == 1)
            color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * .8));
        if (color.getAlpha() == 0) {
            active = false;
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.drawRect(x, y, 1, 1);
    }

}
