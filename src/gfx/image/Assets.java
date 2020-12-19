package gfx.image;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage GRASS, WATER, LIFE, PERSON, VOID;

    public static void Init(){
        GRASS = ImageLoader.loadImage("..\\assets\\Grass.png");
        WATER = ImageLoader.loadImage("..\\assets\\Water.png");
        LIFE = ImageLoader.loadImage("..\\assets\\Life.png");
        PERSON = ImageLoader.loadImage("..\\assets\\Person.png");
        VOID = ImageLoader.loadImage("..\\assets\\Void.png");
    }

}
