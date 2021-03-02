package branch;

import gfx.GUI;
import gfx.image.Assets;
import states.*;
import states.dataState.EditCustomer;
import states.menuState.OptionsScreen;
import states.menuState.SelectorScreen;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Branch implements Runnable {

    private Display display;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    private Handler handler;
    public static int WIDTH, HEIGHT;
    public String title;
    private boolean running = false;
    public static BranchState BranchState;
    public static MenuState MenuState;
    public static GrayState GrayState;
    public static DataState DataState;
    public static SelectorScreen SelectorScreen;
    public static OptionsScreen OptionsScreen;
    public static EditCustomer EditCustomer;

    public Branch(String title, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.title = title;
        handler = new Handler();
        GUI.init();
        //Assets.Init();
        //BranchState = new BranchState(handler);
        //MenuState = new MenuState(handler);
        //GrayState = new GrayState(handler);
        //SelectorScreen = new SelectorScreen(handler);
        //OptionsScreen = new OptionsScreen(handler);
        DataState = new DataState(handler);
        EditCustomer = new EditCustomer(handler);
        handler.switchToState(DataState);
    }


    public void init() throws IOException {
        display = new Display(title, WIDTH, HEIGHT);
        display.getFrame().addKeyListener(handler.getKM());
        display.getFrame().addMouseListener(handler.getMM());
        display.getFrame().addMouseMotionListener(handler.getMM());
        display.getCanvas().addMouseListener(handler.getMM());
        display.getCanvas().addMouseMotionListener(handler.getMM());
        display.getCanvas().addMouseWheelListener(handler.getMM());
    }


    public void tick() {
        handler.getKM().tick();
        Handler.currentState.tick();
    }


    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Everything below is what is drawn on the screen.

        Handler.currentState.render(g);

        //End Draw
        bs.show();

        g.dispose();
    }

    public static int FPS = 60;

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double timePerTick;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            timePerTick = 1000000000 / FPS;
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                if (ticks != FPS) {
                    System.out.println("FPS: " + ticks);
                }
                ticks = 0;
                timer = 0;
            }

        }

        stop();
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
