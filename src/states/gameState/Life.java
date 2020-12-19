package states.gameState;

import gfx.image.Assets;
import states.Handler;

import java.awt.*;
import java.util.Random;

public class Life {

    Handler handler;
    Rectangle bounds;
    Random rand = new Random();

    public Life(Handler handler, Rectangle bounds) {
        this.handler = handler;
        this.bounds = bounds;
    }

    public void tick(){
        bounds.x =+ rand.nextInt(2) - 1;
        bounds.y =+ rand.nextInt(2) - 1;
    }

    public void render(Graphics g){
        g.drawImage(Assets.PERSON,bounds.x,bounds.y,bounds.width,bounds.height,null);
    }
}
