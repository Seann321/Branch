package states.dataState;

import branch.Branch;
import controls.KeyManager;
import gfx.GUI;
import gfx.UIObject;
import states.ConnectState;
import states.DataState;
import states.Handler;
import states.States;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static states.DataState.CurrentCustomer;
import static states.DataState.client;

public class EditCustomer extends States implements Serializable {

    private String input = "";

    int downHeight = GUI.font35.getSize();
    int widthMovement = Branch.WIDTH / 4;
    Background background;

    private ArrayList<UIObject> guiStuff = new ArrayList<>();
    private ArrayList<UIObject> mediaTypeGUI = new ArrayList<>();
    private ArrayList<UIObject> mediaAmountGUI = new ArrayList<>();
    UIObject customerName = new UIObject(DataState.CurrentCustomer.getName(), Branch.WIDTH / 2, GUI.font50.getSize(), true, Color.white, Color.ORANGE, GUI.font100, guiStuff);
    UIObject customerAddress = new UIObject(DataState.CurrentCustomer.getAddress(), 10, (GUI.font50.getSize() * 2) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font35, guiStuff);

    UIObject creationDate = new UIObject(DataState.CurrentCustomer.date, Branch.WIDTH, GUI.font35.getSize(), false, true, Color.white, Color.ORANGE, GUI.font35, guiStuff);
    UIObject delete = new UIObject("DELETE", Branch.WIDTH - 5, Branch.HEIGHT - 5 - GUI.font35.getSize(), false, true, Color.RED, Color.ORANGE, GUI.font35, guiStuff);

    UIObject customerEmail = new UIObject(DataState.CurrentCustomer.getEmail(), 10, (GUI.font50.getSize() * 3) + 5 + downHeight , false, Color.white, Color.ORANGE, GUI.font35, guiStuff);
    UIObject customerPhone = new UIObject(DataState.CurrentCustomer.getPhone(), 10, (GUI.font50.getSize() * 4) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font35, guiStuff);
    UIObject progress = new UIObject("IN PROGRESS", 10, GUI.font35.getSize(), false, Color.yellow, Color.yellow, GUI.font35);

    UIObject status = new UIObject("Notes", Branch.WIDTH / 2, (GUI.font50.getSize() * 15) + 5 + downHeight, true, Color.red, Color.red, GUI.font35, mediaTypeGUI);
    UIObject notes = new UIObject(DataState.CurrentCustomer.notes[0], Branch.WIDTH / 2, (GUI.font50.getSize() * 16) + 5 + downHeight, true, Color.white, Color.ORANGE, GUI.font35, guiStuff);
    UIObject notes2 = new UIObject(DataState.CurrentCustomer.notes[1], Branch.WIDTH / 2, (GUI.font50.getSize() * 17) + 5 + downHeight, true, Color.white, Color.ORANGE, GUI.font35, guiStuff);

