package branch;

import java.awt.*;

public class Main {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        Branch branch = new Branch("Branch", screenSize.width, screenSize.height);
        branch.start();
    }

}
