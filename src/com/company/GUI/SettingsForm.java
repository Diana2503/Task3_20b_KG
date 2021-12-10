package com.company.GUI;

import com.company.Main;

import javax.swing.*;
import java.awt.*;

public class SettingsForm extends JFrame{
    private JPanel mainPanel;
    private JButton inButton;
    private JButton outButton;
    private JButton clearButton;
    private JLabel subPolys;
    private JLabel xMP;
    private JLabel yMP;

    private void setDefaults() {
        setContentPane(mainPanel);
        setVisible(true);
        setBounds(0,0,170,200);
        setResizable(false);

        Timer timer = new Timer(50, e -> {
            toFront();
            subPolys.setText("" + Main.mainGUI.getCounter());
            int mX = MouseInfo.getPointerInfo().getLocation().x;
            int mY = MouseInfo.getPointerInfo().getLocation().y;

            xMP.setText(String.valueOf(mX));
            yMP.setText(String.valueOf(mY));
        });
        timer.start();
    }

    public SettingsForm() {
        setDefaults();
        inButton.addActionListener(e -> Main.mainGUI.zoom(true));
        outButton.addActionListener(e -> Main.mainGUI.zoom(false));
        clearButton.addActionListener(e -> Main.mainGUI.clear());
    }
}
