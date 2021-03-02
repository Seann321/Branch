package states.menuState;

import branch.Branch;
import branch.Main;
import gfx.GUI;
import gfx.image.Assets;
import states.Handler;
import states.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class SelectorScreen extends MenuState implements Serializable {

    public static ArrayList<Rectangle> rectangles = new ArrayList<>();
    int squareScale = 100;
    int smallSquareScale = 100 / 3;
    Random random = new Random();
    Rectangle grayStateRectangle = new Rectangle(0, 0, 0, 0);

    public SelectorScreen(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        for (int i = 0; i < Main.ScreenSize.width / squareScale; i++) {
            for (int ii = 0; ii < Main.ScreenSize.height / squareScale; ii++) {
                rectangles.add(new Rectangle(i * squareScale, ii * squareScale, squareScale, squareScale));
                if(i == 1 && ii == 0){
                    grayStateRectangle = rectangles.get(rectangles.size()-1);
                }
            }
        }
    }

    @Override
    public void tick() {
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            handler.switchToState(Branch.MenuState);
        }
        Rectangle mousePOS = new Rectangle(handler.getMM().getMouseX(), handler.getMM().getMouseY());
        if (rectangles.get(0).contains(mousePOS) && handler.getMM().isLeftPressed()) {
            handler.switchToState(Branch.BranchState);
        }
        if (grayStateRectangle.intersects(mousePOS) && handler.getMM().isLeftPressed()) {
            handler.switchToState(Branch.GrayState);
        }
    }

    @Override
    public void render(Graphics g) {
        //BranchState
        g.drawImage(Assets.ISLAND, rectangles.get(0).x, rectangles.get(0).y, rectangles.get(0).width, rectangles.get(0).height, null);
        //GrayState
        for (int i = 3; i < 6; i++) {
            for (int ii = 0; ii < 3; ii++) {
                Color randomColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), random.nextInt(255));
                g.setColor(randomColor);
                g.fillRect(i * smallSquareScale, ii * smallSquareScale, smallSquareScale, smallSquareScale);
            }
        }
    }
}
