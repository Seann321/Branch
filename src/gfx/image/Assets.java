package gfx.image;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage GRASS, WATER, LIFE, PERSON;
    public static BufferedImage[] VOID;

    public static void Init(){
        GRASS = ImageLoader.loadImage("..\\assets\\Grass.png");
        WATER = ImageLoader.loadImage("..\\assets\\Water.png");
        LIFE = ImageLoader.loadImage("..\\assets\\Life.png");
        PERSON = ImageLoader.loadImage("..\\assets\\Person.png");
        VOID = new BufferedImage[]{
                ImageLoader.loadImage("..\\assets\\VoidA.png"),
                ImageLoader.loadImage("..\\assets\\VoidB.png"),
                ImageLoader.loadImage("..\\assets\\VoidC.png"),
                ImageLoader.loadImage("..\\assets\\VoidD.png"),
        };
    }

}
