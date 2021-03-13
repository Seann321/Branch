package states;

import branch.Branch;
import controls.KeyManager;
import gfx.GUI;
import gfx.UIObject;
import states.dataState.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ConnectState extends States {

    private ArrayList<UIObject> guiStuff = new ArrayList<>();

    String input = "";
    public static String connectIP = "";
    Background background;
    UIObject enterIpAddress = new UIObject("Enter IP Address", Branch.WIDTH / 2, Branch.HEIGHT / 4 - GUI.font100.getSize(), true, Color.white, Color.white, GUI.font100, guiStuff);
    UIObject IPInput = new UIObject("", Branch.WIDTH / 2, Branch.HEIGHT / 4, true, Color.white, Color.white, GUI.font100, guiStuff);


    public ConnectState(Handler handler) {
        super(handler);
        background = new Background(handler);
        gui = new GUI(handler);
        for (UIObject u : guiStuff) {
            addText(gui, u);
        }
    }

    @Override
    public void tick() {
        background.tick();
        gui.tick();
        getKeyInput();
        IPInput.setText(input);
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ENTER)){
            input = input.substring(0, input.length() - 1);
            connectIP = input;
            KeyManager.LockInput = true;
            handler.switchToState(Branch.DataState);
        }
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)){
            input = input.substring(0, input.length() - 1);
            handler.switchToState(Branch.DataState);
        }
    }

    private void getKeyInput() {
        if (KeyManager.LockInput) {
            return;
        }
        input += KeyManager.GetKeyLastTyped();
        if (handler.getKM().keyJustPressed(KeyEvent.VK_DELETE)) {
            input = "";
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_BACK_SPACE)) {
            if (input.length() == 0) {
                return;
            }
            input = input.substring(0, input.length() - 1);
        }
    }

    @Override
    public void reset(){
        KeyManager.LockInput = false;
    }

    @Override
    public void render(Graphics g) {
        background.render(g);
        gui.render(g);
    }
}
