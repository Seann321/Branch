package states.branchState;

import branch.Branch;
import gfx.image.Assets;
import states.BranchState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;

public class WorldBuilder {

    private ArrayList<Tiles> selectionUI = new ArrayList<>();
    public Tiles selectedTile;
    Handler handler;

    public WorldBuilder(Handler handler) {
        this.handler = handler;
        for(int i = 0; i < 4; i++){
            selectionUI.add(new Tiles((new Rectangle(Branch.WIDTH/100, (int)(i * BranchState.TileSize * 1.2) + BranchState.TileSize/10, BranchState.TileSize, BranchState.TileSize)), handler));
        }
        selectionUI.get(0).tileType = Assets.VOID;
        selectionUI.get(1).tileType = Assets.WATER;
        selectionUI.get(2).tileType = Assets.LIFE;
        selectionUI.get(3).tileType = Assets.GRASS;
        selectedTile = selectionUI.get(0);
    }

    public void tick(){
        for(Tiles t : selectionUI){
            if(t.getBounds().contains(handler.getMM().getMouseX(),handler.getMM().getMouseY())){
                if(handler.getMM().isLeftClicked()){
                    selectedTile = t;
                }
                t.getBounds().x = t.cords[0] + 10;
            }else{
                t.getBounds().x = t.cords[0];
            }
        }
    }

    public void render(Graphics g){
        for(Tiles t : selectionUI){
            t.render(g);
            g.setColor(Color.BLACK);
            g.drawRect(t.getBounds().x,t.getBounds().y,t.getBounds().width,t.getBounds().height);
        }
        g.setColor(Color.WHITE);
        g.drawRect(selectedTile.getBounds().x,selectedTile.getBounds().y,selectedTile.getBounds().width,selectedTile.getBounds().height);
    }

    public ArrayList<Tiles> getSelectionUI() {
        return selectionUI;
    }
}
