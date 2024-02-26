package com.Main;

import com.GUI.Mainframe;

import javax.swing.*;
public class HelloApplication extends JFrame {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Mainframe().setVisible(true));

    }
}