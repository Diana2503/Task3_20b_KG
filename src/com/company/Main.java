package com.company;

import com.company.GUI.MainGUI;
import com.company.GUI.ScreenConverter;
import com.company.GUI.SettingsForm;

public class Main {

    public static MainGUI mainGUI;
    public static SettingsForm settingsForm;

    public static void main(String[] args) {
        mainGUI = new MainGUI();
        settingsForm = new SettingsForm();
    }


}

