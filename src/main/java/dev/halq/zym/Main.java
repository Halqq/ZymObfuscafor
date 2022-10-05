package dev.halq.zym;

import dev.halq.zym.Ui.UiMain;
import dev.halq.zym.Ui.components.MainComponent;

/**
 * @author Halq
 * @since 05/10/22
 */

public class Main {

    public static String version = "0.0.1";

    public static void main(String[] args) {
        UiMain.gui();
        System.out.println(MainComponent.getInput());

    }
}
