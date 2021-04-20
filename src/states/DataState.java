package states;

import branch.Branch;
import controls.KeyManager;
import gfx.GUI;
import gfx.UIObject;
import server.Client;
import server.Server;
import states.dataState.Background;
import states.dataState.Customer;
import states.dataState.CustomerUpdated;
import states.dataState.EditCustomer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class DataState extends States implements Serializable {

    private Background background;
    private double versionNumber = 3.0;

    public static Server Server;

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
    UIObject credits = new UIObject("CREATED BY: SEAN", Branch.WIDTH - 5, Branch.HEIGHT - GUI.font35.getSize(), false, true, Color.WHITE, Color.WHITE, GUI.font35, guiStuff);
    UIObject version = new UIObject("Version V" + versionNumber, 5, Branch.HEIGHT - 5 - GUI.font35.getSize(), false, Color.lightGray, Color.lightGray, GUI.font35, guiStuff);
    UIObject enterOptions = new UIObject("Options", Branch.WIDTH / 2, Branch.HEIGHT - 20 - GUI.font35.getSize(), true, Color.white, Color.ORANGE, GUI.font50, guiStuff);
    UIObject serverDetails = new UIObject("Connect To Server", 0, GUI.font.getSize(), false, Color.white, Color.ORANGE, GUI.font, guiStuff);

    public static ArrayList<Customer> OldCustomers = new ArrayList<>();
    public static ArrayList<CustomerUpdated> Customers = new ArrayList<>();
    public static CustomerUpdated CurrentCustomer = new CustomerUpdated("DUMMY");
    private String input = "";
    boolean lookupMode = false;
    boolean showComplete = true;
    public static Client client = new Client();

    public DataState(Handler handler) {
        super(handler);
        gui = new GUI(handler);
        background = new Background(handler);
        Server = new Server();
        UpdateFromData();


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

    public static void UpdateFromData() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("MyData.ser");
            in = new ObjectInputStream(fis);
            NameMatches.clear();
            Offset = 0;
            OldCustomers.clear();
            OldCustomers = (ArrayList) in.readObject();
            updateCustomers();
            in.close();
            fis.close();
            renameFile();
            SaveArray();
        } catch (Exception ex) {
            //System.out.println("MyData.ser not found. Trying CustomerData");
            try {
                fis = new FileInputStream("CustomerData.ser");
                in = new ObjectInputStream(fis);
                NameMatches.clear();
                Offset = 0;
                Customers.clear();
                Customers = (ArrayList) in.readObject();
                in.close();
                fis.close();
            } catch (Exception exc) {
                //System.out.println("CustomerData.ser not found.");
            }
        }
    }

    private static void renameFile() throws IOException {
        File file = new File("MyData.ser");

        File file2 = new File("OldData.ser");

        if (file2.exists())
            throw new java.io.IOException("file exists");

        boolean success = file.renameTo(file2);

        if (!success) {
            System.out.println("File was not successfully renamed");
        }
    }


    private static void updateCustomers() {
        for (Customer old : OldCustomers) {
            CustomerUpdated temp = new CustomerUpdated("TEMP");
            temp.setPhone(old.getPhone());
            temp.setEmail(old.getEmail());
            temp.setAddress(old.getAddress());
            temp.setName(old.getName());
            EditCustomer.changeData(temp, old);
            temp.completed = old.completed;
            Customers.add(temp);
        }
    }


    public static void SaveArray() {
        try {
            // Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CustomerData.ser"));
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
        setServerDetails();
        if(CurrentCustomer.toBeDeleted){
            Customers.remove(CurrentCustomer);
            NameMatches.remove(CurrentCustomer);
        }
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
            if (!ConnectState.connectIP.equals("")) {
                client.downloadFile();
            }
            UpdateFromData();
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

    public static ArrayList<CustomerUpdated> NameMatches = new ArrayList<>();

    static int Offset = 0;

    private void setServerDetails() {
        if (Server.runServer) {
            serverDetails.setText("SERVER RUNNING AT " + Server.getIPAddress());
            serverDetails.setAllColors(Color.WHITE);
            return;
        }
        if (!Server.runServer && !ConnectState.connectIP.equals("")) {
            serverDetails.setText("CLIENT CONNECT AT " + ConnectState.connectIP);
            serverDetails.setAllColors(Color.WHITE);
            return;
        }
        if (serverDetails.wasClicked()) {
            serverDetails.clicked = false;
            handler.switchToState(Branch.ConnectState);
        }
    }

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
            Offset += 1;
            if (10 + Offset > NameMatches.size()) {
                Offset -= 1;
            }
        }
        if (handler.getKM().keyJustPressed(KeyEvent.VK_UP) || handler.getMM().isWheelUp()) {
            Offset -= 1;
            if (Offset < 0) {
                Offset = 0;
            }
        }

        {
            for (CustomerUpdated c : NameMatches) {
                if (i >= 10) {
                    break;
                }
                activeSearch.get(i).setText(NameMatches.get(i + Offset).getName() + "   " + NameMatches.get(i + Offset).getPhone() + "   Date Made: " + NameMatches.get(i + Offset).getDate());
                if (NameMatches.get(i + Offset).completed) {
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
                    for (CustomerUpdated c : Customers) {
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

                for (CustomerUpdated c : Customers) {
                    String namePhoneInfo = c.getName() + c.getPhone();
                    if (namePhoneInfo.contains(input)) {
                        if (!NameMatches.contains(c)) {
                            if (c.completed && !showComplete) {

                            } else {
                                NameMatches.add(c);
                            }
                        }
                    } else {
                        if (NameMatches.contains(c)) {
                            NameMatches.remove(c);
                            Offset = 0;
                        }
                    }
                    if (c.completed && !showComplete) {
                        if (NameMatches.contains(c)) {
                            NameMatches.remove(c);
                            Offset = 0;
                        }
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
        Offset = 0;
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
        CurrentCustomer = new CustomerUpdated(input);
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
