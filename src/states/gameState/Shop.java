package states.gameState;

import branch.Branch;
import gfx.GUI;
import gfx.UIObject;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;

public class Shop {

    //Robotnist - 10 (Spawns Robotnist)
    //Robotnist Speed Upgrade - 25 (Double Speed)
    //Robotnist Communication Upgrade - 25 (Allows them to not lock onto the same plant)
    //Robotnist Closest Module - 100 (Instead of a random search, they will chose the closest, if not locked on too)

    private ArrayList<UIObject> shopGUI = new ArrayList<>();
    UIObject buyRobotnist = new UIObject("Robotnist (10)", Branch.WIDTH/2, Branch.HEIGHT/4, true, Color.white, Color.lightGray, GUI.font50, shopGUI);
    private Handler handler;
    private GUI gui;

    public Shop(Handler handler, GUI gui) {
        this.handler = handler;
        this.gui = gui;
        for (UIObject u : shopGUI) {
            u.active = false;
            gui.addText(u);
        }
    }

    public void tick() {
        setTextColors();
        if (GameState.Player.getTileTypeUnderPlayer().equals(Tiles.TileType.SHOP)) {
            for(UIObject u : shopGUI){
                u.active = true;
            }
        }else{
            for(UIObject u : shopGUI){
                u.active = false;
            }
        }
        if(buyRobotnist.wasClicked()){
            if(Player.GrassPoints >= 10){
                GameState.Player.robotnist.add(new Robotnist(handler, new Rectangle(GameState.Player.bounds.x,GameState.Player.bounds.y,GameState.Player.bounds.width,GameState.Player.bounds.height)));
                Player.GrassPoints -= 10;
            }
        }
    }

    private void setTextColors(){
        if(Player.GrassPoints >= 10){
            buyRobotnist.setColor(Color.white);
        }else{
            buyRobotnist.setColor(Color.lightGray);
        }
    }

    public void render(Graphics g) {
        if (GameState.Player.getTileTypeUnderPlayer().equals(Tiles.TileType.SHOP)) {
            gui.render(g);
        }
    }

}
