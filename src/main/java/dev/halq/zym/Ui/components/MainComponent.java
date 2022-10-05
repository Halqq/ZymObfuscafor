package dev.halq.zym.Ui.components;

import dev.halq.zym.obfuscation.MainObfuscation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainComponent extends JPanel {

    public static JTextField output;
    public static JTextField input;
    public static JTextArea excludes;

    public MainComponent() {
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 =  new JPanel();
        JButton b1 = new JButton("...");
        JButton b2 = new JButton("...");
        JButton b3 = new JButton("Obfuscate");
        JLabel l1 = new JLabel("Input : ");
        JLabel l2 = new JLabel("Output :");
        JLabel l3 = new JLabel("Exclue : ");

        input = new JTextField(40);
        output = new JTextField(40);
        excludes = new JTextArea(10, 40);

        p1.add(l1);
        p1.add(input);
        p1.add(b1);

        b1.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NativeJFileChooser nfileChooser = new NativeJFileChooser(System.getProperty("user.home"));
                int option = nfileChooser.showOpenDialog(null);
                if (option == NativeJFileChooser.APPROVE_OPTION) {
                    File file = nfileChooser.getSelectedFile();
                    input.setText(file.getAbsolutePath());
            }
         }
        });

        p2.add(l2);
        p2.add(output);
        p2.add(b2);

        b2.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NativeJFileChooser nfileChooser = new NativeJFileChooser(System.getProperty("user.home"));
                int option = nfileChooser.showOpenDialog(null);
                if (option == NativeJFileChooser.APPROVE_OPTION) {
                    File file = nfileChooser.getSelectedFile();
                    output.setText(file.getAbsolutePath());
                }
            }
        });

        p3.add(l3);
        p3.add(excludes);

        p4.add(b3);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainObfuscation.startClassRemapping();
            }
        });

        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(p4);
    }

    public static String getInput(){
        return input.getText();
    }

    public static String getOutput(){
        return output.getText();
    }

    public static String getExcludes(){
        return excludes.getText();
    }

}
