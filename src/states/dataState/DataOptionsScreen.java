package states.dataState;

import branch.Branch;
import branch.Display;
import gfx.GUI;
import gfx.UIObject;
import states.Handler;
import states.States;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class DataOptionsScreen extends States implements Serializable {

    private ArrayList<UIObject> guiStuff = new ArrayList<>();
    private static Boolean[] Settings = new Boolean[2];

    Background background;
    UIObject dotsToggle = new UIObject("TOGGLE BACKGROUND DOTS", Branch.WIDTH/2, Branch.HEIGHT/4, true, Color.white, Color.orange, GUI.font50, guiStuff);
    UIObject backgroundToggle = new UIObject("TOGGLE BACKGROUND COLOR", Branch.WIDTH/2, Branch.HEIGHT/4 + GUI.font50.getSize(), true, Color.white, Color.orange, GUI.font50, guiStuff);
    UIObject returnToMenu = new UIObject("RETURN TO MENU", Branch.WIDTH/2, Branch.HEIGHT/4 + (GUI.font50.getSize() * 3), true, Color.white, Color.orange, GUI.font50, guiStuff);

    public DataOptionsScreen(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        for(UIObject o : guiStuff){
            gui.addText(o);
        }
        background = new Background(handler);
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("Settings.ser");
            in = new ObjectInputStream(fis);
            Settings = (Boolean[]) in.readObject();
            in.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println("No Settings Found");
            Settings[0] = true;
            Settings[1] = true;
        }
        init();
    }

    void init(){
        if(Settings[1]){
            Display.Canvas.setBackground(Color.black);
        }else{
            Display.Canvas.setBackground(Color.darkGray.darker());
        }
        Background.active = Settings[0];
    }

    @Override
    public void tick() {
        gui.tick();
        background.tick();
        if(dotsToggle.wasClicked()){
            Settings[0] = !Settings[0];
        }
        if(backgroundToggle.wasClicked()){
            Settings[1] = !Settings[1];
        }
        if(handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE) || returnToMenu.wasClicked()){
            handler.switchToState(Branch.DataState);
            SaveArray();
        }
        if(Settings[1]){
            Display.Canvas.setBackground(Color.black);
        }else{
            Display.Canvas.setBackground(Color.darkGray.darker());
        }
        Background.active = Settings[0];
    }

    public static void SaveArray() {
        try {
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Settings.ser"));
            out.writeObject(Settings);
            out.close();

            // Serialize data object to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(Settings);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();
        } catch (IOException e) {
        }
    }

    @Override
    public void render(Graphics g) {
        background.render(g);
        gui.render(g);
    }
}
