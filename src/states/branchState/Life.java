package states.branchState;

import gfx.image.Assets;
import states.BranchState;
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
    int speed = 3;
    int attack = 1;
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
        newLife.health += (rand.nextInt(3) - 1) + oldLife.health;
        newLife.speed += (rand.nextInt(3) - 1) + oldLife.speed;
        newLife.attack += (rand.nextInt(3) - 1) + oldLife.speed;
    }

    private void fight(){
        for (Life l : BranchState.Life) {
            if (l.getBounds().intersects(getBounds())) {
                if(l.color.equals(color)){
                    return;
                }else{
                    l.health -= rand.nextInt(2);
                    l.health -= attack;
                    if(l.health <= 0){
                        l.active = false;
                    }
                }
            }
        }
    }

    private void reproduce() {
        for (Life l : BranchState.Life) {
            if (l == this) {
                continue;
            }
            if (l.getBounds().intersects(getBounds())) {
                return;
            }
        }
        if (BranchState.getTileAtCords(getCords()).tileType == Assets.GRASS) {
            if (rand.nextInt(100) == 1) {
                Life tempLife = new Life(handler, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height), BranchState.randomColor());
                tempLife.swim = true;
                tempLife.health += 10;
                evolve(this,tempLife);
                BranchState.NewLife.add(tempLife);
            }else{
                Life tempLife = new Life(handler, new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height),color);
                tempLife.evolve(this,tempLife);
                BranchState.NewLife.add(tempLife);
            }
        }
    }

    private void tileCondition() {
        Tiles tileUnder = BranchState.getTileAtCords(getCords());
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
        g.drawImage(image, bounds.x + BranchState.Camera.getX(), bounds.y + BranchState.Camera.getY(), bounds.width, bounds.height, null);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        g.setColor(color);
        g.fillRect(getCords()[0] + imageWidth,getCords()[1] + imageHeight,imageWidth,imageHeight);
    }

    public int[] getCords() {
        return new int[]{bounds.x + BranchState.Camera.getX(), bounds.y + BranchState.Camera.getY()};
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds.x + BranchState.Camera.getX(), bounds.y + BranchState.Camera.getY(), bounds.width, bounds.height);
    }

}
