package com.GUI;

import com.EgyptianLegue.admin;
import com.EgyptianLegue.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LogIn extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private ActionListener loginListener;
    private JPanel panel1;

    public LogIn(ActionListener loginListener) {
        this.loginListener = loginListener;

        // Same code as before for creating the login panel
        setSize(300, 150);
        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        this.loginListener = loginListener;

        // Same code as before for creating the login panel

        // Add components to the panel
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        loginButton.addActionListener(e -> performLogin());

    }

    private void performLogin() {
        List<admin> admins = controller.readAdminsFromFile("F:/admins.txt");
        // Same login logic as before
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        boolean flag = false ;
        // If login successful, invoke the listener
        for(admin a : admins)
        {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                loginListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                flag = true;
                break;
            }

        }
        if(!flag)
        {
            JOptionPane.showMessageDialog(this, "Login Failed. Please try again.");
            passwordField.setText("");
        }


    }

}

