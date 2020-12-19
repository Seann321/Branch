package states.gameState;

import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.util.Random;

public class Tiles {

    public Rectangle bounds;
    private Handler handler;
    public int[] cords;
    private Image image;
    Random rand = new Random();

    public enum TileType {
        VOID
    }

    public TileType tileType;

    public Tiles(Rectangle bounds, int[] startingCords, Handler handler) {
        this.bounds = bounds;
        this.handler = handler;
        this.cords = startingCords;
        image = Assets.Void[rand.nextInt(4)];
        tileType = TileType.VOID;
    }

    public void tick() {
    }


    public void render(Graphics g) {
        g.drawImage(image, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
    }

}
