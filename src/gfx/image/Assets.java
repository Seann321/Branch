package gfx.image;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage Player, Grass, Sand, PlayerBoat, Flower, Shop, Robotnist, RobotnistBoat, Marsh, Selected, MarshFlower;
    public static BufferedImage[] Void, Water;

    public static void Init(){
        Player = ImageLoader.loadImage("..\\assets\\Player.png");
        MarshFlower = ImageLoader.loadImage("..\\assets\\MarshFlower.png");
        Selected = ImageLoader.loadImage("..\\assets\\Selected.png");
        PlayerBoat = ImageLoader.loadImage("..\\assets\\PlayerBoat.png");
        Grass = ImageLoader.loadImage("..\\assets\\Grass.png");
        Sand = ImageLoader.loadImage("..\\assets\\Sand.png");
        Flower = ImageLoader.loadImage("..\\assets\\Flower.png");
        Shop = ImageLoader.loadImage("..\\assets\\FlowerShop.png");
        Robotnist = ImageLoader.loadImage("..\\assets\\Robotnist.png");
        RobotnistBoat = ImageLoader.loadImage("..\\assets\\RobotnistBoat.png");
        Marsh = ImageLoader.loadImage("..\\assets\\Marsh.png");
        Void = new BufferedImage[]{
                ImageLoader.loadImage("..\\assets\\VoidA.png"),
                ImageLoader.loadImage("..\\assets\\VoidB.png"),
                ImageLoader.loadImage("..\\assets\\VoidC.png"),
                ImageLoader.loadImage("..\\assets\\VoidD.png"),
        };
        Water = new BufferedImage[]{
                ImageLoader.loadImage("..\\assets\\WaterA.png"),
                ImageLoader.loadImage("..\\assets\\WaterB.png"),
                ImageLoader.loadImage("..\\assets\\WaterC.png"),
                ImageLoader.loadImage("..\\assets\\WaterD.png"),
        };
    }

}
