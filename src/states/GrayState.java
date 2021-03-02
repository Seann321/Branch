package states;

import branch.Branch;
import branch.Main;
import gfx.GUI;
import gfx.sounds.SoundManager;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GrayState extends States {

    public static ArrayList<Rectangle> rectangles = new ArrayList<>();
    Random random = new Random();

    public GrayState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        int squareScale = 50;
        for (int i = 0; i < Main.ScreenSize.width / squareScale; i++) {
            for (int ii = 0; ii < Main.ScreenSize.height / squareScale; ii++) {
                rectangles.add(new Rectangle(i * squareScale, ii * squareScale, squareScale, squareScale));
            }
        }
    }

    @Override
    public void tick() {
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)){
            handler.switchToState(Branch.MenuState);
        }
        try {
            SoundManager.tone(random.nextInt(5000),1,SoundManager.Volume);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        for (Rectangle r : rectangles) {
            Color randomColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255),random.nextInt(255));
            g.setColor(randomColor);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
    }
}
