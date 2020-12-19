package gfx.image;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage[] Void;

    public static void Init(){
        Void = new BufferedImage[]{
                ImageLoader.loadImage("..\\assets\\VoidA.png"),
                ImageLoader.loadImage("..\\assets\\VoidB.png"),
                ImageLoader.loadImage("..\\assets\\VoidC.png"),
                ImageLoader.loadImage("..\\assets\\VoidD.png"),
        };
    }

}
