package states.menuState;

import branch.Branch;
import gfx.GUI;
import gfx.UIObject;
import gfx.sounds.SoundManager;
import states.Handler;
import states.States;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OptionsScreen extends States {

    UIObject volumeSwitcher = new UIObject("Volume: .5", Branch.WIDTH/2, Branch.HEIGHT/2, true, Color.white, Color.lightGray, GUI.font100);

    public OptionsScreen(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        gui.addText(volumeSwitcher);
    }

    @Override
    public void tick() {
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)){
            handler.switchToState(Branch.MenuState);
        }
        if(volumeSwitcher.wasRightClicked()){
            SoundManager.Volume-=.1;
            if(SoundManager.Volume < 0){
                SoundManager.Volume = 0;
            }
        }if(volumeSwitcher.wasClicked()){
            SoundManager.Volume+=.1;
            if(SoundManager.Volume > 1){
                SoundManager.Volume = 1;
            }
        }
        double roundOff = Math.round(SoundManager.Volume * 10.0) / 10.0;
        volumeSwitcher.setText("Volume: " + roundOff);
    }

    @Override
    public void render(Graphics g) {
        gui.render(g);
    }
}
