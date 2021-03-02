package states.branchState;

import gfx.image.Assets;
import states.BranchState;
import states.Handler;

import java.awt.*;
import java.util.Random;

public class Tiles {

    public boolean frozen = false;
    private Rectangle bounds;
    private Handler handler;
    public int[] cords = new int[0];
    Random rand = new Random();
    public Image tileType;

    public Tiles(Rectangle bounds, int[] startingCords, Handler handler) {
        this.bounds = bounds;
        this.handler = handler;
        this.cords = startingCords;
        tileType = Assets.VOID;
    }

    public Tiles(Rectangle bounds, Handler handler) {
        frozen = true;
        this.bounds = bounds;
        this.handler = handler;
        cords = new int[]{bounds.x,bounds.y};
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (frozen) {
            g.drawImage(tileType, bounds.x, bounds.y, bounds.width, bounds.height, null);
        } else
            g.drawImage(tileType, bounds.x + BranchState.Camera.getX(), bounds.y + BranchState.Camera.getY(), bounds.width, bounds.height, null);
    }

    public Rectangle getBounds() {
        if(frozen){
            return bounds;
        }
        return new Rectangle(bounds.x + BranchState.Camera.getX(), bounds.y + BranchState.Camera.getY(), bounds.width, bounds.height);
    }
}