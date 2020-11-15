package states.gameState;

import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Robotnist {

    private Handler handler;
    public Rectangle bounds;
    private int speed = 1;
    private Tiles lockedOnTo = null;
    Random rand = new Random();

    public Robotnist(Handler handler, Rectangle bounds) {
        this.bounds = bounds;
        this.handler = handler;
    }

    public void tick() {
        for (Tiles t : GameState.TilesMap.values()) {
            if (t.bounds.intersects(bounds)) {
                tileUnderPlayer = t;
            }
        }
        if (lockedOnTo == null) {
            ArrayList<Tiles> flowerTiles = new ArrayList<>();
            for (Tiles t : GameState.TilesMap.values()) {
                if (t.flower && !t.lockedOn) {
                    flowerTiles.add(t);
                }
                if (t.bounds.intersects(bounds)) {
                    tileUnderPlayer = t;
                }
            }
            if (flowerTiles.size() != 0) {
                lockedOnTo = flowerTiles.get(rand.nextInt(flowerTiles.size()));
                lockedOnTo.lockedOn = true;
            } else {
                return;
            }
        }
        if (lockedOnTo.bounds.getX() > bounds.x) {
            bounds.x += speed;
        }
        if (lockedOnTo.bounds.getX() < bounds.x) {
            bounds.x -= speed;
        }
        if (lockedOnTo.bounds.getY() > bounds.y) {
            bounds.y += speed;
        }
        if (lockedOnTo.bounds.getY() < bounds.y) {
            bounds.y -= speed;
        }
        if (!lockedOnTo.flower) {
            lockedOnTo.lockedOn = false;
            lockedOnTo = null;
            return;
        }
        if (lockedOnTo.bounds.getX() == bounds.x && lockedOnTo.bounds.getY() == bounds.y) {
            if (lockedOnTo.flower) {
                Player.GrassPoints += lockedOnTo.flowerPoints;
                lockedOnTo.removeFlower();
            }
            lockedOnTo.lockedOn = false;
            lockedOnTo = null;
        }
    }

    private Tiles tileUnderPlayer = null;

    public void render(Graphics g) {
        if (tileUnderPlayer == null)
            return;
        if (tileUnderPlayer.tileType == (Tiles.TileType.WATER)) {
            g.drawImage(Assets.RobotnistBoat, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
        } else {
            g.drawImage(Assets.Robotnist, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
        }
    }

}
