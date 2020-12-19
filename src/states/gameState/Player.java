package states.gameState;

import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;

public class Player {

    private Handler handler;
    public Rectangle bounds;
    private int speed = 10;

    public Player(Handler handler, Rectangle bounds) {
        this.bounds = bounds;
        this.handler = handler;
    }

    public void tick() {
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

    }


    public void render(Graphics g) {
    }

}
