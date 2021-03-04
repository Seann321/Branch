package states;

import branch.Branch;
import branch.Display;
import controls.KeyManager;
import gfx.GUI;
import gfx.UIObject;
import states.dataState.Background;
import states.dataState.Customer;

import javax.naming.Name;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DataState extends States implements Serializable {

    private Background background;

    private ArrayList<UIObject> guiStuff = new ArrayList<>();
    private ArrayList<UIObject> activeSearch = new ArrayList<>();
    UIObject lookUp = new UIObject("Lookup Customer", Branch.WIDTH / 2, Branch.HEIGHT / 4 - GUI.font100.getSize(), true, Color.white, Color.ORANGE, GUI.font100, guiStuff);
    UIObject showCompleted = new UIObject("Hide Completed", Branch.WIDTH / 2, Branch.HEIGHT / 4 - GUI.font50.getSize() + 10, true, Color.white, Color.ORANGE, GUI.font50, guiStuff);
    UIObject enterNew = new UIObject("Enter New Customer", Branch.WIDTH / 2, Branch.HEIGHT / 4, true, Color.white, Color.ORANGE, GUI.font100, guiStuff);
    UIObject customer1 = new UIObject("1", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer2 = new UIObject("2", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 2 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer3 = new UIObject("3", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 3 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer4 = new UIObject("4", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 4 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer5 = new UIObject("5", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 5 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer6 = new UIObject("6", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 6 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer7 = new UIObject("7", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 7 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer8 = new UIObject("8", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 8 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer9 = new UIObject("9", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 9 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject customer10 = new UIObject("10", 10, Branch.HEIGHT / 4 + GUI.font100.getSize() + GUI.font50.getSize() * 10 + 10, false, Color.white, Color.ORANGE, GUI.font35, activeSearch);
    UIObject currentInput = new UIObject("", Branch.WIDTH / 2, Branch.HEIGHT / 4 + GUI.font100.getSize(), true, Color.white, Color.white, GUI.font50, guiStuff);
    UIObject credits = new UIObject("CREATED BY: SEAN", Branch.WIDTH - 5, Branch.HEIGHT, false, true, Color.WHITE, Color.WHITE, GUI.font35, guiStuff);
    UIObject version = new UIObject("Version V2.3", 5, Branch.HEIGHT - 5, false, Color.lightGray, Color.lightGray, GUI.font35, guiStuff);
    UIObject enterOptions = new UIObject("Options", Branch.WIDTH / 2, Branch.HEIGHT - 20, true, Color.white, Color.ORANGE, GUI.font50, guiStuff);

    public static ArrayList<Customer> Customers = new ArrayList<>();
    public static Customer CurrentCustomer = new Customer("DUMMY");
    private String input = "";
    boolean lookupMode = false;
    boolean showComplete = true;

    public DataState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        background = new Background(handler);
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("MyData.ser");
            in = new ObjectInputStream(fis);
            Customers = (ArrayList) in.readObject();
            in.close();
            fis.close();
        } catch (Exception ex) {
            System.out.println("No Customers Found");
        }


        for (UIObject u : guiStuff) {
            addText(gui, u);
        }
        for (UIObject u : activeSearch) {
            addText(gui, u);
            u.active = false;
        }
        showCompleted.active = false;
        lookUp.setAllColors(Color.WHITE);
        enterNew.setAllColors(Color.WHITE);
        currentInput.setAllColors(Color.WHITE);
    }

    public static void SaveArray() {
        try {
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("MyData.ser"));
            out.writeObject(Customers);
            out.close();

            // Serialize data object to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(Customers);
            out.close();

            // Get the bytes of the serialized object
            byte[] buf = bos.toByteArray();
        } catch (IOException e) {
        }
    }

    @Override
    public void tick() {
        gui.tick();
        background.tick();
        if (enterOptions.wasClicked()) {
            handler.switchToState(Branch.DataOptionsScreen);
        }
        if (credits.isHovering()) {
            credits.setAllColors(new Color((int) (Math.random() * 254), (int) (Math.random() * 254), (int) (Math.random() * 254)));
        }
        if (enterNew.wasClicked()) {
            KeyManager.LockInput = false;
            enterNew.setText("ENTER NAME");
            enterNew.setAllColors(Color.white);
            lookUp.active = false;
        }
        if (lookUp.wasClicked()) {
            lookupMode = true;
            enterNew.active = false;
            lookUp.setText("SEARCH BY NAME OR PHONE");
            KeyManager.LockInput = false;
            lookUp.setAllColors(Color.white);
            lookUp.clicked = false;
            showCompleted.active = true;
        }

        getKeyInput();
    }

    public static ArrayList<Customer> NameMatches = new ArrayList<>();

    int offset = 0;

    private void getKeyInput() {
        if (KeyManager.LockInput) {
            return;
        }
        input += KeyManager.GetKeyLastTyped();
        if (handler.getKM().keyJustPressed(KeyEvent.VK_DELETE)) {
            input = "";
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_BACK_SPACE)) {
            if (input.length() == 0) {
                return;
            }
            input = input.substring(0, input.length() - 1);
        }
        currentInput.setText(input);
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ESCAPE)) {
            reset();
        }
        int i = 0;
        if (handler.getKM().keyJustPressed(KeyEvent.VK_DOWN) || handler.getMM().isWheelDown()) {
            offset += 1;
            if (10 + offset > NameMatches.size()) {
                offset -= 1;
            }
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_UP) || handler.getMM().isWheelUp()) {
            offset -= 1;
            if (offset < 0) {
                offset = 0;
            }
        }

        {
            for (Customer c : NameMatches) {
                if (i >= 10) {
                    break;
                }
                activeSearch.get(i).setText(NameMatches.get(i + offset).getName() + "   " + NameMatches.get(i + offset).getPhone() + "   Date Made: " + NameMatches.get(i + offset).getDate());
                if (NameMatches.get(i + offset).completed) {
                    activeSearch.get(i).setColor(Color.green);
                } else {
                    activeSearch.get(i).setColor(Color.white);
                }
                i++;
            }
            for (int j = i; j < activeSearch.size(); j++) {
                activeSearch.get(j).setText("");
            }
            for (UIObject o : activeSearch) {
                if (o.wasClicked()) {
                    for (Customer c : Customers) {
                        if (o.getText().equals(c.getName() + "   " + c.getPhone() + "   Date Made: " + c.getDate())) {
                            CurrentCustomer = c;
                            reset();
                            handler.switchToState(Branch.EditCustomer);
                        }
                    }
                }
            }

            if (lookupMode) {

                if (showCompleted.wasClicked()) {
                    if (showComplete) {
                        showCompleted.setText("SHOW COMPLETED");
                        showComplete = false;
                    } else {
                        showCompleted.setText("HIDE COMPLETED");
                        showComplete = true;
                    }
                }

                for (UIObject u : activeSearch) {
                    u.active = true;
                }

                for (Customer c : Customers) {
                    String namePhoneInfo = c.getName() + c.getPhone();
                    if (namePhoneInfo.contains(input)) {
                        if (!NameMatches.contains(c)) {
                            NameMatches.add(c);

                        }
                    } else {
                        NameMatches.remove(c);
                        offset = 0;
                    }
                    if(c.completed && !showComplete){
                        NameMatches.remove(c);
                        offset = 0;
                    }
                }
                return;
            } else {
                for (UIObject u : activeSearch) {
                    u.active = false;
                    u.clicked = false;
                }
            }
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_ENTER) && !lookupMode) {
            enterNew.resetColors();
            enterNew.setText("Enter New Customer");
            lookUp.active = true;
            lookUp.resetColors();
            captureName();
        }
    }

    @Override
    public void reset() {
        offset = 0;
        enterNew.resetColors();
        lookUp.resetColors();
        enterNew.active = true;
        lookUp.active = true;
        showCompleted.active = false;
        KeyManager.LockInput = true;
        lookupMode = false;
        enterNew.setText("Enter New Customer");
        lookUp.setText("Lookup Customer");
        input = "";
        currentInput.setText(input);
        for (UIObject u : guiStuff) {
            u.clicked = false;
        }
        for (UIObject u : activeSearch) {
            u.active = false;
            u.clicked = false;
        }
    }

    private void captureName() {
        KeyManager.LockInput = true;
        if (input.equals("")) {
            input = "DUMMY";
        }
        CurrentCustomer = new Customer(input);
        Customers.add(CurrentCustomer);
        input = "";
        currentInput.setText(input);
        handler.switchToState(Branch.EditCustomer);
    }

    @Override
    public void render(Graphics g) {
        background.render(g);
        gui.render(g);
    }
}
