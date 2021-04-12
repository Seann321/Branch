package branch;

import java.awt.*;

public class Main {

    public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        Branch branch = new Branch("Branch", 1920, 1080);
        branch.start();
    }

}
