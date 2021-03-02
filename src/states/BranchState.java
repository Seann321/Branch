package states;

import branch.Branch;
import gfx.GUI;
import gfx.UIObject;
import gfx.image.Assets;
import states.branchState.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class BranchState extends States {

    //GUITextStuff
    private ArrayList<UIObject> guiStuff = new ArrayList<>();

    public static ArrayList<Life> Life = new ArrayList<>();
    public static ArrayList<Life> NewLife = new ArrayList<>();
    public static ArrayList<Tiles> TilesMap = new ArrayList<>();
    Random rand = new Random();
    public static int TileSize = 120;
    public static int TileWidth = (Branch.WIDTH / TileSize);
    public static int TileHeight = (Branch.HEIGHT / TileSize);
    private static Player Player;
    public static Camera Camera;
    private static WorldBuilder worldBuilder;

    public BranchState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        for (UIObject x : guiStuff) {
            addText(gui, x);
        }
        createTiles();
        Player = new Player(handler, new Rectangle(TileWidth / 2 * TileSize, TileHeight / 2 * TileSize, (int) (TileSize * .6), (int) (TileSize * .6)));
        Camera = new Camera(Player);
        worldBuilder = new WorldBuilder(handler);
    }

    void createTiles() {
        for (int i = 0; i < TileWidth + 10; i++) {
            for (int ii = 0; ii < TileHeight + 10; ii++) {
                TilesMap.add(new Tiles(new Rectangle(i * TileSize, ii * TileSize, TileSize, TileSize), new int[]{i, ii}, handler));
            }
        }
    }

    public static Tiles getTileAtCords(int[] cords) {
        for (Tiles t : TilesMap) {
            if (t.getBounds().contains(cords[0], cords[1])) {
                return t;
            }
        }
        return TilesMap.get(0);
    }

    @Override
    public void tick() {
        Life.clear();
        Life.addAll(NewLife);
        if (handler.getMM().isLeftPressed()) {
            if (worldBuilder.selectedTile.tileType == Assets.LIFE) {
                NewLife.add(new Life(handler, new Rectangle(handler.getMM().getMouseX() - Camera.getX(), handler.getMM().getMouseY() - Camera.getY(), 12, 20)));
            } else {

                for (Tiles t : TilesMap) {
                    if (t.getBounds().contains(handler.getMM().getMouseX(), handler.getMM().getMouseY())) {
                        for (Tiles tt : worldBuilder.getSelectionUI()) {
                            if (tt.getBounds().contains(handler.getMM().getMouseX(), handler.getMM().getMouseY())) {
                                return;
                            }
                        }
                        t.tileType = worldBuilder.selectedTile.tileType;
                    }
                }
            }
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            handler.switchToState(Branch.MenuState);
        }
        gui.tick();
        worldBuilder.tick();
        Player.tick();
        Camera.tick();
        for (Tiles t : TilesMap) {
            t.tick();
        }
        for (Life l : Life) {
            l.tick();
            if(!l.active){
                NewLife.remove(l);
            }
        }
    }

    public static Color randomColor() {
        Random randomColor = new Random();
        return new Color(randomColor.nextInt(254), randomColor.nextInt(254), randomColor.nextInt(254));
    }

    public String randomCords() {
        int x = rand.nextInt(TileWidth);
        int y = rand.nextInt(TileHeight);
        return "X" + x + "Y" + y;
    }


    @Override
    public void render(Graphics g) {
        for (Tiles t : TilesMap) {
            t.render(g);
        }
        gui.render(g);
        Player.render(g);
        for (Life l : Life) {
            l.render(g);
        }
        worldBuilder.render(g);
    }
}
