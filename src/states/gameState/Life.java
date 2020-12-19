package states.gameState;

import gfx.image.Assets;
import org.w3c.dom.css.Rect;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Life {

    public Color color = Color.black;
    Handler handler;
    BufferedImage image = Assets.PERSON;
    Rectangle bounds;
    int health = 5;
    int speed = 1;
    Random rand = new Random();
    boolean swim = false;
    public boolean active = true;

    public Life(Handler handler, Rectangle bounds) {
        this.handler = handler;
        this.bounds = bounds;
    }

    public Life(Handler handler, Rectangle bounds, Color color) {
        setColor(color);
        this.handler = handler;
        this.bounds = bounds;
    }

    public void tick() {
        if(speed != 0){
            bounds.x += rand.nextInt(3) - 1;
            bounds.y += rand.nextInt(3) - 1;
        }else{
            bounds.x += (rand.nextInt(3) - 1) * speed;
            bounds.y += (rand.nextInt(3) - 1) * speed;
        }
        tileCondition();
        fight();
        reproduce();
    }

    private void evolve(Life oldLife, Life newLife){
        int change = rand.nextInt(3) - 1;
        newLife.health += change + oldLife.health;
        newLife.speed += change + oldLife.speed;
    }

    private void fight(){
        for (Life l : GameState.Life) {
            if (l.getBounds().intersects(getBounds())) {
                if(l.color.equals(color)){
                    return;
                }else{
                    l.health -= rand.nextInt(2);
                    if(l.health <= 0){
                        l.active = false;
                    }
                }
            }
        }
    }

    private void reproduce() {
        for (Life l : GameState.Life) {
            if (l == this) {
                continue;
            }
            if (l.getBounds().intersects(getBounds())) {
                return;
            }
        }
        if (GameState.getTileAtCords(getCords()).tileType == Assets.GRASS) {
            if (rand.nextInt(100) == 1) {
                Life tempLife = new Life(handler, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height),GameState.randomColor());
                tempLife.swim = true;
                tempLife.health += 10;
                evolve(this,tempLife);
                GameState.NewLife.add(tempLife);
            }else{
                GameState.NewLife.add(new Life(handler, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height),color));
            }
        }
    }

    private void tileCondition() {
        Tiles tileUnder = GameState.getTileAtCords(getCords());
        if (tileUnder.tileType == Assets.WATER && !swim) {
            active = false;
        }
        if (tileUnder.tileType == Assets.VOID) {
            active = false;
        }
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void render(Graphics g) {
        g.drawImage(image, bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height, null);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        g.setColor(color);
        g.fillRect(getCords()[0] + imageWidth,getCords()[1] + imageHeight,imageWidth,imageHeight);
    }

    public int[] getCords() {
        return new int[]{bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY()};
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds.x + GameState.Camera.getX(), bounds.y + GameState.Camera.getY(), bounds.width, bounds.height);
    }

}
