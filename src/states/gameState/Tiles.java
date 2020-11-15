package states.gameState;

import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tiles {

    public Rectangle bounds;
    private Handler handler;
    public int[] cords;
    private Image image;
    Random rand = new Random();
    public boolean lockedOn = false;
    public boolean flower = false;
    public int flowerPoints = 0;

    public enum TileType {
        WATER, GRASS, SAND, SPACE, FLOWER, SHOP, MARSH, MARSHFLOWER
    }

    public TileType tileType;

    public Tiles(Rectangle bounds, int[] startingCords, Handler handler) {
        this.bounds = bounds;
        this.handler = handler;
        this.cords = startingCords;
        image = Assets.Water[rand.nextInt(4)];
        tileType = TileType.WATER;
    }

    public void tick() {
        if (!GameState.TutorialEnded) {
            return;
        }
        if (tileType == TileType.GRASS) {
            //4000
            if (rand.nextInt(4000) == 1) {
                changeImage(Assets.Flower);
            }
        }
        if (tileType == TileType.MARSH) {
            //3000
            if (rand.nextInt(3000) == 1) {
                changeImage(Assets.MarshFlower);
            }
        }
    }

    public static Image getTileImage(TileType x) {
        if (x == TileType.GRASS) {
            return Assets.Grass;
        }
        if (x == TileType.SAND) {
            return Assets.Sand;
        }
        if (x == TileType.FLOWER) {
            return Assets.Flower;
        }
        if (x == TileType.SHOP) {
            return Assets.Shop;
        }
        if (x == TileType.MARSH) {
            return Assets.Marsh;
        }
        if (x == TileType.MARSHFLOWER) {
            return Assets.MarshFlower;
        }
        return Assets.PlayerBoat;
    }

    public void removeFlower(){
        if(tileType == TileType.MARSHFLOWER){
            changeImage(Assets.Marsh);
        }if(tileType == TileType.FLOWER){
            changeImage(Assets.Grass);
        }
    }

    public void changeImage(Image image) {
        this.image = image;
        if (image.equals(Assets.Grass)) {
            tileType = TileType.GRASS;
            setFlower(false);
        }
        if (image.equals(Assets.Sand)) {
            tileType = TileType.SAND;
            setFlower(false);
        }
        if (image.equals(Assets.Flower)) {
            tileType = TileType.FLOWER;
            setFlower(true);
        }
        if (image.equals(Assets.Shop)) {
            tileType = TileType.SHOP;
            setFlower(false);
        }
        if (image.equals(Assets.Marsh)) {
            tileType = TileType.MARSH;
            setFlower(false);
        }
        if (image.equals(Assets.MarshFlower)) {
            tileType = TileType.MARSHFLOWER;
            setFlower(true);
        }
    }

    private void setFlower(boolean x) {
        if (x) {
            flower = true;
        } else {
            flower = false;
        }
        if (tileType == TileType.MARSHFLOWER) {
            flowerPoints = 3;
        }
        if (tileType == TileType.FLOWER) {
            flowerPoints = 1;
        }
    }

    public void render(Graphics g) {
        g.drawImage(image, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
    }

}
