package states;

import branch.Branch;
import gfx.GUI;
import gfx.UIObject;
import states.gameState.Tiles;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuState extends States {

    private ArrayList<UIObject> guiStuff = new ArrayList<>();
    UIObject title = new UIObject("BRANCH", GUI.font50.getSize(), Branch.HEIGHT/4, false, Color.white, Color.white, GUI.font100, guiStuff);
    UIObject startGame = new UIObject("Start", GUI.font50.getSize(), Branch.HEIGHT/4 + (GUI.font100.getSize() *2), false, Color.white, Color.lightGray, GUI.font100, guiStuff);
    UIObject credits = new UIObject("Credits", GUI.font50.getSize(), Branch.HEIGHT/4 + GUI.font100.getSize() * 3, false, Color.white, Color.lightGray, GUI.font100, guiStuff);
    UIObject options = new UIObject("Options", GUI.font50.getSize(), Branch.HEIGHT/4 + (GUI.font100.getSize() * 4), false, Color.white, Color.lightGray, GUI.font100, guiStuff);
    UIObject leaveGame = new UIObject("Exit", GUI.font50.getSize(), Branch.HEIGHT/4 + (GUI.font100.getSize() * 5), false, Color.white, Color.lightGray, GUI.font100, guiStuff);

    public MenuState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        GUI.currentGUI = gui;
        for (UIObject u : guiStuff) {
            addText(gui, u);
        }
    }

    @Override
    public void tick() {
        if (leaveGame.wasClicked()) {
            System.exit(0);
        }
        if(credits.wasClicked()){
            credits.setText("Made By Sean!");
        }
        if(startGame.wasClicked() || handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)){
            handler.switchToGameState();
        }
        gui.tick();
    }

    @Override
    public void render(Graphics g) {
        for (Tiles t : GameState.TilesMap.values()) {
            t.render(g);
        }
        gui.render(g);
    }
}
