package com.GUI;

import javax.swing.*;
import java.awt.*;

public class home extends JPanel {
    private JButton teamsPanelButton;
    private JButton matchesPanelButton;
    private JButton playersPanelButton;

    public home() {
        setLayout(new GridLayout(7, 3));

        teamsPanelButton = new JButton("Open teams panel");
        matchesPanelButton = new JButton("Open match panel");
        playersPanelButton = new JButton("Open players panel");

        add(teamsPanelButton);
        add(matchesPanelButton);
        add(playersPanelButton);
    }

    public JButton getTeamsPanelButton() {
        return teamsPanelButton;
    }

    public JButton getMatchesPanelButton() {
        return matchesPanelButton;
    }

    public JButton getPlayersPanelButton() {
        return playersPanelButton;
    }

}
