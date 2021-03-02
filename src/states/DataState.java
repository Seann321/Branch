package states;

import branch.Branch;
import controls.KeyManager;
import gfx.GUI;
import gfx.UIObject;
import states.dataState.Customer;
import states.dataState.LoadSaveFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class DataState extends States {

    private ArrayList<UIObject> guiStuff = new ArrayList<>();
    private ArrayList<UIObject> activeSearch = new ArrayList<>();
    UIObject lookUp = new UIObject("Lookup Customer", Branch.WIDTH / 2, Branch.HEIGHT / 4 - GUI.font100.getSize(), true, Color.white, Color.ORANGE, GUI.font100, guiStuff);
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
    public static ArrayList<Customer> Customers = new ArrayList<>();
    public static Customer CurrentCustomer = new Customer("DUMMY");
    private String input = "";
    boolean lookupMode = false;

    public DataState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        for (UIObject u : guiStuff) {
            addText(gui, u);
        }
        for (UIObject u : activeSearch) {
            addText(gui, u);
            u.active = false;
        }
        lookUp.setAllColors(Color.WHITE);
        enterNew.setAllColors(Color.WHITE);
        currentInput.setAllColors(Color.WHITE);
        LoadSaveFile.createFile();
    }


    @Override
    public void tick() {
        gui.tick();
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
        }

        getKeyInput();
    }

    private ArrayList<Customer> nameMatches = new ArrayList<>();


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

        {
            int i = 0;
            for (Customer c : nameMatches) {
                activeSearch.get(i).setText(nameMatches.get(i).getName() + "   " + nameMatches.get(i).getPhone() + "   Date Made: " + nameMatches.get(i).getDate());
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

                for (UIObject u : activeSearch) {
                    u.active = true;
                }

                for (Customer c : Customers) {
                    String namePhoneInfo = c.getName() + c.getPhone();
                    if (namePhoneInfo.contains(input)) {
                        if (!nameMatches.contains(c)) {
                            nameMatches.add(c);
                        }
                    } else {
                        nameMatches.remove(c);
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
        enterNew.resetColors();
        lookUp.resetColors();
        enterNew.active = true;
        lookUp.active = true;
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
        gui.render(g);
    }
}
