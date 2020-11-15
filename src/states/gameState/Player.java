package states.gameState;

import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private Handler handler;
    public Rectangle bounds;
    private int speed = 4;
    public static int GrassPoints = 5;
    public ArrayList<Robotnist> robotnist = new ArrayList<>();
    private Inventory inventory;

    public Player(Handler handler, Rectangle bounds) {
        this.bounds = bounds;
        this.handler = handler;
        inventory = new Inventory(handler);
    }

    public void tick() {
        inventory.tick();
        for(Robotnist r : robotnist){
            r.tick();
        }
        for (Tiles t : GameState.TilesMap.values()) {
            if (t.bounds.intersects(bounds)) {
                tileUnderPlayer = t;
            }
        }
        if (handler.getKM().left) {
            bounds.x -= speed;
        }
        if (handler.getKM().right) {
            bounds.x += speed;
        }
        if (handler.getKM().up) {
            bounds.y -= speed;
        }
        if (handler.getKM().down) {
            bounds.y += speed;
        }
        if(tileUnderPlayer.flower){
            GrassPoints+= tileUnderPlayer.flowerPoints;
            tileUnderPlayer.removeFlower();
        }

    }

    private Tiles tileUnderPlayer = null;

    public Tiles.TileType getTileTypeUnderPlayer(){
        if(tileUnderPlayer == null){
            return Tiles.TileType.GRASS;
        }else{
            return tileUnderPlayer.tileType;
        }
    }

    public void render(Graphics g) {
        if(tileUnderPlayer == null){
            return;
        }
        if (tileUnderPlayer.tileType == (Tiles.TileType.WATER)) {
            g.drawImage(Assets.PlayerBoat, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
        } else {
            g.drawImage(Assets.Player, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
        }
        for(Robotnist r : robotnist){
            r.render(g);
        }
        inventory.render(g);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