    UIObject goingTo = new UIObject("Going To", Branch.WIDTH / 2, (GUI.font50.getSize() * 18) + 5 + downHeight, true, Color.red, Color.red, GUI.font35, mediaTypeGUI);
    UIObject dvd = new UIObject(DataState.CurrentCustomer.getMediaType("dvd"), Branch.WIDTH / 2, (GUI.font50.getSize() * 19) + 5 + downHeight, true, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject fd = new UIObject(DataState.CurrentCustomer.getMediaType("fd"), (Branch.WIDTH / 2) - 275, (GUI.font50.getSize() * 19) + 5 + downHeight, true, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject cd = new UIObject(DataState.CurrentCustomer.getMediaType("cd"), (Branch.WIDTH / 2) + 275, (GUI.font50.getSize() * 19) + 5 + downHeight, true, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);

    UIObject tapes = new UIObject("Tapes", 0, (GUI.font50.getSize() * 6) + 5 + downHeight, false, Color.red, Color.red, GUI.font50, mediaTypeGUI);
    UIObject VHS = new UIObject(DataState.CurrentCustomer.getMediaType("VHS"), 0, (GUI.font50.getSize() * 7) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject VHSC = new UIObject(DataState.CurrentCustomer.getMediaType("VHS-C"), 0, (GUI.font50.getSize() * 8) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject MM8 = new UIObject(DataState.CurrentCustomer.getMediaType("8MM"), 0, (GUI.font50.getSize() * 9) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject MM6 = new UIObject(DataState.CurrentCustomer.getMediaType("6MM"), 0, (GUI.font50.getSize() * 10) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject BETACAM = new UIObject(DataState.CurrentCustomer.getMediaType("BETA/CAM"), 0, (GUI.font50.getSize() * 11) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject UMATIC = new UIObject(DataState.CurrentCustomer.getMediaType("UMATIC"), 0, (GUI.font50.getSize() * 12) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);

    UIObject audio = new UIObject("Audio", widthMovement, (GUI.font50.getSize() * 6) + 5 + downHeight, false, Color.red, Color.red, GUI.font50, mediaTypeGUI);
    UIObject LP = new UIObject(DataState.CurrentCustomer.getMediaType("LP"), widthMovement, (GUI.font50.getSize() * 7) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject CASSETTE = new UIObject(DataState.CurrentCustomer.getMediaType("CASSETTE"), widthMovement, (GUI.font50.getSize() * 8) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject REEL = new UIObject(DataState.CurrentCustomer.getMediaType("REEL"), widthMovement, (GUI.font50.getSize() * 9) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);


    UIObject pictures = new UIObject("Pictures", widthMovement * 2, (GUI.font50.getSize() * 6) + 5 + downHeight, false, Color.red, Color.red, GUI.font50, mediaTypeGUI);
    UIObject PHOTO = new UIObject(DataState.CurrentCustomer.getMediaType("PHOTOS"), widthMovement * 2, (GUI.font50.getSize() * 7) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject PHOTOBOX = new UIObject(DataState.CurrentCustomer.getMediaType("PHOTOBOX"), widthMovement * 2, (GUI.font50.getSize() * 8) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject ALBUM = new UIObject(DataState.CurrentCustomer.getMediaType("ALBUM"), widthMovement * 2, (GUI.font50.getSize() * 9) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject SLIDES = new UIObject(DataState.CurrentCustomer.getMediaType("SLIDES"), widthMovement * 2, (GUI.font50.getSize() * 10) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject NEG = new UIObject(DataState.CurrentCustomer.getMediaType("NEG"), widthMovement * 2, (GUI.font50.getSize() * 11) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);


    UIObject film = new UIObject("Film", widthMovement * 3, (GUI.font50.getSize() * 6) + 5 + downHeight, false, Color.red, Color.red, GUI.font50, mediaTypeGUI);
    UIObject MM8F = new UIObject(DataState.CurrentCustomer.getMediaType("FILM_8MM"), widthMovement * 3, (GUI.font50.getSize() * 7) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject MM16F = new UIObject(DataState.CurrentCustomer.getMediaType("FILM_16MM"), widthMovement * 3, (GUI.font50.getSize() * 8) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);

    UIObject DVD = new UIObject(DataState.CurrentCustomer.getMediaType("DVD"), 0, (GUI.font50.getSize() * 14) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject FD = new UIObject(DataState.CurrentCustomer.getMediaType("FD"), widthMovement, (GUI.font50.getSize() * 14) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject SD = new UIObject(DataState.CurrentCustomer.getMediaType("SD"), widthMovement * 2, (GUI.font50.getSize() * 14) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);
    UIObject HD = new UIObject(DataState.CurrentCustomer.getMediaType("HD"), widthMovement * 3, (GUI.font50.getSize() * 14) + 5 + downHeight, false, Color.white, Color.ORANGE, GUI.font50, mediaAmountGUI);

    private UIObject activeUIObject = null;

    private boolean deleteConfirm = false;

    public EditCustomer(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        background = new Background(handler);
        addText(gui, progress);
        for (UIObject u : guiStuff) {
            addText(gui, u);
        }
        for (UIObject u : mediaTypeGUI) {
            addText(gui, u);
        }
        for (UIObject u : mediaAmountGUI) {
            addText(gui, u);
        }
    }

    @Override
    public void tick() {
        background.tick();
        updateMediaText();
        gui.tick();
        deleteCustomer();
        if (progress.wasClicked()) {
            CurrentCustomer.currentStatus++;
            if (CurrentCustomer.currentStatus == CustomerUpdated.Status.length)
                CurrentCustomer.currentStatus = 0;
        }if (progress.wasRightClicked()) {
            CurrentCustomer.currentStatus--;
            if (CurrentCustomer.currentStatus == -1)
                CurrentCustomer.currentStatus = CustomerUpdated.Status.length - 1;
        }
        progress.setText(CustomerUpdated.Status[CurrentCustomer.currentStatus]);
        if (CurrentCustomer.currentStatus == CustomerUpdated.Status.length - 1) {
            progress.setAllColors(Color.green);
        } else if (CurrentCustomer.currentStatus == 0) {
            progress.setAllColors(Color.white);
        } else {
            progress.setAllColors(Color.yellow);
        }
        if (activeUIObject == null) {
            for (UIObject o : mediaAmountGUI) {
                if (o.isHovering() && handler.getMM().isWheelDown()) {
                    String[] temp = o.text.split(" ");
                    if (DataState.CurrentCustomer.getMediaAmount(temp[0]) <= 0) {
                        DataState.CurrentCustomer.setMediaAmount(0, temp[0]);
                    } else {
                        DataState.CurrentCustomer.setMediaAmount(DataState.CurrentCustomer.getMediaAmount(temp[0]) - 1, temp[0]);
                    }
                }
                if (o.isHovering() && handler.getMM().isWheelUp()) {
                    String[] temp = o.text.split(" ");
                    DataState.CurrentCustomer.setMediaAmount(DataState.CurrentCustomer.getMediaAmount(temp[0]) + 1, temp[0]);
                }
                if (o.wasClicked()) {
                    String[] temp = o.text.split(" ");
                    DataState.CurrentCustomer.setMediaAmount(DataState.CurrentCustomer.getMediaAmount(temp[0]) + 1, temp[0]);
                }
                if (o.wasRightClicked()) {
                    String[] temp = o.text.split(" ");
                    if (DataState.CurrentCustomer.getMediaAmount(temp[0]) <= 0) {
                        DataState.CurrentCustomer.setMediaAmount(0, temp[0]);
                    } else {
                        DataState.CurrentCustomer.setMediaAmount(DataState.CurrentCustomer.getMediaAmount(temp[0]) - 1, temp[0]);
                    }
                }
            }
            updateTextInfo();
        }
        editText();
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE) && KeyManager.LockInput) {
            DataState.SaveArray();
            pushUpdate();
            handler.switchToState(Branch.DataState);
        }
    }

    private void pushUpdate() {
        if (!ConnectState.connectIP.equals("")) {
            client.uploadFile();
        }
    }


    private void deleteCustomer() {
        if (!deleteConfirm && delete.wasClicked()) {
            deleteConfirm = true;
            delete.setText("ARE YOU SURE *CAN NOT UNDO*");
        }
        if (deleteConfirm && delete.wasClicked()) {
            deleteConfirm = true;
            DataState.Customers.remove(DataState.CurrentCustomer);
            DataState.NameMatches.remove(DataState.CurrentCustomer);
            CurrentCustomer.toBeDeleted = true;
            handler.switchToState(Branch.DataState);
            if (!ConnectState.connectIP.equals("")) {
                client.uploadFile();
            } else {
                DataState.SaveArray();

            }
        }
    }

    private void editText() {
        if (activeUIObject == null) {
            for (UIObject u : guiStuff) {
                if (u.wasClicked()) {
                    setActiveUIObject(u);
                }
            }
            if (activeUIObject == null) {
                return;
            }
        }
        activeUIObject.tick();
        getKeyInput();
        activeUIObject.setText(input);
        boolean clickEnter = false;
        UIObject clicked = null;
        for (UIObject u : guiStuff) {
            if (u.wasClicked()) {
                clickEnter = true;
                clicked = u;
            }
        }
        for (UIObject u : mediaAmountGUI) {
            if (u.wasClicked()) {
                clickEnter = true;
            }
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ENTER) || clickEnter) {
            input = "";
            activeUIObject.resetColors();
            KeyManager.LockInput = true;
            newTextInfo();
            activeUIObject = null;
            for (UIObject u : guiStuff) {
                u.clicked = false;
            }
            if (clicked != null) {
                setActiveUIObject(clicked);
            }
        }
    }

    void setActiveUIObject(UIObject u) {
        input = u.getText();
        activeUIObject = u;
        u.setAllColors(Color.ORANGE);
        KeyManager.LockInput = false;
    }

    private void updateMediaText() {
        VHS.setText(DataState.CurrentCustomer.getMediaType("VHS"));
        VHSC.setText(DataState.CurrentCustomer.getMediaType("VHS-C"));
        MM8.setText(DataState.CurrentCustomer.getMediaType("8MM"));
        MM6.setText(DataState.CurrentCustomer.getMediaType("6MM"));
        BETACAM.setText(DataState.CurrentCustomer.getMediaType("BETA/CAM"));
        UMATIC.setText(DataState.CurrentCustomer.getMediaType("UMATIC"));
        LP.setText(DataState.CurrentCustomer.getMediaType("LP"));
        CASSETTE.setText(DataState.CurrentCustomer.getMediaType("CASSETTE"));
        REEL.setText(DataState.CurrentCustomer.getMediaType("REEL"));
        PHOTO.setText(DataState.CurrentCustomer.getMediaType("PHOTOS"));
        ALBUM.setText(DataState.CurrentCustomer.getMediaType("ALBUM"));
        SLIDES.setText(DataState.CurrentCustomer.getMediaType("SLIDES"));
        NEG.setText(DataState.CurrentCustomer.getMediaType("NEG"));
        MM8F.setText(DataState.CurrentCustomer.getMediaType("FILM_8MM"));
        MM16F.setText(DataState.CurrentCustomer.getMediaType("FILM_16MM"));
        DVD.setText(DataState.CurrentCustomer.getMediaType("DVD"));
        FD.setText(DataState.CurrentCustomer.getMediaType("FD"));
        SD.setText(DataState.CurrentCustomer.getMediaType("SD"));
        HD.setText(DataState.CurrentCustomer.getMediaType("HD"));
        PHOTOBOX.setText(DataState.CurrentCustomer.getMediaType("PHOTOBOX"));
        dvd.setText(DataState.CurrentCustomer.getMediaType("dvd"));
        cd.setText(DataState.CurrentCustomer.getMediaType("cd"));
        fd.setText(DataState.CurrentCustomer.getMediaType("fd"));
    }

    private void newTextInfo() {
        for (UIObject u : guiStuff) {
            if (u.getText().length() <= 1) {
                u.setText("DUMMY");
            }
        }
        DataState.CurrentCustomer.setName(customerName.text);
        DataState.CurrentCustomer.setAddress(customerAddress.text);
        DataState.CurrentCustomer.setEmail(customerEmail.text);
        DataState.CurrentCustomer.setPhone(customerPhone.text);
        DataState.CurrentCustomer.date = creationDate.text;
        DataState.CurrentCustomer.notes[0] = notes.text;
        DataState.CurrentCustomer.notes[1] = notes2.text;
    }

    private void updateTextInfo() {
        customerName.setText(DataState.CurrentCustomer.getName());
        customerAddress.setText(DataState.CurrentCustomer.getAddress());
        customerEmail.setText(DataState.CurrentCustomer.getEmail());
        customerPhone.setText(DataState.CurrentCustomer.getPhone());
        creationDate.setText(DataState.CurrentCustomer.date);
        notes.setText(DataState.CurrentCustomer.notes[0]);
        notes2.setText(DataState.CurrentCustomer.notes[1]);
    }

    private void getKeyInput() {
        if (KeyManager.LockInput) {
            return;
        }

        input += KeyManager.GetKeyLastTyped();
        if(handler.getKM().shift && handler.getKM().keyJustPressed(KeyEvent.VK_BACK_SPACE)){
            if(input.length() > 5)
            input = input.substring(0, input.length() - 5);
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_DELETE)) {
            input = "";
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_BACK_SPACE)) {
            if (input.length() == 0) {
                return;
            }
            input = input.substring(0, input.length() - 1);
        }
    }

    @Override
    public void reset() {
        deleteConfirm = false;
        delete.setText("DELETE");
    }

    @Override
    public void render(Graphics g) {
        background.render(g);
        gui.render(g);
    }
}
