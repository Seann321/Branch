package states.gameState;

import branch.Branch;
import gfx.image.Assets;
import states.GameState;
import states.Handler;

import java.awt.*;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;
    private ArrayList<InventorySlot> inventorySlots = new ArrayList<>();
    private int baseHeight = Branch.HEIGHT - GameState.TileSize;
    private Rectangle invBounds;
    private Rectangle marshBounds = new Rectangle(0, baseHeight, GameState.TileSize / 2, GameState.TileSize / 2);
    private Rectangle grassBounds = new Rectangle(0, baseHeight, GameState.TileSize / 2, GameState.TileSize / 2);
    public Tiles.TileType selectedTile = Tiles.TileType.GRASS;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventorySlots.add(new InventorySlot(grassBounds, Tiles.TileType.GRASS, true));
        inventorySlots.add(new InventorySlot(marshBounds, Tiles.TileType.MARSH));
        placeWidth();
    }

    private void placeWidth() {
        int i = 0;
        for (InventorySlot r : inventorySlots) {
            r.rect.x = (Branch.WIDTH / 2) - (GameState.TileSize / 3 * inventorySlots.size()) + ((int) (GameState.TileSize / 1.5) * i);
            i++;
        }
        invBounds = new Rectangle((Branch.WIDTH / 2) - (GameState.TileSize / 3 * inventorySlots.size()) - ((int) (GameState.TileSize / 1.5)) + (GameState.TileSize/2),
                baseHeight,(GameState.TileSize / 2) * (i+1), GameState.TileSize / 2);
    }

    public void tick() {
        for (InventorySlot is : inventorySlots) {
            moveImageUp(is.rect);
        }
        setSelectedTile();
    }

    private void moveImageUp(Rectangle r) {
        if (r.contains(handler.getMM().getMouseX(), handler.getMM().getMouseY())) {
            r.y = baseHeight - GameState.TileSize / 10;
        } else {
            r.y = baseHeight;
        }
    }

    private void setSelectedTile() {
        for (InventorySlot is : inventorySlots) {
            if (is.rect.contains(handler.getMM().getMouseX(), handler.getMM().getMouseY()) && handler.getMM().isLeftClicked()) {
                selectedTile = is.tileType;
                for (InventorySlot is2 : inventorySlots) {
                    is2.selected = false;
                }
                is.selected = true;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(255,255,255,50));
        g.fillRect(invBounds.x, invBounds.y, invBounds.width, invBounds.height);
        for (InventorySlot is : inventorySlots) {
            g.drawImage(Tiles.getTileImage(is.tileType), is.rect.x, is.rect.y, is.rect.width, is.rect.height, null);
            if (is.selected) {
                g.drawImage(Assets.Selected, is.rect.x, is.rect.y, is.rect.width, is.rect.height, null);
            }
        }
    }

}

class InventorySlot {

    public Rectangle rect;
    public Tiles.TileType tileType;
    public boolean selected = false;

    public InventorySlot(Rectangle rect, Tiles.TileType tileType) {
        this.rect = rect;
        this.tileType = tileType;
    }

    public InventorySlot(Rectangle rect, Tiles.TileType tileType, boolean selected) {
        this.rect = rect;
        this.selected = selected;
        this.tileType = tileType;
    }


}
