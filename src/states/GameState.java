package states;

import branch.Branch;
import gfx.GUI;
import gfx.UIObject;
import gfx.image.Assets;
import states.gameState.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameState extends States {

    //GUITextStuff
    private ArrayList<UIObject> guiStuff = new ArrayList<>();

    public static Map<String, Tiles> TilesMap = new HashMap<>();
    Random rand = new Random();
    public static int TileSize = 120;
    public static int TileWidth = (Branch.WIDTH / TileSize);
    public static int TileHeight = (Branch.HEIGHT / TileSize);
    public static Player Player;
    public static Camera Camera;

    public GameState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        for (UIObject x : guiStuff) {
            addText(gui, x);
        }
        createTiles();
        Player = new Player(handler, new Rectangle(TileWidth / 2 * TileSize, TileHeight / 2 * TileSize, (int) (TileSize * .6), (int) (TileSize * .6)));
        Camera = new Camera(Player);
        Tiles tempPlayerTile = null;
    }

    void createTiles() {
        for (int i = 0; i < TileWidth + 10; i++) {
            for (int ii = 0; ii < TileHeight + 10; ii++) {
                String key = "X" + i + "Y" + ii;
                TilesMap.put(key, new Tiles(new Rectangle(i * TileSize, ii * TileSize, TileSize, TileSize), new int[]{i, ii}, handler));
            }
        }
    }

    @Override
    public void tick() {
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)){
            handler.switchToMenuState();;
        }
        gui.tick();
        Player.tick();
        Camera.tick();
        for (Tiles t : TilesMap.values()) {
            t.tick();
        }
    }

    public Tiles randomTile(Tiles.TileType a){
        ArrayList<Tiles> randomTiles = new ArrayList<>();
        for(Tiles t : TilesMap.values()){
            if(t.tileType == a){
                randomTiles.add(t);
            }
        }
        if(randomTiles.size() != 0){
            return randomTiles.get(rand.nextInt(randomTiles.size()));
        }
        return null;
    }

    public Color randomColor() {
        return new Color(rand.nextInt(254), rand.nextInt(254), rand.nextInt(254));
    }

    public String randomCords() {
        int x = rand.nextInt(TileWidth);
        int y = rand.nextInt(TileHeight);
        return "X" + x + "Y" + y;
    }


    @Override
    public void render(Graphics g) {
        for (Tiles t : TilesMap.values()) {
            t.render(g);
        }
        gui.render(g);
        Player.render(g);
    }
}
