package test;

import com.formdev.flatlaf.FlatLightLaf;
import gui.Controller;

public class MainClass {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        Controller controller = new Controller();

        controller.showWindow();
    }

}
