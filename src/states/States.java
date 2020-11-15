package states;

import gfx.GUI;
import gfx.UIObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class States {

    protected Handler handler;
    public GUI gui;


    public States(Handler handler) {
        this.handler = handler;

    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void addText(GUI gui, UIObject x) {
        x.handler = handler;
        gui.addText(x);
    }

}
