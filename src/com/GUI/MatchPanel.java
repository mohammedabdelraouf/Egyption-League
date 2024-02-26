package com.GUI;

import com.EgyptianLegue.Match;
import com.EgyptianLegue.Team;
import com.EgyptianLegue.controller;
import com.EgyptianLegue.score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchPanel extends JPanel {
    private JTextField matchIDField, dateField, teamsField, refereeField, scoreField, stadiumField;
    private JTextArea outputArea;
    private JButton backButton;
    public List<Match> matches;

    public MatchPanel() {

        setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        matchIDField = new JTextField();
        dateField = new JTextField();
        teamsField = new JTextField();
        refereeField = new JTextField();
        scoreField = new JTextField();
        stadiumField = new JTextField();
        backButton = new JButton("Back To Home");

        inputPanel.add(new JLabel("Match ID:"));
        inputPanel.add(matchIDField);
        inputPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Teams (Separated by '|'):"));
        inputPanel.add(teamsField);
        inputPanel.add(new JLabel("Football Referee:"));
        inputPanel.add(refereeField);
        inputPanel.add(new JLabel("Score:"));
        inputPanel.add(scoreField);
        inputPanel.add(new JLabel("Stadium Name:"));
        inputPanel.add(stadiumField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton enterButton = new JButton("Enter Match");
        JButton updateButton = new JButton("Update Match");
        JButton displayListButton = new JButton("Display Match List");

        buttonPanel.add(backButton);
        buttonPanel.add(enterButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayListButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Button actions
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterMatchInformation();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMatchInformation();
            }
        });

        displayListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMatchList();
            }
        });
    }

    public JButton getBackButton() {
        return backButton;
    }

    private void enterMatchInformation() {
        try {
            int matchID = Integer.parseInt(matchIDField.getText());
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
            String[] teamNames = teamsField.getText().split("\\|");
            List<Team> teams = new ArrayList<>();
            for (String teamName : teamNames) {
                teams.add(new Team(teamName));
            }
            String[] scores = scoreField.getText().split("\\|");
            score s = new score(teamNames[0], Integer.parseInt(scores[0]),teamNames[1],Integer.parseInt(scores[1]));

            String referee = refereeField.getText();
            String stadium = stadiumField.getText();

            Match match = new Match(matchID, date, teams, referee, s, stadium);
            matches.add(match);
            controller.L.setMatchList(matches);
            updateOutputArea("Match information entered: " + match.toString());
            clearInputFields();
        } catch (NumberFormatException | ParseException ex) {
            updateOutputArea("Error: Invalid input format.");
        }
    }

    private void updateMatchInformation() {
        try {
            int matchID = Integer.parseInt(matchIDField.getText());
            Match match = getMatchById(matchID);

            if (match != null) {
                String updateType = JOptionPane.showInputDialog("Enter update type:");
                Object updateValue = getUpdateValue(updateType);

                if (updateValue != null) {
                    match.updateMatch(updateType, updateValue);
                    Match.setMatchById(matches, matchID,match);
                    controller.L.setMatchList(matches);
                    clearInputFields();
                } else {
                    updateOutputArea("Invalid update value.");
                }
            } else {
                updateOutputArea("Match not found.");
            }
        } catch (NumberFormatException ex) {
            updateOutputArea("Error: Invalid match ID format.");
        }
    }

    private void displayMatchList() {
        if (matches.isEmpty()) {
            updateOutputArea("Match list is empty.");
        } else {
            updateOutputArea("Match List:");
            for (Match match : matches) {
                outputArea.append(match.toString()+"\n");
            }
        }
    }

    private Match getMatchById(int matchID) {
        for (Match match : matches) {
            if (match.getMatchID() == matchID) {
                return match;
            }
        }
        return null;
    }

    private Object getUpdateValue(String updateType) {
        switch (updateType.toLowerCase()) {
            case "date":
                try {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(JOptionPane.showInputDialog("Enter new date (yyyy-MM-dd):"));
                } catch (ParseException e) {
                    return null;
                }
            case "teams":
                String[] teamNames = JOptionPane.showInputDialog("Enter new team names (Separated by '|'):").split("\\|");
                List<Team> teams = new ArrayList<>();
                for (String teamName : teamNames) {
                    teams.add(new Team(teamName));
                }
                return teams;
            case "football_referee":
                return JOptionPane.showInputDialog("Enter new football referee:");
            case "score":
                try {
                    String[]scores = JOptionPane.showInputDialog("Enter new score:").split("\\|");
                    score s = new score(Integer.parseInt(scores[0]),Integer.parseInt(scores[1]));
                    return s;
                } catch (NumberFormatException e) {
                    return null;
                }
            case "stadium_name":
                return JOptionPane.showInputDialog("Enter new stadium name:");
            default:
                return null;
        }
    }

    private void updateOutputArea(String message) {
        outputArea.setText(message + "\n");
    }

    private void clearInputFields() {
        matchIDField.setText("");
        dateField.setText("");
        teamsField.setText("");
        refereeField.setText("");
        scoreField.setText("");
        stadiumField.setText("");
    }

}
