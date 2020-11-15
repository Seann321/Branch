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
    UIObject grassPoints = new UIObject("Grass Points: ", 0, GUI.font50.getSize(), false, Color.green, Color.green, GUI.font50, guiStuff);

    public static Map<String, Tiles> TilesMap = new HashMap<>();
    Random rand = new Random();
    public static int TileSize = 120;
    public static int TileWidth = (Branch.WIDTH / TileSize);
    public static int TileHeight = (Branch.HEIGHT / TileSize);
    public static Player Player;
    public static Camera Camera;
    private Shop shop;

    public GameState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        shop = new Shop(handler,gui);
        for (UIObject x : guiStuff) {
            addText(gui, x);
        }
        createTiles();
        Player = new Player(handler, new Rectangle(TileWidth / 2 * TileSize, TileHeight / 2 * TileSize, (int) (TileSize * .6), (int) (TileSize * .6)));
        Camera = new Camera(Player);
        Tiles tempPlayerTile = null;
        for (Tiles t : TilesMap.values()) {
            if (t.bounds.intersects(Player.bounds)) {
                tempPlayerTile = t;
            }
        }
        tempPlayerTile.changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] + 1) + "Y" + (tempPlayerTile.cords[1])).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] - 1) + "Y" + (tempPlayerTile.cords[1])).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0]) + "Y" + ((tempPlayerTile.cords[1]) + 1)).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0]) + "Y" + ((tempPlayerTile.cords[1]) - 1)).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] - 1) + "Y" + ((tempPlayerTile.cords[1]) - 1)).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] + 1) + "Y" + ((tempPlayerTile.cords[1]) - 1)).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] - 1) + "Y" + ((tempPlayerTile.cords[1]) + 1)).changeImage(Assets.Grass);
        TilesMap.get("X" + (tempPlayerTile.cords[0] + 1) + "Y" + ((tempPlayerTile.cords[1]) + 1)).changeImage(Assets.Grass);
        //buildIsland();
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
        spreadGrass();
        updateGUI();
        if (handler.getKM().left && handler.getKM().right) {
            Player.GrassPoints++;
        }
        shop.tick();
        if(!TutorialEnded){
            return;
        }
        shopItems();
    }

    boolean shopMade = false;
    public void shopItems(){
        if(!shopMade && Player.GrassPoints >= 5){
            randomTile(Tiles.TileType.GRASS).changeImage(Assets.Shop);
            shopMade = true;
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

    public void updateGUI() {
        grassPoints.setText("Grass Points: " + Player.GrassPoints);
    }

    public static boolean TutorialEnded = false;

    public void buildIsland() {
        for (Tiles t : TilesMap.values()) {
            if (t.tileType == Tiles.TileType.WATER) {
                if (validMove(Tiles.TileType.GRASS, t)) {
                    t.changeImage(Assets.Sand);
                }
            }
            if(t.tileType == Tiles.TileType.MARSH){
                if (!validMove(Tiles.TileType.WATER, t)) {
                    t.changeImage(Assets.Grass);
                }
            }
        }
    }

    public void spreadGrass() {
        if (TutorialEnded) {
            if (Player.GrassPoints > 0) {
                for (Tiles t : TilesMap.values()) {
                    {
                        if (t.bounds.contains(Player.bounds) && t.tileType == Tiles.TileType.SAND) {
                            if (validMove(Tiles.TileType.GRASS, t) || validMove(Tiles.TileType.FLOWER,t) || validMove(Tiles.TileType.SHOP,t)) {
                                t.changeImage(Tiles.getTileImage(Player.getInventory().selectedTile));
                                Player.GrassPoints--;
                                buildIsland();
                            }
                        }
                    }
                }
                return;
            }
        }
        if (Player.GrassPoints > 0)
            for (Tiles t : TilesMap.values()) {
                if (t.bounds.contains(Player.bounds) && t.tileType == Tiles.TileType.WATER) {
                    if (validMove(Tiles.TileType.GRASS, t)) {
                        t.changeImage(Assets.Grass);
                        Player.GrassPoints--;
                        if (Player.GrassPoints == 0 && !TutorialEnded) {
                            TutorialEnded = true;
                            buildIsland();
                        }
                    }
                }
            }
    }

    public boolean validMove(Tiles.TileType a, Tiles startTile) {
        if (TilesMap.get("X" + (startTile.cords[0] + 1) + "Y" + (startTile.cords[1])) != null) {
            if (TilesMap.get("X" + (startTile.cords[0] + 1) + "Y" + (startTile.cords[1])).tileType == a)
                return true;
        }
        if (TilesMap.get("X" + (startTile.cords[0] - 1) + "Y" + (startTile.cords[1])) != null) {
            if (TilesMap.get("X" + (startTile.cords[0] - 1) + "Y" + (startTile.cords[1])).tileType == a)
                return true;
        }
        if (TilesMap.get("X" + (startTile.cords[0]) + "Y" + ((startTile.cords[1]) + 1)) != null) {
            if (TilesMap.get("X" + (startTile.cords[0]) + "Y" + ((startTile.cords[1]) + 1)).tileType == a)
                return true;
        }
        if (TilesMap.get("X" + (startTile.cords[0]) + "Y" + ((startTile.cords[1]) - 1)) != null) {
            if (TilesMap.get("X" + (startTile.cords[0]) + "Y" + ((startTile.cords[1]) - 1)).tileType == a)
                return true;
        }
        return false;
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
        shop.render(g);
    }
}
