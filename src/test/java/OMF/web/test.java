package OMF.web;

//import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.InputEvent;

public class test {
    public test() throws AWTException {
    }

    public static void main(String[] args) throws AWTException {

        Robot r = new Robot();

        int mask = InputEvent.BUTTON1_DOWN_MASK;
        r.mouseMove(1000, 1000);
        r.mousePress(mask);
        r.mouseRelease(mask);
    }
}

