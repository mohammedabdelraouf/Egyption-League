package com.GUI;

import com.EgyptianLegue.Player;
import com.EgyptianLegue.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class playersPanel extends JPanel {
    private JTextField nameField, idField, numberField, teamField, ageField, scoreField, rankField;
    private JTextArea outputArea;
    private JButton backButton;
    public List<Player> players;

    public playersPanel() {

        setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        nameField = new JTextField();
        idField = new JTextField();
        numberField = new JTextField();
        teamField = new JTextField();
        ageField = new JTextField();
        scoreField = new JTextField();
        rankField = new JTextField();
        backButton = new JButton("back");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Number:"));
        inputPanel.add(numberField);
        inputPanel.add(new JLabel("Team:"));
        inputPanel.add(teamField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Score:"));
        inputPanel.add(scoreField);
        inputPanel.add(new JLabel("Rank:"));
        inputPanel.add(rankField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton enterButton = new JButton("Enter");
        JButton updateButton = new JButton("Update");
        JButton searchButton = new JButton("Search");
        buttonPanel.add(backButton);
        buttonPanel.add(enterButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Button actions
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPlayerInformation();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlayerInformation();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPlayer();
            }
        });

        // New button to display player list
        JButton displayListButton = new JButton("Display Player List");
        buttonPanel.add(displayListButton);

        displayListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPlayerList(players);
            }
        });
    }

    public JButton getBackButton() {
        return backButton;
    }

    private void displayPlayerList(List<Player> playerList) {
        if (playerList.isEmpty()) {
            updateOutputArea("Player list is empty.");
        } else {
            updateOutputArea("Player List:");
            for (Player player : playerList) {
                outputArea.append(player.toString()+"\n");
            }
        }
    }


    private void enterPlayerInformation() {
        String name = nameField.getText();
        int id = Integer.parseInt(idField.getText());
        int number = Integer.parseInt(numberField.getText());
        String team = teamField.getText();
        int age = Integer.parseInt(ageField.getText());
        int score = Integer.parseInt(scoreField.getText());
        int rank = Integer.parseInt(rankField.getText());

        Player player = new Player(name, id, number, team, age, score, rank);
        players.add(player);
        controller.L.setPlayers(players);
        updateOutputArea("Player information entered: " + player.toString());
        clearInputFields();
    }

    private void updatePlayerInformation() {
        int number = Integer.parseInt(numberField.getText());
        String team = teamField.getText();
        int age = Integer.parseInt(ageField.getText());
        int score = Integer.parseInt(scoreField.getText());
        int rank = Integer.parseInt(rankField.getText());

        Player player = getPlayerById(Integer.parseInt(idField.getText()));
        if (player != null) {
            player.updatePlayerInformation(number, team, age, score, rank);
            updateOutputArea("Player information updated: " + player.toString());
            clearInputFields();
        } else {
            updateOutputArea("Player not found.");
        }
    }

    private void searchPlayer() {
        String playerName = nameField.getText();
        String teamName = teamField.getText();
        int playerId = Integer.parseInt(idField.getText());
        Player player = null;
        if (teamName.isEmpty()) {
            player = Player.searchPlayerByName(players, playerName);
        } else if(!teamName.isEmpty()&& !playerName.isEmpty()){
            player = Player.searchPlayerByNameAndTeam(players, playerName, teamName);
        }
        else if(!idField.getText().isEmpty())
        {
            player = Player.searchPlayerByID(players , playerId);
        }
        if (player != null) {
            updateOutputArea("Player found: " + player.toString());
            clearInputFields();
        } else {
            updateOutputArea("Player not found.");
        }
    }

    private Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    private void updateOutputArea(String message) {
        //outputArea.append(message + "\n");
        outputArea.setText(message + "\n");
    }

    private void clearInputFields() {
        nameField.setText("");
        idField.setText("");
        numberField.setText("");
        teamField.setText("");
        ageField.setText("");
        scoreField.setText("");
        rankField.setText("");
    }

}

