package states;

import branch.Branch;
import controls.KeyManager;
import controls.MouseManager;
import gfx.GUI;

public class Handler {

    private KeyManager keyManager;
    private MouseManager mouseManager;
    public static States currentState = null;

    public Handler(){
        keyManager = new KeyManager();
        mouseManager = new MouseManager(this);
    }

    public KeyManager getKM() {
        return keyManager;
    }

    public MouseManager getMM() {
        return mouseManager;
    }

    public void setCurrentState(States x){
        currentState = x;
    }


    public GUI getCurrentGUI() {
        return GUI.currentGUI;
    }
    public void resetMouseManager(){
        mouseManager = null;
        mouseManager = new MouseManager(this);
    }

    public void switchToGameState(){
        GUI.currentGUI = Branch.GameState.gui;
        currentState = Branch.GameState;
    }

    public void switchToMenuState(){
        GUI.currentGUI = Branch.MenuState.gui;
        currentState = Branch.MenuState;
    }

}
