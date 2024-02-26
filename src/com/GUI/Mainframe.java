package com.GUI;

import com.EgyptianLegue.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Mainframe extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private  playersPanel playersPanel ;
    private MatchPanel matchPanel;
    private teamsPanel teamsPanel;
    public Mainframe() {
        setTitle("Egyptian league");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create login form
        LogIn loginPanel = new LogIn(e -> loginSuccess());
        cardPanel.add(loginPanel, "login");

        // Create home form
        home homePanel = new home();
        homePanel.getMatchesPanelButton().addActionListener(e -> setMatchPanel());
        homePanel.getPlayersPanelButton().addActionListener(e -> setPlayersPanel());
        homePanel.getTeamsPanelButton().addActionListener(e -> setTeamsPanel());
        cardPanel.add(homePanel, "home");

        teamsPanel = new teamsPanel();
        teamsPanel.getBackButton().addActionListener(e ->  cardLayout.show(cardPanel, "home"));
        cardPanel.add(teamsPanel, "teamsPanel");

        playersPanel = new playersPanel();
        playersPanel.getBackButton().addActionListener(e ->  cardLayout.show(cardPanel, "home"));
        cardPanel.add(playersPanel, "playersPanel");

        matchPanel =new MatchPanel();
        matchPanel.getBackButton().addActionListener(e ->cardLayout.show(cardPanel, "home") );
        cardPanel.add(matchPanel, "match");
        add(cardPanel);
        // Add a window listener to perform actions before closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Add your actions here before the frame is closed
                System.out.println("Closing the frame...");

                // For example, you could prompt the user for confirmation
                int result = JOptionPane.showConfirmDialog(Mainframe.this, "Are you sure you want to exit?");
                if (result == JOptionPane.OK_OPTION) {
                    // Perform additional actions if the user confirms
                    if (!controller.isEmpty())
                        controller.writeToFiles();
                    System.out.println("Exiting the application.");
                    System.exit(0); // Terminate the application
                }
            }
        });
    }

    private void setTeamsPanel() {
        teamsPanel.setTeams(controller.L.getTeams());
        cardLayout.show(cardPanel, "teamsPanel");
    }

    private void loginSuccess() {
        cardLayout.show(cardPanel, "home");
        controller.read_files("F:/teams.txt", "F:/players.txt", "F:/matches.txt");
        System.out.println("read done");
    }

    private void setPlayersPanel()
    {
        playersPanel.players = controller.L.getPlayers();
        cardLayout.show(cardPanel, "playersPanel");
    }
    private void setMatchPanel()
    {
        matchPanel.matches = controller.L.getMatchList();
        cardLayout.show(cardPanel, "match");
    }

    public  void changePanel(){
        cardLayout.show(cardPanel,"");
    }
}

