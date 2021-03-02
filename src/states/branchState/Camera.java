package states.branchState;

import branch.Branch;

public class Camera {

    private int x,y;
    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void tick(){
        x = (-player.bounds.x - (player.bounds.width/2)) + Branch.WIDTH/2;
        y = (-player.bounds.y - (player.bounds.height/2)) + Branch.HEIGHT/2;
    }

    public int getX(){
        if(x > 0){
            return 0;
        }
        return x;
    }

    public int getY(){
        if(y > 0){
            return 0;
        }
        return y;
    }

}
