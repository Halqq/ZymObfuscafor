package dev.halq.zym.Ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import dev.halq.zym.Main;
import dev.halq.zym.Ui.components.MainComponent;
import dev.halq.zym.Ui.components.ObfuscateComponent;
import dev.halq.zym.Ui.components.SettingComponent;

import javax.swing.*;
import java.awt.*;

/**
 * @author Halq
 * @since 05/10/22
 */

public class UiMain {

    public UiMain(){
        gui();
    }

    public static void gui(){
        FlatDarculaLaf.setup();
        JTabbedPane component = new JTabbedPane();
        JFrame frame = new JFrame("ZymObfuscator " + Main.version);

        component.add("Main", new MainComponent());
        component.add("Settings", new SettingComponent());
        component.add("Obfuscate", new ObfuscateComponent());


        component.setPreferredSize(new Dimension(650, 450));
        frame.add(component, BorderLayout.CENTER);
        frame.setLocation(150, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();

        frame.setVisible(true);


    }
}
